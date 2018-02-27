package com.fangyi.sample.module_mvp.other.callback;



import com.fangyi.fymvp.bean.BaseResponse;
import com.fangyi.fymvp.bean.SimpleResponse;
import com.fangyi.fymvp.utils.Convert;

import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/1/24
 * 说    明：默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * ================================================
 */
public abstract class JsonBaseCallback<T> extends AbsCallback<T> {

    private Type type;
    private Class<T> clazz;

    public JsonBaseCallback() {
    }

    public JsonBaseCallback(Type type) {
        this.type = type;
    }

    public JsonBaseCallback(Class<T> clazz) {
        this.clazz = clazz;
    }


    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
    }


    /**
     * 异常
     *
     * @param response
     */
    @Override
    public void onError(Response<T> response) {

        Throwable exception = response.getException();

        if (exception instanceof UnknownHostException || exception instanceof ConnectException) {
            response.setException(new Throwable("网络连接失败，请连接网络！"));
        } else if (exception instanceof SocketTimeoutException) {
            response.setException(new Throwable("网络请求超时"));
        } else if (exception instanceof HttpException) {
            response.setException(new Throwable("服务器响应码404和500了，知道该怎么办吗？"));
        } else if (exception instanceof StorageException) {
            response.setException(new Throwable("sd卡不存在或者没有权限"));
        } else if (exception instanceof IllegalStateException) {
            response.setException(new Throwable("啊啊啊"));
        } else if (exception instanceof IllegalArgumentException) {
            response.setException(new Throwable("出现意外了！"));
        }

        super.onError(response);
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
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
