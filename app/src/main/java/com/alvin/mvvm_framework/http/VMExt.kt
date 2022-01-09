package com.alvin.mvvm_framework.http

import androidx.lifecycle.viewModelScope
import com.alvin.mvvm.base.view_model.BaseViewModel
import com.alvin.mvvm.base.view_model.LoadingEntity
import com.alvin.mvvm_network.BaseResponse
import com.alvin.mvvm_network.exception.ExceptionHandle
import com.alvin.mvvm_network.exception.ResponseThrowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不传
 * @param isLoading 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.request(
    block: suspend () -> T,
    success: (T) -> Unit,
    error: (ResponseThrowable) -> Unit = {},
    isLoading: Boolean = false,
    loadingMessage: String? = null
): Job {
    // 开始执行请求
    httpCallback.beforeNetwork.postValue(
        LoadingEntity(isLoading,!loadingMessage.isNullOrEmpty(),loadingMessage?:"")
    )
    return viewModelScope.launch {
        kotlin.runCatching {
            //请求体
            block()
        }.onSuccess {
            // 网络请求成功， 结束请求
            httpCallback.afterNetwork.postValue(false)
            //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
            kotlin.runCatching {
                success(it)
            }.onFailure { error ->
                // 发生异常， 打印错误内容
                error.printStackTrace()
                // 执行失败的回调方法
                error(ExceptionHandle.handleException(error))
            }
        }.onFailure {
            // 请求时发生异常， 执行失败回调
            httpCallback.onFailed.value = it.message ?: ""
            it.printStackTrace()
            error(ExceptionHandle.handleException(it))
        }
    }
}