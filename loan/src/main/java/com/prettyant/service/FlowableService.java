package com.prettyant.service;

import com.prettyant.bean.BusinessInfo;
import com.prettyant.bean.FlowPathModel;
import com.prettyant.bean.FlowPathModelResponse;
import com.prettyant.bean.TaskModel;
import com.prettyant.dao.BusinessInfoDao;
import com.prettyant.utils.LogUtils;
import com.prettyant.utils.ResponseUtil;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FlowableService {

    @Autowired
    private StandaloneProcessEngineConfiguration configuration;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private BusinessInfoDao businessInfoDao;


    /**
     * 部署流程
     */
    @Bean
    @ConditionalOnMissingBean
    public Deployment deployment() {
        // 1、获取 ProcessEngine 对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        // 2、获取RepositoryService  资源相关的服务
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //仅部署一次
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        List<Deployment> list = deploymentQuery.list();
        for (Deployment deploy : list) {
            if (deploy.getName().equals("贷款审批流程plus")) {
                LogUtils.w(FlowableService.class, "已经部署过了------");
                return deploy;
            }
        }
        LogUtils.w(FlowableService.class, "第一次部署------");
        // 3、完成流程的部署操作
        Deployment deploy = repositoryService.createDeployment()
//                .addClasspathResource("LoanApplicationPlus.bpmn20.xml") // 关联要部署的流程文件
                .addClasspathResource("LoanApplicationSimple1.4.bpmn20.xml") // 关联要部署的流程文件
                .name("贷款审批流程plus")
                .deploy(); // 部署流程
        System.out.println("deploy.getId() = " + deploy.getId() + " , this is deployment id as used in following.");
        System.out.println("deploy.getName() = " + deploy.getName() + " configuration:" + configuration);
        return deploy;

        //关注两张数据表: ACT_RE_DEPLOYMENT(发布信息表) , ACT_RE_PROCDEF(流程定义表), ACT_GE_BYTEARRAY
    }

    /**
     * 用户提交业务
     *
     * @param businessInfo
     */
    public void submitBusiness(BusinessInfo businessInfo) {
        ruleService.executeRule(businessInfo);//规则匹配

        ProcessEngine processEngine = configuration.buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 在启动流程实例的时候创建了流程变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee0", "zhangsan");
        variables.put("assignee1", "lisi");
        variables.put("username", businessInfo.getUsername());
        variables.put("businessName", businessInfo.getBusinessName());
        variables.put("amount", businessInfo.getAmount());
        variables.put("duringTime", businessInfo.getDuringTime());
        variables.put("rate", businessInfo.getRate());
//        runtimeService.startProcessInstanceById(deployment.getId(),variables);
        ProcessInstance loanApplication = runtimeService.startProcessInstanceByKey("LoanApplication", variables);

        //设置业务编号
        businessInfo.setProcessInstanceId(loanApplication.getId());
        //设置 业务状态
        businessInfo.setBusinessStatus(0);
        //备份一份到数据库
        businessInfoDao.addBusinessInfo(businessInfo);
        System.out.println("流程定义的ID：" + loanApplication.getProcessDefinitionId());
        System.out.println("流程实例的ID：" + loanApplication.getId());
        System.out.println("当前活动的ID：" + loanApplication.getActivityId());

    }

    /**
     * 用户查询办理的业务信息
     *
     * @param sUsername
     * @return
     */
    public List<BusinessInfo> queryBusinessInfos(String sUsername, int index) {
        int pageSize = 10;
        ProcessEngine processEngine = configuration.buildProcessEngine();
        List<HistoricProcessInstance> list = processEngine.getHistoryService().createHistoricProcessInstanceQuery().list();
        ArrayList<BusinessInfo> businessInfos = new ArrayList<>();
        for (HistoricProcessInstance historicProcessInstance : list) {
            List<HistoricVariableInstance> result = processEngine.getHistoryService().createHistoricVariableInstanceQuery().executionId(historicProcessInstance.getId()).list();

            BusinessInfo businessInfo = new BusinessInfo();
            for (HistoricVariableInstance h : result) {
                String variableName = h.getVariableName();
                switch (variableName) {
                    case "amount":
                        businessInfo.setAmount((String) h.getValue());
                        break;
                    case "rate":
                        businessInfo.setRate((Float) h.getValue());
                        break;
                    case "businessName":
                        businessInfo.setBusinessName((String) h.getValue());
                        break;
                    case "username":
                        businessInfo.setUsername((String) h.getValue());
                        break;
                    case "duringTime":
                        businessInfo.setDuringTime((String) h.getValue());
                        break;
                    case "businessStatus":
                        businessInfo.setBusinessStatus((Integer) h.getValue());
                        break;
                }
            }
            Date startTime = historicProcessInstance.getStartTime();
            SimpleDateFormat sdfstart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            businessInfo.setCreateTime(sdfstart.format(startTime));//创建时间
            businessInfo.setProcessInstanceId(historicProcessInstance.getId());
            if (businessInfo.getUsername().equals(sUsername)) {
                businessInfos.add(businessInfo);
            }

        }
        Collections.reverse(businessInfos);//不用倒叙，最上面显示最新的即可
        if ((index + 1) * 10 <= businessInfos.size()) {
            return businessInfos.subList(index, ((index + 1) * pageSize));
        } else {
            if (index * pageSize > businessInfos.size()) {
                return new ArrayList<>();
            }
            return businessInfos.subList(index * pageSize, businessInfos.size());
        }
    }


    /**
     * 用户 根据业务id 获取某个业务流程
     *
     * @param processInstanceId 业务编号
     * @return
     */
    public ArrayList<FlowPathModel> queryUserProcess(String processInstanceId) {
        System.out.println("需要查询的 processInstanceId = " + processInstanceId);
        ProcessEngine processEngine = configuration.buildProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();

        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime().desc() // 指定排序的字段和顺序
                .list();
        FlowPathModelResponse flowPathModelResponse = new FlowPathModelResponse();
        ArrayList<FlowPathModel> flowPathModels = new ArrayList<>();
        for (HistoricActivityInstance history : list) {
            String activityName = history.getActivityName();//节点名称
            String assignee = history.getAssignee();//操作员姓名
//            String executionId = history.getExecutionId();//业务编号
            String taskId = history.getTaskId();//任务编号
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = history.getStartTime();
            String formatTime = sdf.format(startTime);
            System.out.println("history.getExecutionId() = " + history.getExecutionId());
            FlowPathModel flowPathModel = new FlowPathModel();
            //流程名称

            flowPathModel.setActivityName(activityName);
            List<Comment> taskComments = processEngine.getTaskService().getTaskComments(taskId);
            if (taskComments.size() > 0) {
                flowPathModel.setActivityName(activityName + "\n审批意见:" + taskComments.get(0).getFullMessage());
            }
            flowPathModel.setAssignee(!StringUtils.hasText(assignee) ? "服务节点" : assignee);
            flowPathModel.setProcessInstanceId(processInstanceId);
            flowPathModel.setTaskId(taskId);
            flowPathModel.setStartTime(formatTime);
            flowPathModels.add(flowPathModel);
        }
        flowPathModelResponse.setFlowPathModels(flowPathModels);
        return flowPathModels;
    }


    /**
     * 操作员查询已完成的任务
     *
     * @param cusName 登录用户名
     * @param index   索引
     * @return 已完成列表
     */
    public List<TaskModel> queryCusFinishedTask(String cusName, int index) {
        int pageSize = 10;
        ProcessEngine processEngine = configuration.buildProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .finished() // 查询的历史记录的状态是已经完成
                .taskAssignee(cusName)
//                .executionId("417511")
                .orderByHistoricActivityInstanceStartTime().desc() // 指定排序的字段和顺序
                .list();
        ArrayList<TaskModel> taskModels = new ArrayList<>();

        for (HistoricActivityInstance history : list) {
            String taskId = history.getTaskId();//任务编号
            Date startTime = history.getStartTime();
            String activityName = history.getActivityName();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatStartTime = sdf.format(startTime);
            String processInstanceId = history.getProcessInstanceId();//业务编号
            String description = "无";
            List<Comment> taskComments = processEngine.getTaskService().getTaskComments(taskId);
            if (taskComments.size() > 0) {
                description = taskComments.get(0).getFullMessage();
            }

            TaskModel taskModel = new TaskModel();
            taskModel.setAssignee(cusName);
            taskModel.setTaskId(taskId);
            taskModel.setName(activityName);
            taskModel.setCreateTime(formatStartTime);
            taskModel.setProcessInstanceId(processInstanceId);
            taskModel.setDescription(description);
            taskModels.add(taskModel);
        }
        if ((index + 1) * 10 <= taskModels.size()) {
            return taskModels.subList(index, ((index + 1) * pageSize));
        } else {
            if (index * pageSize > taskModels.size()) {
                return new ArrayList<>();
            }
            return taskModels.subList(index * pageSize, taskModels.size());
        }
    }

    /**
     * 操作员查询当前待处理的任务
     *
     * @param cusName 为空时，获取的是所有的任务，
     *                不为空，获取的是当前业务员的任务
     * @return
     */
    public List<TaskModel> queryCurrentTask(String cusName, int index) {
        int pageSize = 10;
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().list();
        List<TaskModel> taskModels = new ArrayList<>();
        for (Task task : list) {
            String assignee = task.getAssignee();
            String processInstanceId = task.getProcessInstanceId();
            String id = task.getId();
            String name = task.getName();
            String description = task.getDescription();
            Date createTime = task.getCreateTime();
            if (assignee.equals(cusName) || !StringUtils.hasText(cusName)) {
                TaskModel taskModel = new TaskModel();
                taskModel.setAssignee(assignee);
                taskModel.setName(name);
                if (name.startsWith("业务受理")) {
                    taskModel.setNeedJudge(false);
                } else {
                    taskModel.setNeedJudge(true);
                }
                taskModel.setTaskId(id);
                taskModel.setProcessInstanceId(processInstanceId);
                taskModel.setDescription(description);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formatTime = sdf.format(createTime);
                taskModel.setCreateTime(formatTime);
                taskModels.add(taskModel);
            }
            System.out.println("task :" + task + "  assignee:" + assignee + "   name:" + name + "  definitionId:" + task.getProcessDefinitionId());
        }
        Collections.reverse(taskModels);
        if ((index + 1) * 10 <= taskModels.size()) {
            return taskModels.subList(index, ((index + 1) * pageSize));
        } else {
            if (index * pageSize > taskModels.size()) {
                return new ArrayList<>();
            }
            return taskModels.subList(index * pageSize, taskModels.size());
        }
    }


    /**
     * 操作员处理当前的任务
     *
     * @param taskId            任务id
     * @param processInstanceId
     * @param approved          同意/拒绝
     * @return
     */
    public String dealTask(String taskId, String processInstanceId, String message, boolean approved) {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        //添加审批意见
        taskService.addComment(taskId, processInstanceId, message);
        // 创建流程变量
        Map<String, Object> map = new HashMap<>();
        map.put("approved", approved);
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Integer times = (Integer) runtimeService.getVariable(processInstanceId, "times");
        if (!approved) {
            if (times == null) {
                map.put("times", 1);
            } else {
                times++;
                map.put("times", times);
            }
        } else {
            if (times == null) {
                map.put("times", 0);
            } else {
                map.put("times", times);
            }
        }
        LogUtils.i(FlowableService.class, "当前拒绝次数 " + times);
        // 完成任务
        taskService.complete(taskId, map);
        return ResponseUtil.returnData(ResponseUtil.OK, "处理成功");
    }
}
