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
    fun handleException(throwable: Throwable?): ResponseThrowable {
        return when (throwable) {
            is ResponseThrowable -> throwable
            is HttpException -> ResponseThrowable(EnumError.NETWORK_ERROR, throwable.message())
            is JsonDataException, is JsonEncodingException, is ParseException -> ResponseThrowable(
                EnumError.PARSE_ERROR,
                throwable.message
            )
            is ConnectException -> ResponseThrowable(EnumError.NETWORK_ERROR, throwable.message)
            is SSLException -> ResponseThrowable(EnumError.SSL_ERROR, throwable.message)
            is ConnectTimeoutException -> ResponseThrowable(
                EnumError.TIMEOUT_ERROR,
                throwable.message
            )
            is SocketTimeoutException -> ResponseThrowable(
                EnumError.TIMEOUT_ERROR,
                throwable.message
            )
            is UnknownHostException -> ResponseThrowable(
                EnumError.TIMEOUT_ERROR,
                throwable.message
            )
            else -> ResponseThrowable(
                EnumError.UNKNOWN,
                throwable?.message
            )
        }
    }
}