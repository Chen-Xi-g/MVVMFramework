package com.alvin.base_mvvm.base

import android.app.Application
import com.alvin.mvvm.base.application.IMVVM
import com.alvin.mvvm.help.DefaultSettingActivity
import com.alvin.mvvm.help.DefaultSettingFragment
import com.alvin.mvvm.help.ISettingBaseActivity
import com.alvin.mvvm.help.ISettingBaseFragment
import com.alvin.mvvm_network.application.INetWork
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

/**
 * <h3> 作用类描述：初始化MVVM与MVVM_NETWORK</h3>
 *
 * @Package :        com.alvin.base_mvvm.base
 * @Date :           2022/4/3
 * @author 高国峰
 */
interface IBaseMVVM : IMVVM, INetWork {

    /**
     * 初始化框架
     *
     * @param application Application
     * @param baseUrl String 基础Url
     * @param iSettingBaseActivity ISettingBaseActivity 自定义Activity样式
     * @param iSettingBaseFragment ISettingBaseFragment 自定义Fragment样式
     * @param isDebug Boolean 是否是调试模式
     * @param timeUnit TimeUnit 时间单位
     * @param timeout Long 时间
     * @param retryOnConnection Boolean 是否重试
     * @param domain [@kotlin.ExtensionFunctionType] Function1<MutableMap<String, String>, Unit> 多域名配置
     * @param hideVerticalLine Boolean 是否隐藏垂直线
     * @param requestTag String 请求标识
     * @param responseTag String 响应标识
     * @param interceptors Array<out Interceptor> 拦截器
     */
    fun initBaseMVVM(
        application: Application,
        baseUrl: String,
        iSettingBaseActivity: ISettingBaseActivity = DefaultSettingActivity(),
        iSettingBaseFragment: ISettingBaseFragment = DefaultSettingFragment(),
        isDebug: Boolean = false,
        timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
        timeout: Long = 30 * 1000,
        retryOnConnection: Boolean = true,
        domain: MutableMap<String, String>.() -> Unit = {},
        hideVerticalLine: Boolean = false,
        requestTag: String = "HTTP Request 请求参数",
        responseTag: String = "HTTP Response 响应结果",
        vararg interceptors: Interceptor
    ) {
        initMVVM(application, iSettingBaseActivity, iSettingBaseFragment, isDebug)
        initNetwork(
            baseUrl,
            timeUnit,
            timeout,
            retryOnConnection,
            domain,
            hideVerticalLine,
            requestTag,
            responseTag,
            isDebug,
            *interceptors
        )
    }
}