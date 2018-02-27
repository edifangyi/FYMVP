package com.fangyi.sample.module_mvp.other.callback;


import com.fangyi.fymvp.bean.BaseResponse;
import com.fangyi.fymvp.bean.SimpleResponse;
import com.fangyi.fymvp.utils.Convert;

import com.google.gson.stream.JsonReader;
import com.lzy.okgo.convert.Converter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/25
 * 说    明：Rx 默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * ================================================
 */
public class JsonBaseConvert<T> implements Converter<T> {

    private Type type;
    private Class<T> clazz;

    public JsonBaseConvert() {
    }

    public JsonBaseConvert(Type type) {
        this.type = type;
    }

    public JsonBaseConvert(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        if (type == null) {
            if (clazz == null) {
                // 如果没有通过构造函数传进来，就自动解析父类泛型的真实类型（有局限性，继承后就无法解析到）
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                return parseClass(response, clazz);
            }
        }

        if (type instanceof ParameterizedType) {
            return parseParameterizedType(response, (ParameterizedType) type);
        } else if (type instanceof Class) {
            return parseClass(response, (Class<?>) type);
        } else {
            return parseType(response, type);
        }
    }


    private T parseClass(okhttp3.Response response, Class<?> rawType) throws Exception {
        if (rawType == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        if (rawType == String.class) {
            //noinspection unchecked
            return (T) body.string();
        } else if (rawType == JSONObject.class) {
            //noinspection unchecked
            return (T) new JSONObject(body.string());
        } else if (rawType == JSONArray.class) {
            //noinspection unchecked
            return (T) new JSONArray(body.string());
        } else {
            T t = Convert.fromJson(jsonReader, rawType);
            response.close();
            return t;
        }
    }

    private T parseParameterizedType(okhttp3.Response response, ParameterizedType type) throws Exception {
        if (type == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        Type rawType = type.getRawType();                     // 泛型的实际类型
        Type typeArgument = type.getActualTypeArguments()[0]; // 泛型的参数
        if (rawType != BaseResponse.class) {
            // 泛型格式如下： new JsonBaseCallback<外层BaseBean<内层JavaBean>>(this)
            T t = Convert.fromJson(jsonReader, type);
            response.close();
            return t;
        } else {
            if (typeArgument == Void.class) {
                // 泛型格式如下： new JsonBaseCallback<BaseResponse<Void>>(this)
                SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
                response.close();
                //noinspection unchecked
                return (T) simpleResponse.toBaseResponse();
            } else {
                // 泛型格式如下： new JsonBaseCallback<BaseResponse<内层JavaBean>>(this)
                BaseResponse baseResponse = Convert.fromJson(jsonReader, type);
                response.close();
                int code = baseResponse.code;
                //这里的0是以下意思
                //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
                if (code == 200) {
                    //noinspection unchecked
                    return (T) baseResponse;
                } else if (code == 201) {
                    throw new IllegalStateException("用户授权信息无效");
                } else if (code == 202) {
                    throw new IllegalStateException("用户收取信息已过期");
                } else {
                    //直接将服务端的错误信息抛出，onError中可以获取
                    throw new IllegalStateException("错误代码：" + code + "，错误信息：" + baseResponse.message);
                }
            }
        }
    }


    private T parseType(okhttp3.Response response, Type type) throws Exception {
        if (type == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;

        JsonReader jsonReader = new JsonReader(body.charStream());
        // 泛型格式如下： new JsonBaseCallback<任意JavaBean>(this)
        T t = Convert.fromJson(jsonReader, type);
        response.close();
        return t;
    }
}
