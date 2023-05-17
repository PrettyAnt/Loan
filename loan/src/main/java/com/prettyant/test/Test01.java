package com.prettyant.test;

import com.google.gson.Gson;
import com.prettyant.bean.BusinessInfo;
import com.prettyant.bean.FlowPathModel;
import com.prettyant.bean.TaskModel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.*;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.flowable.variable.api.history.HistoricVariableInstanceQuery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class Test01 {

    /**
     * 获取流程引擎对象
     */
    @Test
    public void testProcessEngine() {
        // 获取 ProcessEngineConfiguration 对象
        ProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        // 配置 相关的数据库连接信息
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("123456");
        // UTC是统一标准世界时间
        //也可以设置以下时区
        //北京时间东八区
        //serverTimezone=GMT%2B8
        //上海时间
        //serverTimezone=Asia/Shanghai
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/flowable-learn?serverTimezone=UTC");
        // 如果数据库中的表结构不存在就新建
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        // 通过 ProcessEngineConfiguration 构建 processEngine 对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
    }

    /*
        数据库表名说明：

        Flowable的所有数据库表都以ACT_开头。第二部分是说明表用途的两字符标示符。服务API的命名也大略符合这个规则。

        ACT_RE_*: 'RE’代表repository。带有这个前缀的表包含“静态”信息，例如流程定义与流程资源（图片、规则等）。

        ACT_RU_*: 'RU’代表runtime。这些表存储运行时信息，例如流程实例（process instance）、用户任务（user task）、变量（variable）、作业（job）等。Flowable只在流程实例运行中保存运行时数据，并在流程实例结束时删除记录。这样保证运行时表小和快。

        ACT_HI_*: 'HI’代表history。这些表存储历史数据，例如已完成的流程实例、变量、任务等。

        ACT_GE_*: 通用数据。在多处使用。
    */

    ProcessEngineConfiguration configuration = null;

    @Before
    // import org.junit.Before; 在一个类中最先执行的方法
    public void before() {
        // 获取 ProcessEngineConfiguration 对象
        configuration = new StandaloneProcessEngineConfiguration();
        // 配置 相关的数据库连接信息
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("123456");
        // UTC是统一标准世界时间
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/flowable-learn?serverTimezone=UTC");
        // 如果数据库中的表结构不存在就新建
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
    }

    /**
     * 部署流程
     */
    @Test
    public void testDeploy() {
        // 1、获取 ProcessEngine 对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        // 2、获取RepositoryService  资源相关的服务
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 3、完成流程的部署操作
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("LoanApplicationSimple1.4.bpmn20.xml") // 关联要部署的流程文件
                .name("贷款审批流程plus")
                .deploy(); // 部署流程
        System.out.println("deploy.getId() = " + deploy.getId() + " , this is deployment id as used in following.");
        System.out.println("deploy.getName() = " + deploy.getName());

        //关注两张数据表: ACT_RE_DEPLOYMENT(发布信息表) , ACT_RE_PROCDEF(流程定义表), ACT_GE_BYTEARRAY
    }

    @Test
    public void testConfig() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        List<Deployment> list = deploymentQuery.list();
        for (Deployment deploment : list) {
            System.out.println(" " + deploment.getId() + "  " + deploment.getName() + " " + deploment.getDeploymentTime());
        }

//        TaskService taskService = processEngine.getTaskService();
//        TaskQuery taskQuery = taskService.createTaskQuery();
//        List<Task> list = taskQuery.list();
//        for (Task task : list) {
//            System.out.printf("task:", task.getName() + " " + task.getId() + "" + task.getAssignee() + "" + task.getDescription());
//        }
    }


    /**
     * 查询流程定义的信息
     */
    @Test
    public void testDeployQuery() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 查询单个
        // 如果 查询多个， 就用 deploymentIds
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId("375001").singleResult();
        System.out.println("processDefinition.getName() = " + processDefinition.getName());
        System.out.println("processDefinition.getId() = " + processDefinition.getId());
        System.out.println("processDefinition.getDeploymentId() = " + processDefinition.getDeploymentId());
        System.out.println("processDefinition.getDescription() = " + processDefinition.getDescription());
    }

    /**
     * 删除流程定义
     */
    @Test
    public void testDeleteDeploy() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 删除部署的流程 第一个参数id，如果部署的流程启动了就不允许删除
