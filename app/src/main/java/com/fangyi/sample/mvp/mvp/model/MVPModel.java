package com.fangyi.sample.mvp.mvp.model;


import com.fangyi.fymvp.base.BaseModel;
import com.fangyi.fymvp.basebean.BaseResponse;
import com.fangyi.sample.mvp.api.ApiUrls;
import com.fangyi.sample.mvp.bean.LoginBean;
import com.fangyi.sample.mvp.mvp.contract.MVPContract;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;

import java.util.Map;

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
public class MVPModel extends BaseModel implements MVPContract.Model {


    @Override
    public GetRequest<BaseResponse<LoginBean>> doSuccess(Map<String, String> params) {
        return OkGo.<BaseResponse<LoginBean>>get(ApiUrls.doSuccess)
                .params(params)
                .tag(this);
    }
}