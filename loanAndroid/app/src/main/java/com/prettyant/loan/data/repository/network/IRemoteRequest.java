package com.prettyant.loan.data.repository.network;

import androidx.lifecycle.MutableLiveData;

import com.prettyant.loan.data.bean.Response;
import com.prettyant.loan.data.bean.UserInfo;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 5:57 PM  24/05/23
 * PackageName : com.prettyant.loan.repository.network
 * describle :
 */
public interface IRemoteRequest {
    /**
     * 请求服务器 登录
     *
     * @param loginDataResultMutableLiveData  返回的报文
     *
     * @param userInfoMutableLiveData  提交的报文
     */
    void requestLoginData(MutableLiveData<Response> loginDataResultMutableLiveData, MutableLiveData<UserInfo>userInfoMutableLiveData);
}
