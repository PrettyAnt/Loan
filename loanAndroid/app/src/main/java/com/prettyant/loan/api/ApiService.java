package com.prettyant.loan.api;

import com.prettyant.loan.model.bean.BusinessInfo;
import com.prettyant.loan.model.bean.BusinessInfosResponse;
import com.prettyant.loan.model.bean.FlowPathModelResponse;
import com.prettyant.loan.model.bean.Response;
import com.prettyant.loan.model.bean.TaskResponse;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chenyu
 */
public interface ApiService {

    String API_ROOT = "http://192.168.140.62:8100/";

    /**
     * 登录
     *
     * @return
     */
    @Headers("Content-Type:application/json")
//    @FormUrlEncoded
    @POST("login")
    Observable<Response> login(@Body RequestBody requestBody);
    //@Header("Authorization") String authorization,

    /**
     * 注册
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("regist")
    Observable<Response> regist(@Body RequestBody requestBody);

    /**
     * 获取利率
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("getRate")
    Observable<BusinessInfo> getRate(@Header("Authorization") String authorization, @Body RequestBody requestBody);


    /**
     * 用户申请贷款业务提交接口
     *
     * @param authorization
     * @param requestBody
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("apply")
    Observable<Response> apply(@Header("Authorization") String authorization, @Body RequestBody requestBody);

    /**
     * 用户业务查询接口
     *
     * @param authorization
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("userQuery")
    Observable<BusinessInfosResponse> userQuery(@Header("Authorization") String authorization, @Query("index") int index);

    /**
     * 查询某个业务的历史流程
     * @param authorization
     * @param processInstanceId
     * @return
     */
    @Headers("Content-Type:application/json")
//    @FormUrlEncoded
    @POST("queryHistory")
    Observable<FlowPathModelResponse> queryHistory(@Header("Authorization") String authorization, @Query("processInstanceId") String processInstanceId);

    /**
     * 登出接口
     * @param authorization
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("logOut")
    Observable<Response> logOut(@Header("Authorization") String authorization);

    /**
     * 操作员查询已完成的任务
     *
     * @param authorization
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("queryFinishedTask")
    Observable<TaskResponse> queryFinishedTask(@Header("Authorization") String authorization, @Query("index") int index);

    /**
     * 操作员查询当前的任务
     *
     * @param authorization
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("queryCurrentTask")
    Observable<TaskResponse> queryCurrentTask(@Header("Authorization") String authorization, @Query("index") int index);
    /**
     * 处理当前的任务
     *
     * @param taskId   任务id
     * @param approved 同意/拒绝
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("dealTask")
    Observable<Response> dealTask( @Query("taskId") String taskId,
                                   @Query("processInstanceId") String processInstanceId,
                                   @Query("message") String message,
                                   @Query("approved") boolean approved);
}
