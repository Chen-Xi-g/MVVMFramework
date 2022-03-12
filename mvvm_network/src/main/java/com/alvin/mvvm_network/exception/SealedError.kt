package com.alvin.mvvm_network.exception

/**
 * <h3> 作用类描述：使用密封类，封装网络请求错误数据</h3>
 *
 * @Package :        com.alvin.mvvm_network.exception
 * @Date :           2021/12/23-15:39
 * @author 高国峰
 */
sealed class SealedError(val code: Int, val error: String) {
    data class Unknown(private val msgError: String = "请求失败，请稍后再试。") : SealedError(1000, msgError)
    data class ParseError(private val msgError: String = "解析错误，请稍后再试。") :
        SealedError(1001, msgError)

    data class NetworkError(private val msgError: String = "网络连接错误，请稍后重试。") :
        SealedError(1002, msgError)

    data class SslError(private val msgError: String = "证书出错，请稍后再试。") : SealedError(1003, msgError)
    data class TimeoutError(private val msgError: String = "网络连接超时，请稍后重试。") :
        SealedError(1004, msgError)
}