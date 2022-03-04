package com.alvin.mvvm

import android.app.Application
import com.alvin.mvvm.manager.LifecycleCallback
import com.alvin.mvvm.utils.LogUtil
import me.jessyan.autosize.AutoSizeConfig

/**
 * <h3> 作用类描述：Application的基类，自动注册屏幕适配和生命周期监听。</h3>
 *
 * 项目中继承BaseApplication可实现自动注册，或者自行实现AutoSize和生命周期初始化
 *
 * @Package :        com.alvin.mvvm
 * @Date :           2021/9/2-18:46
 * @author Alvin
 */
abstract class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 屏幕适配初始化
        AutoSizeConfig.getInstance().setCustomFragment(true)
            .setExcludeFontScale(true)
            .unitsManager
            .setSupportDP(true).isSupportSP = true
        // 注册Activity生命监听
        registerActivityLifecycleCallbacks(LifecycleCallback())
        if (isLogDebug()) {
            LogUtil.closeLog()
        }
    }

    /**
     * 是否打印Log日志
     *
     * @return Boolean
     */
    abstract fun isLogDebug(): Boolean
}