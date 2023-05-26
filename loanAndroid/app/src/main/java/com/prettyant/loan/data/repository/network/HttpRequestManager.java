package com.prettyant.loan.data.repository.network;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.prettyant.loan.api.ApiUtil;
import com.prettyant.loan.model.bean.Response;
import com.prettyant.loan.model.bean.UserInfo;
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
public class HttpRequestManager implements IRemoteRequest {
    private static final HttpRequestManager ourInstance = new HttpRequestManager();

   public static HttpRequestManager getInstance() {
        return ourInstance;
    }

    private HttpRequestManager() {
    }


    /**
     * 登录
     * @param loginDataResultMutableLiveData
     * @param userInfoMutableLiveData
     */
    @Override
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
                        LogUtil.v(LogUtil.LOG+"----->> 请求成功");
                        loginDataResultMutableLiveData.postValue(response);
                    }
                });
    }

    /**
     * 注册
     * @param responseMutableLiveData  返回报文
     * @param userInfoMutableLiveData  请求报文
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
                        LogUtil.v(LogUtil.LOG+"----->> 请求成功");
                        responseMutableLiveData.postValue(response);
                    }
                });
    }
}
