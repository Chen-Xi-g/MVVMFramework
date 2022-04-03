package com.alvin.base_mvvm.ext

import androidx.lifecycle.viewModelScope
import com.alvin.mvvm.base.view_model.BaseViewModel
import com.alvin.mvvm.base.view_model.LoadingEntity
import com.alvin.mvvm.utils.LogUtil
import com.alvin.mvvm_network.BaseResponse
import com.alvin.mvvm_network.exception.ExceptionHandle
import com.alvin.mvvm_network.exception.ResponseThrowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * <h3> 作用类描述：ViewModel扩展函数</h3>
 *
 * @Package :        com.alvin.base_mvvm.ext
 * @Date :           2022/4/3
 * @author 高国峰
 */
object VMExt {

    /**
     * 过滤服务器结果，失败抛异常
     * @param block 请求体方法，必须要用suspend关键字修饰
     * @param success 成功回调
     * @param error 失败回调 可不传
     * @param isLoading 是否显示 Loading 布局
     * @param loadingMessage 加载框提示内容
     */
    fun <T> BaseViewModel.request(
        block: suspend () -> BaseResponse<T>,
        success: (T?) -> Unit,
        error: (ResponseThrowable) -> Unit = {},
        isLoading: Boolean = false,
        loadingMessage: String? = null
    ): Job {
        // 开始执行请求
        httpCallback.beforeNetwork.postValue(
            // 执行Loading逻辑
            LoadingEntity(
                isLoading,
                loadingMessage?.isNotEmpty() == true,
                loadingMessage ?: ""
            )
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
                    executeResponse(it) { coroutine ->
                        success(coroutine)
                    }
                }.onFailure { error ->
                    // 请求时发生异常， 执行失败回调
                    val responseThrowable = ExceptionHandle.handleException(error)
                    httpCallback.onFailed.value = responseThrowable.errorMsg ?: ""
                    responseThrowable.errorLog?.let { errorLog ->
                        LogUtil.e(errorLog)
                    }
                    // 执行失败的回调方法
                    error(responseThrowable)
                }
            }.onFailure { error ->
                // 请求时发生异常， 执行失败回调
                val responseThrowable = ExceptionHandle.handleException(error)
                httpCallback.onFailed.value = responseThrowable.errorMsg ?: ""
                responseThrowable.errorLog?.let { errorLog ->
                    LogUtil.e(errorLog)
                }
                // 执行失败的回调方法
                error(responseThrowable)
            }
        }
    }

    /**
     * 不过滤结果
     * @param block 请求体方法，必须要用suspend关键字修饰
     * @param success 成功回调
     * @param error 失败回调 可不传
     * @param isLoading 是否显示 Loading 布局
     * @param loadingMessage 加载框提示内容
     */
    fun <T> BaseViewModel.requestNoCheck(
        block: suspend () -> T,
        success: (T) -> Unit,
        error: (ResponseThrowable) -> Unit = {},
        isLoading: Boolean = false,
        loadingMessage: String? = null
    ): Job {
        // 开始执行请求
        httpCallback.beforeNetwork.postValue(
            // 执行Loading逻辑
            LoadingEntity(
                isLoading,
                loadingMessage?.isNotEmpty() == true,
                loadingMessage ?: ""
            )
        )
        return viewModelScope.launch {
            runCatching {
                //请求体
                block()
            }.onSuccess {
                // 网络请求成功， 结束请求
                httpCallback.afterNetwork.postValue(false)
                //成功回调
                success(it)
            }.onFailure { error ->
                // 请求时发生异常， 执行失败回调
                val responseThrowable = ExceptionHandle.handleException(error)
                httpCallback.onFailed.value = responseThrowable.errorMsg ?: ""
                responseThrowable.errorLog?.let { errorLog ->
                    LogUtil.e(errorLog)
                }
                // 执行失败的回调方法
                error(responseThrowable)
            }
        }
    }

    /**
     * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
     */
    suspend fun <T> executeResponse(
        response: BaseResponse<T>,
        success: suspend CoroutineScope.(T?) -> Unit
    ) {
        coroutineScope {
            when {
                response.isSuccess() -> {
                    success(response.getResponseData())
                }
                else -> {
                    throw ResponseThrowable(
                        response.getResponseCode(),
                        response.getResponseMessage(),
                        response.getResponseMessage()
                    )
                }
            }
        }
    }
}