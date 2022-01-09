package com.alvin.mvvm_network.exception

/**
 * <h3> 作用类描述：自定义异常内容</h3>
 *
 * @Package :        com.alvin.mvvm_network.exception
 * @Date :           2021/12/23-15:33
 * @author 高国峰
 */
class ResponseThrowable : Exception {
    /**
     * 错误码
     */
    var errorCode: Int = 0

    /**
     * 错误信息
     */
    var errorMsg: String?

    /**
     * 错误日志
     */
    var errorLog: String?

    constructor(errorCode: Int, errorMsg: String?, errorLog: String? = "") : super(errorMsg) {
        this.errorCode = errorCode
        this.errorMsg = errorMsg
        this.errorLog = errorLog
    }

    constructor(enumError: EnumError, errorLog: String? = "") {
        this.errorLog = errorLog
        this.errorCode = enumError.getKey()
        this.errorMsg = enumError.getValue()
    }
}