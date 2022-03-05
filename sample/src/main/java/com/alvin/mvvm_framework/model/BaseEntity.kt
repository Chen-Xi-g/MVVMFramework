package com.alvin.mvvm_framework.model


import com.alvin.mvvm_network.BaseResponse

data class BaseEntity<T>(
    val data: T? = null,
    val errorCode: Int? = 0,
    val errorMsg: String? = ""
) : BaseResponse<T>() {
    override fun isSuccess(): Boolean {
        return errorCode == 0
    }

    override fun getResponseCode(): Int {
        return errorCode ?: -1
    }

    override fun getResponseData(): T? {
        return data
    }

    override fun getResponseMessage(): String {
        return errorMsg ?: ""
    }
}