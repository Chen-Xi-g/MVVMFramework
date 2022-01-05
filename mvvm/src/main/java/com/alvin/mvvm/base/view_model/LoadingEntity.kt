package com.alvin.mvvm.base.view_model

/**
 * <h3> 作用类描述：网络请求开始时， 是否显示 Loading 和 Dialog </h3>
 *
 * @Package :        com.alvin.mvvm.base.view_model
 * @Date :           2022/1/4-17:17
 * @author Alvin
 */
data class LoadingEntity(
    val isLoading: Boolean = false,
    val isDialog: Boolean = true,
    val dialogContent: String = ""
)