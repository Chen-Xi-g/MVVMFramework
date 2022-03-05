package com.alvin.mvvm_framework.model

/**
 * <h3> 作用类描述：列表适配器</h3>
 *
 * @Package :        com.alvin.mvvm_framework.model
 * @Date :           2022/3/5
 * @author 高国峰
 */
data class BaseListEntity<T>(
    val curPage: Int = 0,
    val offset: Int = 0,
    val datas: MutableList<T>,
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0,
)