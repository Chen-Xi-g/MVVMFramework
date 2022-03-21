package com.alvin.mvvm_framework.base.interceptor

import com.alvin.mvvm.utils.LogUtil
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

/**
 * <h3> 作用类描述：返回体拦截器</h3>
 *
 * @Package :        com.alvin.mvvm_framework.base.interceptor
 * @Date :           2021/9/23-17:27
 * @author 高国峰
 */
class ResponseInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // 获取返回体，从中获取Cookie持久化登录。
        var proceed = chain.proceed(chain.request())
        if (proceed.body != null) {
            // 拦截响应体，获取code和Message判断Token是否过期
            var json = proceed.body?.string() ?: ""
            // 拦截非法响应体，并且修改
            if (!json.startsWith("{") && !json.startsWith("[")) {
                json = "{}"
            }
            LogUtil.d(json)
            proceed = proceed.newBuilder()
                .body(json.toResponseBody())
                .build()
        }
        return proceed
    }
}