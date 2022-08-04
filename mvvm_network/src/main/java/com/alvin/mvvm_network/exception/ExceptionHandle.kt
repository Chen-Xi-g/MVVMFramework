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

import android.net.ParseException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import org.apache.http.conn.ConnectTimeoutException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

/**
 * <h3> 作用类描述：关于服务端的统一异常处理</h3>
 *
 * @Package :        com.alvin.mvvm_network.exception
 * @Date :           2021/12/23-15:32
 * @author 高国峰
 */
object ExceptionHandle {
    fun handleException(
        throwable: Throwable?
    ): ResponseThrowable {
        return when (throwable) {
            is ResponseThrowable -> throwable
            is HttpException -> ResponseThrowable(
                SealedError.NetworkError(),
                throwable.message()
            )
            is JsonDataException, is JsonEncodingException, is ParseException -> ResponseThrowable(
                SealedError.ParseError(),
                throwable.message
            )
            is ConnectException -> ResponseThrowable(
                SealedError.NetworkError(),
                throwable.message
            )
            is SSLException -> ResponseThrowable(SealedError.SslError(), throwable.message)
            is ConnectTimeoutException -> ResponseThrowable(
                SealedError.TimeoutError(),
                throwable.message
            )
            is SocketTimeoutException -> ResponseThrowable(
                SealedError.TimeoutError(),
                throwable.message
            )
            is UnknownHostException -> ResponseThrowable(
                SealedError.Unknown(),
                throwable.message
            )
            else -> ResponseThrowable(
                SealedError.Unknown(),
                throwable?.message
            )
        }
    }
}