package com.alvin.mvvm_framework

import com.alvin.mvvm.BaseApplication
import com.alvin.mvvm.BuildConfig
import com.alvin.mvvm.help.GlobalMVVMBuilder
import com.alvin.mvvm_framework.base.Constant
import com.alvin.mvvm_framework.base.interceptor.ParameterInterceptor
import com.alvin.mvvm_framework.base.interceptor.ResponseInterceptor
import com.alvin.mvvm_framework.base.setting.BaseActivitySetting
import com.alvin.mvvm_framework.base.setting.BaseFragmentSetting
import com.alvin.mvvm_network.HttpManager
import java.util.concurrent.TimeUnit

/**
 * <h3> 作用类描述：Application</h3>
 *
 * @Package :        com.alvin.mvvm_framework
 * @Date :           2022/3/4
 * @author 高国峰
 */
class SampleApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        GlobalMVVMBuilder.initSetting(BaseActivitySetting(), BaseFragmentSetting())
        initHttpManager()
    }

    private fun initHttpManager() {
        // 参数拦截器
        HttpManager.instance.setting {
            // 设置网络属性
            setTimeUnit(TimeUnit.SECONDS) // 时间类型 秒
            setReadTimeout(30) // 读取超时 30s
            setWriteTimeout(30) // 写入超时 30s
            setConnectTimeout(30) // 链接超时 30s
            setRetryOnConnectionFailure(true) // 超时自动重连
            setBaseUrl("https://www.wanandroid.com") // 默认域名
            // 多域名配置
            setDomain {
                Constant.domainList.forEach { map ->
                    map.forEach {
                        if (it.key.isNotEmpty() && it.value.isNotEmpty()) {
                            put(it.key, it.value)
                        }
                    }
                }
            }
            setLoggingInterceptor(
                isDebug = BuildConfig.DEBUG,
                hideVerticalLine = true,
                requestTag = "HTTP Request 请求参数",
                responseTag = "HTTP Response 返回参数"
            )
            // 添加拦截器
            setInterceptorList(hashSetOf(ResponseInterceptor(), ParameterInterceptor()))
        }
    }

    override fun isLogDebug(): Boolean {
        // 是否显示日志
        return BuildConfig.DEBUG
    }
}