//        repositoryService.deleteDeployment("1");

        // 第二个参数是级联删除，如果部署的流程启动了 相关的任务会一并被删除
        repositoryService.deleteDeployment("610001", true);
    }

    /**
     * 启动流程实例  deploymentId 启动
     */
    @Test
    public void runProcess() {
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 在启动流程实例的时候创建了流程变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee0", "zhangsan");
        variables.put("assignee1", "lisi");
        variables.put("username", "贷款用户2");
        variables.put("nll", 3);
        variables.put("description", "车贷");
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId("375001").singleResult();
        System.out.println("processDefinition.getId() = " + processDefinition.getId());
        runtimeService.startProcessInstanceById(processDefinition.getId(), variables);
    }

    /**
     * 启动流程实例
     */
    @Test
    public void testRunProcess() {
        String businessKey = "1000010";
        ProcessEngine processEngine = configuration.buildProcessEngine();
        // 通过RuntimeService启动流程实例
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 构建流程变量
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("assignee0", "zhangsan");
        variables.put("assignee1", "lisi");
        variables.put("username", "贷款用户1000010");


        // 流程key <process id="holidayRequest" name="Holiday Request" isExecutable="true">
        ProcessInstance loanApplication = runtimeService.startProcessInstanceByKey("LoanApplication", businessKey, variables);


        System.out.println("LoanApplication.getProcessDefinitionId() = " + loanApplication.getProcessDefinitionId());
        System.out.println("LoanApplication.getId() = " + loanApplication.getId());
        System.out.println("LoanApplication.getActivityId() = " + loanApplication.getActivityId());
        System.out.println("LoanApplication.getName() = " + loanApplication.getName());

        System.out.println("流程定义的ID：" + loanApplication.getProcessDefinitionId());
        System.out.println("流程实例的ID：" + loanApplication.getId());
        System.out.println("当前活动的ID：" + loanApplication.getActivityId());


        // act_ru_variable、 act_ru_task、act_ru_execution 表可查看到相关信息。
    }

    @Test
    public void testVariables() {
        String username = "chenyu";
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Object times = runtimeService.getVariable("580004", "times");
        System.out.println("times = " + times);
//        List<Execution> list = runtimeService.createExecutionQuery().list();
//        BusinessInfo businessInfo = new BusinessInfo();
//        for (Execution execution : list) {
//            String name = (String) runtimeService.getVariable(execution.getId(), "username");
////            runtimeService.set
//            if (username.equals(name)) {
//                businessInfo.setUsername(name);
////                businessInfo.set
//            }
//            System.out.println("name --- >>> " + name);
//        }
    }

    /**
     * 查询流程变量
     */
    @Test
    public void test03() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 根据 执行实例id 查找变量  act_ru_variable

        List<Execution> list = runtimeService.createExecutionQuery().list();
        System.out.println("list.size() = " + list.size());
        for (Execution execution : list) {
            // 查找指定变量
//            Object startTime = runtimeService.getVariable(execution.getId(), "username");
            if (execution.getId().equals("412501")) {
                System.out.println("result: " + runtimeService.getVariables(execution.getId()));
            }
//            Map<String, Object> map = runtimeService.getVariables(execution.getId());
//            System.out.println("map = " + map);
        }
    }

    @Test
    public void queryUser() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        // 通过RuntimeService启动流程实例
        List<HistoricActivityInstance> list = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery().list();
        for (HistoricActivityInstance h : list) {
            System.out.println("h = " + h);
        }
    }

    @Test
    public void queryTask() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
//        Task task = taskService.createTaskQuery()
//                .processDefinitionKey("LoanApplication")
//                .taskAssignee("zhangsan")
//                .singleResult();
        String taskId = "512656";
        List<Comment> taskComments = taskService.getTaskComments(taskId);
        for (Comment taskComment : taskComments) {
            System.out.println("result: " + taskComment.getUserId() + "--" + taskComment.getFullMessage());
        }
