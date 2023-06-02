package com.prettyant.loan.data.repository.network;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.prettyant.loan.api.ApiUtil;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.data.bean.BusinessInfo;
import com.prettyant.loan.data.bean.BusinessInfosResponse;
import com.prettyant.loan.data.bean.FlowPathModel;
import com.prettyant.loan.data.bean.FlowPathModelResponse;
import com.prettyant.loan.data.bean.Response;
import com.prettyant.loan.data.bean.TaskResponse;
import com.prettyant.loan.data.bean.UserInfo;
import com.prettyant.loan.util.LogUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 6:07 PM  24/05/23
 * PackageName : com.prettyant.loan.data.repository.network
 * describle :
 */
public class HttpRequestManager {
    private static final HttpRequestManager ourInstance = new HttpRequestManager();

    public static HttpRequestManager getInstance() {
        return ourInstance;
    }

    private HttpRequestManager() {
    }


    /**
     * 登录
     *
     * @param loginDataResultMutableLiveData
     * @param userInfoMutableLiveData
     */
    public void requestLoginData(MutableLiveData<Response> loginDataResultMutableLiveData, MutableLiveData<UserInfo> userInfoMutableLiveData) {
        String bodyStr = new Gson().toJson(userInfoMutableLiveData.getValue());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);
        ApiUtil.createApiService().login(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response response) {
                        LogUtil.v(LogUtil.LOG + "----->> 请求成功");
                        loginDataResultMutableLiveData.postValue(response);
                    }
                });
    }

    /**
     * 注册
     *
     * @param responseMutableLiveData 返回报文
     * @param userInfoMutableLiveData 请求报文
     */
    public void requestRegistData(MutableLiveData<Response> responseMutableLiveData, MutableLiveData<UserInfo> userInfoMutableLiveData) {
        String bodyStr = new Gson().toJson(userInfoMutableLiveData.getValue());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);
        ApiUtil.createApiService().regist(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response response) {
                        LogUtil.v(LogUtil.LOG + "----->> 请求成功");
                        responseMutableLiveData.postValue(response);
                    }
                });
    }

    /**
     * 注册
     *
     * @param responseBusiness 返回报文
     * @param businessInfo     请求报文
     */
    public void getRate(MutableLiveData<BusinessInfo> responseBusiness, BusinessInfo businessInfo) {
        String bodyStr = new Gson().toJson(businessInfo);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);
        ApiUtil.createApiService().getRate(businessInfo.getUsername(), body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BusinessInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BusinessInfo businessInfo) {
                        LogUtil.v(LogUtil.LOG + "----->> 请求成功");
                        responseBusiness.postValue(businessInfo);
                    }
                });
    }

    /**
     * 提交
     *
     * @param responseMutableLiveData 返回报文
     * @param businessInfo            请求报文
     */
    public void apply(MutableLiveData<Response> responseMutableLiveData, BusinessInfo businessInfo) {
        String bodyStr = new Gson().toJson(businessInfo);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);
        ApiUtil.createApiService().apply(businessInfo.getUsername(), body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response response) {
                        LogUtil.v(LogUtil.LOG + "----->> 请求成功");
                        responseMutableLiveData.postValue(response);
                    }
                });
    }

    /**
     * 用户业务查询接口
     *
     * @param businessInfosResponseMutableLiveData
     * @param index
     */
    public void query(MutableLiveData<BusinessInfosResponse> businessInfosResponseMutableLiveData, int index) {
        String authorization = ContantFields.username;
        ApiUtil.createApiService().userQuery(authorization, index)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BusinessInfosResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        dissmissProgressDialog();
                        LogUtil.e("查询失败>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(BusinessInfosResponse response) {
//                        dissmissProgressDialog();
                        /**
                         * code:1  success
                         * code:-1 error
                         */
                        businessInfosResponseMutableLiveData.postValue(response);
//                        if (response.code == 1) {
//                        } else {
//                        }
                    }
                });
    }
    /**
     * 查询当前任务
     * @param taskResponseMutableLiveData
     * @param index
     */
    public void queryCurrentTask(MutableLiveData<TaskResponse> taskResponseMutableLiveData,int index) {
        String authorization = ContantFields.username;
        ApiUtil.createApiService().queryCurrentTask(authorization,index)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<TaskResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("查询成功>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(TaskResponse response) {
                        /**
                         * code:1  success
                         * code:-1 error
                         */
                        taskResponseMutableLiveData.postValue(response);

                    }
                });
    }

    /**
     * 查询已完成的任务
     * @param taskResponseMutableLiveData
     * @param index  索引
     */
    public void queryFinishedTask(MutableLiveData<TaskResponse> taskResponseMutableLiveData,int index) {
        String authorization = ContantFields.username;
        ApiUtil.createApiService().queryFinishedTask(authorization,index)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<TaskResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("查询失败>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(TaskResponse response) {
                        /**
                         * code:1  success
                         * code:-1 error
                         */
                       taskResponseMutableLiveData.postValue(response);
                    }
                });
    }

    /**
     *
     * @param taskId
     * @param processInstanceId
     * @param message
     * @param approved
     */
    public void dealTask(MutableLiveData<Response> responseMutableLiveData,String taskId, String processInstanceId, String message, boolean approved) {
        ApiUtil.createApiService().dealTask(taskId,processInstanceId,message,approved)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("查询成功>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        /**
                         * code:1  success
                         * code:-1 error
                         */
                        responseMutableLiveData.postValue(response);

                    }
                });
    }
    /**
     * 查询历史
     *
     * @param flowPathModelMutableLiveData
     * @param processInstanceId
     */
    public void queryHistory(MutableLiveData<FlowPathModelResponse> flowPathModelMutableLiveData, String processInstanceId) {
        String authorization = ContantFields.username;
        ApiUtil.createApiService().queryHistory(authorization,processInstanceId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<FlowPathModelResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        dissmissProgressDialog();
                        FlowPathModelResponse flowPathModelResponse = new FlowPathModelResponse();
                        flowPathModelResponse.code = -1;
                        flowPathModelResponse.message = e.getMessage();
                        flowPathModelMutableLiveData.postValue(flowPathModelResponse);
                    }

                    @Override
                    public void onNext(FlowPathModelResponse response) {
//                        dissmissProgressDialog();
                        /**
                         * code:1  success
                         * code:-1 error
                         */
                        flowPathModelMutableLiveData.postValue(response);

                    }
                });
    }

    /**
     * 登出
     * @param responseMutableLiveData
     */
    public void logOut(MutableLiveData<Response> responseMutableLiveData) {
        String authorization = ContantFields.username;
        ApiUtil.createApiService().logOut(authorization)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("登出失败>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        /**
                         * code:1  success
                         * code:-1 error
                         */
                        responseMutableLiveData.postValue(response);
                    }
                });
    }
}
