package com.alvin.mvvm_framework.base.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer


/**
 * <h3> 作用类描述：公共参数拦截器</h3>
 *
 * @Package :        com.alvin.mvvm_framework.base.interceptor
 * @Date :           2021/9/23-17:32
 * @author 高国峰
 */
class ParameterInterceptor : Interceptor {

    companion object {

        // 公共 Headers 添加
        private var headerParamsMap: MutableMap<String, String?> = mutableMapOf()

        // 消息头 集合形式，一次添加一行
        private var headerLinesList: MutableList<String> = mutableListOf()

        fun addParams(
            params: (
                headerParamsMap: MutableMap<String, String?>,
                headerLinesList: MutableList<String>
            ) -> Unit
        ) {
            params.invoke(
                headerParamsMap,
                headerLinesList
            )
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val requestBuilder = request.newBuilder()
        if (!request.url.toString().contains("login")) {
            /* 添加请求头参数 */
            // 添加请求头参数
            val headerBuilder = request.headers.newBuilder()
            headerParamsMap.forEach {
                it.value?.let { value ->
                    headerBuilder.add(it.key, value)
                }
            }

            // 一次添加一整行请求头
            headerLinesList.forEach {
                headerBuilder.add(it)
            }

//            在此处存储Token
//            headerBuilder.add("token", xxx)
            requestBuilder.headers(headerBuilder.build())
        }
        /* 添加请求体参数 */
        request = requestBuilder.build()
        return chain.proceed(request)
    }

    /**
     * Body转String
     *
     * @param request
     * @return
     */
    private fun bodyToString(request: RequestBody): String {
        val buffer = Buffer()
        request.writeTo(buffer)
        return buffer.readUtf8()
    }

    /**
     * 确认是否是 post 请求
     * @param request 发出的请求
     * @return true 需要注入公共参数
     */
    private fun isPost(request: Request?): Boolean {
        if (request == null) {
            return false
        }
        if (request.method != "POST") {
            return false
        }
        val body = request.body ?: return false
        val mediaType = body.contentType() ?: return false

        return mediaType.subtype != "x-www-form-urlencoded"
    }
}