//        List<Task> list = taskService.createTaskQuery()
//                .processDefinitionKey("LoanApplication")
//                .list();
//        for (Task task : list) {
//            System.out.println(task.getDescription()+"  taskId :" + task.getId() + "  assignee:" + task.getAssignee() + "   name:" + task.getName() + "  definitionId:" + task.getProcessDefinitionId());
//        }


//        System.out.println("=====================================");
//        // 创建流程变量
//        Map<String,Object> map = new HashMap<>();
//        map.put("approved",true);
//        // 完成任务
//        taskService.complete("252503",map);
//        List<Task> list2 = taskService.createTaskQuery()
//                .list();
//        for (Task task:list2) {
//            System.out.println("task :" +task+"  "+task.getAssignee());
//        }
    }

    @Test
    public void queryRunTimeService() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ExecutionQuery executionQuery = runtimeService.createExecutionQuery();
        List<Execution> list = executionQuery.list();
        for (Execution execution : list) {
            System.out.println(execution.getDescription() + " -- " + execution.getName() + " " + execution.getId() + " " + execution.getActivityId() + " " + execution.getDescription() + "\n\n");
        }
    }

    //查询当前用户下的流程
    @Test
    public void testQueryTaskForLisi() {
        String currentUsername = "zhangsan";
        // 获取流程引擎对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery()
//                .processDefinitionKey("LoanApplication")
                .taskAssignee(currentUsername)
                .list();
        for (Task task : list) {
            System.out.println("=================================================");
            System.out.println("task.getProcessDefinitionId() = " + task.getProcessDefinitionId());
            System.out.println("task.getId() = " + task.getId());
            System.out.println("task.getAssignee() = " + task.getAssignee());
            System.out.println("task.getName() = " + task.getName());
            System.out.println("task.getDescription() = " + task.getDescription());
        }
    }

    /**
     * 完成当前任务: 不批假期
     */
    @Test
    public void testCompleteTaskRejected() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("LoanApplication")
                .taskAssignee("zhangsan")
                .singleResult();
        // 创建流程变量
        Map<String, Object> map = new HashMap<>();
        map.put("approved", false);
        // 完成任务
        taskService.complete(task.getId(), map);

    }

    /**
     * 完成当前任务: 批准假期
     */
    @Test
    public void testCompleteTaskApproved() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
//        Task task = taskService.createTaskQuery()
//                .processDefinitionKey("LoanApplication")
//                .taskAssignee("zhangsan")
//                .singleResult();
        // 创建流程变量
        Map<String, Object> map = new HashMap<>();
        map.put("approved", true);
        // 完成任务
        taskService.complete("500003", map);
    }


    /**
     * 获取流程任务的历史数据
     */
    @Test
    public void queryUserProcess() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
//                .processDefinitionId("LoanApplication:1:360003")
//                .processInstanceId("477520")
//                .finished() // 查询的历史记录的状态是已经完成
//                .taskAssignee("zhangsan")
//                .executionId("417511")
                .orderByHistoricActivityInstanceStartTime().asc() // 指定排序的字段和顺序
                .list();
        ArrayList<FlowPathModel> flowPathModels = new ArrayList<>();

        for (HistoricActivityInstance history : list) {
            String activityName = history.getActivityName();//节点名称
            String assignee = history.getAssignee();//操作员姓名
            String processInstanceId = history.getProcessInstanceId();//业务编号
            String taskId = history.getTaskId();//任务编号
            System.out.println("result------->>" + history.getExecutionId() + " - "
                    + history.getAssignee() + " - "
                    + history.getTaskId() + " - "
                    + history.getProcessDefinitionId() + " - "
                    + history.getProcessInstanceId() + " - "
                    + history.getActivityType() + " - "
                    + history.getActivityName() + " - "
                    + history.getActivityId() + " - "
                    + history.getCalledProcessInstanceId() + " - "
                    + history.getId() + "--"
                    + history.getDeleteReason());

            FlowPathModel flowPathModel = new FlowPathModel();
            //流程名称
            flowPathModel.setActivityName(activityName);
            flowPathModel.setAssignee(!StringUtils.hasText(assignee) ? "服务节点" : assignee);
            flowPathModel.setProcessInstanceId(processInstanceId);
            flowPathModel.setTaskId(taskId);
            flowPathModels.add(flowPathModel);
        }
    }

    /**
     * 获取流程任务的历史数据
     */
    @Test
    public void testHistory() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
