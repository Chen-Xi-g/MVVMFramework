package com.alvin.mvvm_network.application

import com.alvin.mvvm_network.HttpManager
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

/**
 * <h3> 作用类描述：初始化网络请求</h3>
 *
 * @Package :        com.alvin.mvvm_network.application
 * @Date :           2022/4/3
 * @author 高国峰
 */
interface INetWork {

    /**
     * 初始化网络请求
     *
     * @param baseUrl String 基础url
     * @param timeUnit TimeUnit 时间单位
     * @param timeout Long 时间（读取、写入、连接）
     * @param retryOnConnection Boolean 是否重试 默认true
     * @param domain [@kotlin.ExtensionFunctionType] Function1<MutableMap<String, String>, Unit> 多域名配置
     * @param hideVerticalLine Boolean 是否隐藏网络请求中的竖线 默认false
     * @param requestTag String 请求标识
     * @param responseTag String 响应标识
     * @param isDebug Boolean 是否打印日志 默认false
     * @param interceptors Array<out Interceptor> 拦截器
     */
    fun initNetwork(
        baseUrl: String,
        timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
        timeout: Long = 30 * 1000,
        retryOnConnection: Boolean = true,
        domain: MutableMap<String, String>.() -> Unit = {},
        hideVerticalLine: Boolean = false,
        requestTag: String = "HTTP Request 请求参数",
        responseTag: String = "HTTP Response 响应结果",
        isDebug: Boolean = false,
        vararg interceptors: Interceptor
    ) {
        initNetwork {
            setBaseUrl(baseUrl) // 基础Url
            setTimeUnit(timeUnit) // 时间类型 秒， 框架默认值 毫秒
            setReadTimeout(timeout) // 读取超时 30s， 框架默认值 10000L
            setWriteTimeout(timeout) // 写入超时 30s， 框架默认值 10000L
            setConnectTimeout(timeout) // 链接超时 30s，框架默认值 10000L
            setRetryOnConnectionFailure(retryOnConnection) // 超时自动重连， 框架默认值 true
            setDomain(domain) // 多域名配置
            setLoggingInterceptor(isDebug, hideVerticalLine, requestTag, responseTag) // 日志拦截器
            setInterceptorList(hashSetOf(*interceptors)) // 拦截器集合
        }
    }

    /**
     * 初始化网络请求
     *
     * @param func [@kotlin.ExtensionFunctionType] Function1<HttpManager, Unit> 函数体
     */
    fun initNetwork(func: HttpManager.() -> Unit) {
        HttpManager.instance.setting {
            func.invoke(this)
        }
    }
}