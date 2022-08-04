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

import com.alvin.mvvm_network.R
import com.alvin.mvvm_network.application.INetWork

/**
 * <h3> 作用类描述：使用密封类，封装网络请求错误数据</h3>
 *
 * @Package :        com.alvin.mvvm_network.exception
 * @Date :           2021/12/23-15:39
 * @author 高国峰
 */
sealed class SealedError(val code: Int, val error: String) {
    data class Unknown(
        private val msgError: String = INetWork.application?.getString(R.string.mv_net_unknown)
            ?: "请求失败，请稍后再试。"
    ) : SealedError(1000, msgError)

    data class ParseError(
        private val msgError: String = INetWork.application?.getString(R.string.mv_net_parse)
            ?: "解析错误，请稍后再试。"
    ) :
        SealedError(1001, msgError)

    data class NetworkError(
        private val msgError: String = INetWork.application?.getString(R.string.mv_net_network)
            ?: "网络连接错误，请稍后重试。"
    ) :
        SealedError(1002, msgError)

    data class SslError(
        private val msgError: String = INetWork.application?.getString(R.string.mv_net_ssl)
            ?: "证书出错，请稍后再试。"
    ) : SealedError(1003, msgError)

    data class TimeoutError(
        private val msgError: String = INetWork.application?.getString(R.string.mv_net_timeout)
            ?: "网络连接超时，请稍后重试。"
    ) :
        SealedError(1004, msgError)
}