package com.fangyi.fymvp.base;

import com.fangyi.fymvp.callback.JsonBaseCallback;
import com.fangyi.fymvp.mvp.IModel;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.socks.library.KLog;

import java.util.Map;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/24
 * 说    明：
 * ================================================
 */
public class BaseModel implements IModel {


    @Override
    public <T> GetRequest<T> get(String url, Map<String, String> params) {
        return OkGo.<T>get(url)
                .params(params);
    }

    @Override
    public <T> PostRequest<T> post(String url, Map<String, String> params) {
        return OkGo.<T>post(url)
                .params(params);
    }
}
