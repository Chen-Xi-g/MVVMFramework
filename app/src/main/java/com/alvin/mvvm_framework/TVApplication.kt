package com.alvin.mvvm_framework

import android.app.Application
import com.alvin.mvvm.BaseApplication
import com.alvin.mvvm.help.GlobalMVVMBuilder
import com.alvin.mvvm_framework.setting.ActivitySetting
import com.alvin.mvvm_framework.setting.FragmentSetting
import com.alvin.mvvm_network.HttpManager
import xyz.doikki.videoplayer.ijk.IjkPlayerFactory
import xyz.doikki.videoplayer.player.VideoViewConfig
import xyz.doikki.videoplayer.player.VideoViewManager
import java.util.concurrent.TimeUnit

/**
 * <h3> 作用类描述：初始化Application</h3>
 *
 * @Package :        com.alvin.mvvm_framework
 * @Date :           2022/1/5
 * @author Alvin
 */
class TVApplication : BaseApplication() {

    companion object{
        var application: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        // 设置全局Activity和Fragment
        GlobalMVVMBuilder.initSetting(
            ActivitySetting(),
            FragmentSetting()
        )

        HttpManager.instance.setting {
            // 设置网络属性
            setTimeUnit(TimeUnit.SECONDS)
            setWriteTimeout(30)
            setConnectTimeout(30)
            setBaseUrl("https://api.iyk0.com/")
            // 多域名
//            setDomain {
//            }
        }

        VideoViewManager.setConfig(
            VideoViewConfig.newBuilder()
                .setPlayerFactory(IjkPlayerFactory.create())
                .build()
        )
    }
}