package com.alvin.mvvm_framework.data

import com.alvin.mvvm_network.HttpManager

/**
 * <h3> 作用类描述：全局常量</h3>
 *
 * @Package :        com.alvin.mvvm_framework.data
 * @Date :           2022/1/8
 * @author 高国峰
 */
object Constant {
    val service = HttpManager.instance.instanceRetrofit(AppService::class.java)
}