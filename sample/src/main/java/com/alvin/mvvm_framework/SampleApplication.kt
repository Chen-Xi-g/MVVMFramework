package com.alvin.mvvm_framework

import android.app.Application
import com.alvin.mvvm.base.application.IMVVM
import com.alvin.mvvm_framework.base.Constant
import com.alvin.mvvm_framework.base.interceptor.ParameterInterceptor
import com.alvin.mvvm_framework.base.interceptor.ResponseInterceptor
import com.alvin.mvvm_framework.base.setting.BaseActivitySetting
import com.alvin.mvvm_framework.base.setting.BaseFragmentSetting
import com.alvin.mvvm_network.application.INetWork
import com.blankj.utilcode.util.Utils
import java.util.concurrent.TimeUnit

/**
 * <h3> 作用类描述：Application</h3>
 *
 * @Package :        com.alvin.mvvm_framework
 * @Date :           2022/3/4
 * @author 高国峰
 */
class SampleApplication : Application(), IMVVM, INetWork {

    override fun onCreate() {
        super.onCreate()
        // 初始化MVVM框架
        initMVVM(this, BaseActivitySetting(), BaseFragmentSetting(), BuildConfig.DEBUG)
        /* 两种配置网络请求，选择其一即可 */
        // 初始化网络请求，默认配置
//        initNetwork(baseUrl = "https://www.wanandroid.com")
        // 初始化网络请求, 自定义配置
        initNetwork(
            application = this,
            // 基础url
            baseUrl = "https://www.wanandroid.com",
            // 时间单位
            timeUnit = TimeUnit.SECONDS,
            // 时间
            timeout = 30,
            // 是否重试
            retryOnConnection = true,
            // 多域名配置
            domain = {
                Constant.domainList.forEach { map ->
                    map.forEach {
                        if (it.key.isNotEmpty() && it.value.isNotEmpty()) {
                            put(it.key, it.value)
                        }
                    }
                }
            },
            // 是否隐藏网络请求中的竖线
            hideVerticalLine = true,
            // 请求标识
            requestTag = "Request 请求参数",
            // 响应表示
            responseTag = "Response 响应结果",
            // 是否Debug
            isDebug = BuildConfig.DEBUG,
            // 拦截器
            ResponseInterceptor(),
            ParameterInterceptor()
        )
        Utils.init(this)
    }
}