//                .finished() // 查询的历史记录的状态是已经完成
//                .taskAssignee("zhangsan")
//                .processInstanceId("532507")
                .orderByHistoricActivityInstanceStartTime().asc() // 指定排序的字段和顺序
                .list();
        ArrayList<TaskModel> taskModels = new ArrayList<>();
        TaskService taskService = processEngine.getTaskService();
        HashMap<String, String> stringStringHashMap = new HashMap<>();

        for (HistoricActivityInstance history : list) {
            String assignee = history.getAssignee();//操作员姓名
            String taskId = history.getTaskId();//任务编号
            String activityName = history.getActivityName();
            Date startTime = history.getStartTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatStartTime = sdf.format(startTime);
            String processInstanceId = history.getProcessInstanceId();//业务编号
            stringStringHashMap.put(processInstanceId, processInstanceId);
            String description = "无";
            List<Comment> taskComments = taskService.getTaskComments(taskId);
            if (taskComments.size() > 0) {
                description = taskComments.get(0).getFullMessage();
            }

            TaskModel taskModel = new TaskModel();
            taskModel.setAssignee(assignee);
            taskModel.setTaskId(taskId);
            taskModel.setCreateTime(formatStartTime);
            taskModel.setName(activityName);
            taskModel.setProcessInstanceId(processInstanceId);
            taskModel.setDescription(description);
            taskModels.add(taskModel);
        }

        System.out.println("已完成的任务 -->>" + stringStringHashMap);
    }


    @Test
    public void queryBusinessInfos() {
        String sUsername = "lisi";
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
            businessInfo.setProcessInstanceId(historicProcessInstance.getId());
            if (businessInfo.getUsername().equals(sUsername)) {
                businessInfos.add(businessInfo);
            }
        }
        System.out.println("businessInfos = " + businessInfos);
    }


    @Test
    public void QueryBusinessInfos() {
        String username = "chenyu";
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<Execution> list = runtimeService.createExecutionQuery().list();
        ArrayList<BusinessInfo> businessInfos = new ArrayList<>();
        for (Execution execution : list) {
            String processInstanceId = execution.getProcessInstanceId();//业务编号
//            Map<String, Object> variables = runtimeService.getVariables(execution.getId());
//            String name = (String) runtimeService.getVariable(execution.getId(), "username");
            String businessName = (String) runtimeService.getVariable(execution.getId(), "businessName");
            System.out.println("businessName = " + businessName);
//            String duringTime = (String) runtimeService.getVariable(execution.getId(), "duringTime");
//            float rate = (Float) runtimeService.getVariable(execution.getId(), "rate");
//            String amount = (String) runtimeService.getVariable(execution.getId(), "amount");
////            System.out.println("execution.getTenantId() = " + execution.getTenantId());
////            System.out.println("execution.getActivityId() = " + execution.getActivityId());
//            boolean businessStatus = execution.isEnded();
//            BusinessInfo businessInfo = new BusinessInfo();
//            businessInfo.setProcessInstanceId(processInstanceId);
//            businessInfo.setUsername(name);
//            businessInfo.setBusinessName(businessName);
//            businessInfo.setDuringTime(duringTime);
//            businessInfo.setRate(rate);
//            businessInfo.setAmount(amount);
//            businessInfo.setBusinessStatus(businessStatus ? 1 : 0);
//            businessInfos.add(businessInfo);
//            System.out.println("name --- >>> " + name);
        }
        System.out.println("result: " + businessInfos.toString());
    }
}
