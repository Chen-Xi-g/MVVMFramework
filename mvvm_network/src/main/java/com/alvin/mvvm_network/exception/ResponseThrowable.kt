/*
 * Copyright 2022 高国峰
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    constructor(sealedError: SealedError, errorLog: String? = "") {
        this.errorLog = errorLog
        this.errorCode = sealedError.code
        this.errorMsg = sealedError.error
    }
}