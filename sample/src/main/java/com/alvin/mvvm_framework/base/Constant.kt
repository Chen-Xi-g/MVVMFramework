package com.alvin.mvvm_framework.base

import com.alvin.mvvm_network.HttpManager.Companion.toDomain

/**
 * <h3> 作用类描述：全局常量</h3>
 *
 * @Package :        com.alvin.mvvm_framework.base
 * @Date :           2022/3/5
 * @author 高国峰
 */
object Constant {
    /**
     * 多域名需求，项目中用到添加如下配置
     */
    const val ossKey = "oss" // 多域名Key标识
    const val ossValue = "https://oss.xxx.com/" // 多域名具体Value值
    var ossHead = ossKey.toDomain // 使用多域名时，在Headers中添加内容

    /**
     * 动态管理域名，在下面添加后自动注册到域名切换框架中
     */
    val domainList = listOf(
        mapOf(
            ossKey to ossValue
        )
    )
}