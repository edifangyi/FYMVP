package com.fangyi.sample.main;


import com.fangyi.fymvp.base.BaseModel;
import com.fangyi.fymvp.basebean.BaseResponse;
import com.fangyi.sample.api.ApiUrls;
import com.fangyi.sample.bean.LoginBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;

import java.util.Map;

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
public class MainModel extends BaseModel implements MainContract.Model {


    @Override
    public GetRequest<BaseResponse<LoginBean>> doSuccess(Map<String, String> params) {

        return OkGo.<BaseResponse<LoginBean>>get(ApiUrls.doSuccess)
                .params(params)
                .tag(this);
    }

}