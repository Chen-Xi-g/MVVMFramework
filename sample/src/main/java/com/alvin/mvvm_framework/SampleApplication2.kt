package com.alvin.mvvm_framework

import android.app.Application
import com.alvin.base_mvvm.base.IBaseMVVM
import com.alvin.mvvm_framework.base.Constant
import com.alvin.mvvm_framework.base.interceptor.ParameterInterceptor
import com.alvin.mvvm_framework.base.interceptor.ResponseInterceptor
import com.alvin.mvvm_framework.base.setting.BaseActivitySetting
import com.alvin.mvvm_framework.base.setting.BaseFragmentSetting
import java.util.concurrent.TimeUnit

/**
 * <h3> 作用类描述：集成BaseMVVM的初始化</h3>
 *
 * @Package :        com.alvin.mvvm_framework
 * @Date :           2022/4/3
 * @author 高国峰
 */
class SampleApplication2 : Application(), IBaseMVVM {
    override fun onCreate() {
        super.onCreate()
        // 使用默认配置
        initBaseMVVM(this, "https://www.wanandroid.com/")
        // 自定义配置
        initBaseMVVM(
            this,
            "https://www.wanandroid.com/",
            BaseActivitySetting(),
            BaseFragmentSetting(),
            isDebug = BuildConfig.DEBUG,
            timeUnit = TimeUnit.SECONDS,
            timeout = 30,
            retryOnConnection = true,
            domain = {
                Constant.domainList.forEach { map ->
                    map.forEach {
                        if (it.key.isNotEmpty() && it.value.isNotEmpty()) {
                            put(it.key, it.value)
                        }
                    }
                }
            },
            // 是否打印
            hideVerticalLine = true,
            // 请求标识
            requestTag = "Request 请求参数",
            // 响应标识
            responseTag = "Response 响应结果",
            // 拦截器
            ResponseInterceptor(),
            ParameterInterceptor()
        )
    }
}