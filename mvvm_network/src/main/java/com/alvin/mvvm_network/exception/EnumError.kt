package com.alvin.mvvm_network.exception

/**
 * <h3> 作用类描述：</h3>
 *
 * @Package :        com.alvin.mvvm_network.exception
 * @Date :           2021/12/23-15:39
 * @author 高国峰
 */
enum class EnumError(private val code: Int, private val error: String) {

    /**
     * 未知错误
     */
    UNKNOWN(1000, "请求失败，请稍后再试"),

    /**
     * 解析错误
     */
    PARSE_ERROR(1001, "解析错误，请稍后再试"),

    /**
     * 网络错误
     */
    NETWORK_ERROR(1002, "网络连接错误，请稍后重试"),

    /**
     * 证书出错
     */
    SSL_ERROR(1004, "证书出错，请稍后再试"),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1006, "网络连接超时，请稍后重试");

    fun getValue(): String {
        return error
    }

    fun getKey(): Int {
        return code
    }

}