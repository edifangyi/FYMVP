package com.fangyi.fymvp.mvp;


import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.util.Map;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/25
 * ================================================
 */
public interface IModel {

    interface Listener<Result> {
        void onResult(Result result);
        void onError(String mssage);
    }

    <T> GetRequest<T> get(String url, Map<String, String> params);

    <T> PostRequest<T> post(String url, Map<String, String> params);
}
