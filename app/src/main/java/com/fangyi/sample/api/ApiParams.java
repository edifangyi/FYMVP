package com.fangyi.sample.api;

import java.util.HashMap;
import java.util.Map;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/25
 * 说    明：
 * ================================================
 */
public class ApiParams {

    //登陆
    public static Map<String, String> doSuccess(String userName, String userPwd) {
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("userPwd", userPwd);
        return map;
    }
}
