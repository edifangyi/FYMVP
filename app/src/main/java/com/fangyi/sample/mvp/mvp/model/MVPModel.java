package com.fangyi.sample.mvp.mvp.model;


import com.fangyi.fymvp.base.BaseModel;
import com.fangyi.fymvp.basebean.BaseResponse;

import com.fangyi.sample.mvp.bean.LoginBean;
import com.fangyi.sample.mvp.callback.JsonBaseCallback;
import com.fangyi.sample.mvp.mvp.contract.MVPContract;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.Map;

/**
 * Create By admin On 2017/7/11
 * 功能：
 */
public class MVPModel extends BaseModel implements MVPContract.Model {


    @Override
    public void doSuccer(String doSuccess, Map<String, String> stringStringMap, Listener<BaseResponse<LoginBean>> listener) {

        OkGo.<BaseResponse<LoginBean>>post(doSuccess)
                .params(stringStringMap)
                .execute(new JsonBaseCallback<BaseResponse<LoginBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<LoginBean>> response) {

                        listener.onResult(response.body());
                    }


                    @Override
                    public void onError(Response<BaseResponse<LoginBean>> response) {
                        super.onError(response);
                    }
                });

    }
}