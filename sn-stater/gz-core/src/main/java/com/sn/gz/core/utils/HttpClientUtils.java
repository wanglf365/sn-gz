/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: HttpClient
 * Author:   DJ
 * Date:     2018/4/17 16:58
 *
 * @since 1.0.0
 */
package com.sn.gz.core.utils;

import com.sn.gz.core.BusinessException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * http客户端
 * @author luffy
 * Date 2018/4/17
 * @since 1.0.0
 */
@Slf4j
public class HttpClientUtils {
    private static OkHttpClient client = new OkHttpClient.Builder()
            //设置连接超时时间
            .connectTimeout(10, TimeUnit.SECONDS)
            //设置读取超时时间
            .readTimeout(20, TimeUnit.SECONDS)
            .build();



    public static String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .build();
        return execute(request);
    }

    /**
     * post请求
     * @param url   url请求地址
     * @param param 请求参数
     * @return java.lang.String
     * @author post
     * Date 2018/4/17 17:08
     * @since 1.0.0
     */
    public static String post(String url, String param) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, param);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .build();
        return execute(request);
    }

    /**
     * post请求
     * @param url   url请求地址
     * @param requestBody 请求参数
     * @return java.lang.String
     * @author post
     * Date 2018/4/17 17:08
     * @since 1.0.0
     */
    public static String post(String url, RequestBody requestBody) {

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .build();
        return execute(request);
    }

    /**
     * post请求,form提交
     * @param url      url请求地址
     * @param formBody 请求参数
     * @return java.lang.String
     * @author post
     * Date 2018/4/17 17:08
     * @since 1.0.0
     */
    public static String postForm(String url, FormBody formBody) {
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader("Content-Type", "x-www-form-urlencoded")
                .addHeader("Cache-Control", "no-cache")
                .build();
        return execute(request);
    }

    /**
     * 执行
     * @param request 请求
     * @return java.lang.String
     * @author luffy
     * 2018/7/20 11:55
     * @since 1.0.0
     */
    public static String execute(Request request) {
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new BusinessException("发送POST请求异常");
        }
    }
}
