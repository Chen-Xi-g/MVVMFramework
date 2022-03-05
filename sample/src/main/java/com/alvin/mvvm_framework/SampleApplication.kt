package com.alvin.mvvm_framework

import com.alvin.mvvm.BaseApplication
import com.alvin.mvvm.BuildConfig
import com.alvin.mvvm.help.GlobalMVVMBuilder
import com.alvin.mvvm_framework.base.setting.BaseActivitySetting
import com.alvin.mvvm_framework.base.setting.BaseFragmentSetting

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
    }

    override fun isLogDebug(): Boolean {
        // 是否显示日志
        return BuildConfig.DEBUG
    }
}