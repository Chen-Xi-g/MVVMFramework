package com.alvin.mvvm_network

import android.app.Application
import com.safframework.http.interceptor.AndroidLoggingInterceptor
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.Proxy
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


/**
 * <h3> 作用类描述：网络请求管理</h3>
 *
 * @Package :        com.yleanlink.network
 * @Date :           2021/9/8-12:44
 * @author 高国峰
 */
class HttpManager {
    companion object {
        private lateinit var application: Application

        // 多域名快捷字段
        inline val String.toDomain: String get() = "${RetrofitUrlManager.DOMAIN_NAME_HEADER}${this}"

        val instance: HttpManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpManager()
        }
    }

    // 读取超时
    private var readTimeout = 10000L

    // 连接超时
    private var connectTimeout = 10000L

    // 写入超时
    private var writeTimeout = 10000L

    // 时间类型
    private var timeUnit = TimeUnit.MILLISECONDS


    // 失败后是否重连
    private var retryOnConnectionFailure = true

    // 日志拦截器
    private var loggingInterceptor: Interceptor = AndroidLoggingInterceptor.build()

    // 拦截器集合
    private var interceptorHashSet: HashSet<Interceptor> = hashSetOf()

    // OkHttpBuilder
    private var okHttpBuilder: OkHttpClient.Builder? = null

    // Retrofit
    private var retrofit: Retrofit? = null

    // 设置BaseUrl 默认为 ""
    private var baseUrl = ""


    /**
     * 初始化OkHttpBuilder
     */
    private fun initHttpBuilder() {

        okHttpBuilder = RetrofitUrlManager.getInstance().with(
            OkHttpClient.Builder()
                // 读取超时
                .readTimeout(readTimeout, timeUnit)
                // 链接超时
                .connectTimeout(connectTimeout, timeUnit)
                // 写入超时
                .writeTimeout(writeTimeout, timeUnit)
                // 链接失败重试
                .retryOnConnectionFailure(retryOnConnectionFailure)
                // 配置日志连接器
                .addInterceptor(loggingInterceptor)
        )
        if (interceptorHashSet.isNotEmpty()) {
            interceptorHashSet.forEach {
                okHttpBuilder?.addInterceptor(it)
            }
        }
        retrofit = Retrofit.Builder()
            // 添加OkHttp客户端
            .client(okHttpBuilder!!.build())
            // Moshi
            .addConverterFactory(MoshiConverterFactory.create())
            // 开启单独的线程
            .callbackExecutor(Executors.newSingleThreadExecutor())
            .baseUrl(baseUrl)
            .build()
    }

    fun getOkHttpBuilder(): OkHttpClient.Builder? {
        return okHttpBuilder
    }

    /**
     * 获取Retrofit的实例对象
     * @param apiClass 传入`Retrofit`的Api类
     * @param interceptor 自定义拦截器
     * @param netInterceptor 网络拦截器
     */
    fun <T> instanceRetrofit(
        apiClass: Class<T>,
        interceptor: MutableList<Interceptor> = mutableListOf(),
        netInterceptor: MutableList<Interceptor> = mutableListOf()
    ): T {
        if (okHttpBuilder == null) {
            initHttpBuilder()
        }

        interceptor.forEach {
            okHttpBuilder?.addInterceptor(it)
        }
        netInterceptor.forEach {
            okHttpBuilder?.addNetworkInterceptor(it)
        }

        if (baseUrl.isEmpty()) {
            throw NullPointerException("'BaseUrl'是空的，需要在调用'instanceRetrofit'之前由'setBaseUrl'进行设置。")
        }

        return retrofit!!.create(apiClass)
    }


    /**
     * 设置OkHttpBuilder默认时间类型。
     *
     *  可选时间类型：
     *
     *   * 纳秒[TimeUnit.NANOSECONDS]。
     *
     *   * 微秒[TimeUnit.MICROSECONDS]。
     *
     *   * 毫秒[TimeUnit.MILLISECONDS]。
     *
     *   * 秒[TimeUnit.SECONDS]。
     *
     *   * 分钟[TimeUnit.MINUTES]。
     *
     *   * 小时[TimeUnit.HOURS]。
     *
     *   * 天[TimeUnit.DAYS]。
     *
     * @param timeUnit 时间类型默认为毫秒[TimeUnit.MILLISECONDS]。
     *
     */
    fun setTimeUnit(timeUnit: TimeUnit) = apply {
        this.timeUnit = timeUnit
    }

    /**
     * 设置读取超时时间
     * @param readTimeout 读取超时时间 默认毫秒（可通过[setTimeUnit]修改默认时间类型）。
     */
    fun setReadTimeout(readTimeout: Long) = apply {
        this.readTimeout = readTimeout
    }

    /**
     * 设置写入超时时间
     * @param writeTimeout 写入超时时间 默认毫秒（可通过[setTimeUnit]修改默认时间类型）。
     */
    fun setWriteTimeout(writeTimeout: Long) = apply {
        this.writeTimeout = writeTimeout
    }

    /**
     * 连接超时时间
     * @param connectTimeout 连接超时时间 默认毫秒（可通过[setTimeUnit]修改默认时间类型）。
     */
    fun setConnectTimeout(connectTimeout: Long) = apply {
        this.connectTimeout = connectTimeout
    }

    /**
     * 超时后是否自动重连
     * @param retryOnConnectionFailure true 超时后将会自动重连 false 超时后不会重新连接
     */
    fun setRetryOnConnectionFailure(retryOnConnectionFailure: Boolean) = apply {
        this.retryOnConnectionFailure = retryOnConnectionFailure
    }

    /**
     * 设置 Http 日志拦截器
     * @param loggingInterceptor 传入实现`Interceptor`的日志拦截器
     */
    fun setLoggingInterceptor(loggingInterceptor: Interceptor) = apply {
        this.loggingInterceptor = loggingInterceptor
    }

    /**
     * 设置 Http 日志拦截器
     * @param isDebug true： 开发环境
     *                false: 正式环境
     * @param hideVerticalLine 是否隐藏打印的边界， 方便复制粘贴
     * @param requestTag 请求日志标签
     * @param responseTag 返回日志标签
     */
    fun setLoggingInterceptor(
        isDebug: Boolean = true,
        hideVerticalLine: Boolean = false,
        requestTag: String = "Request",
        responseTag: String = "Response"
    ) = apply {
        this.loggingInterceptor = AndroidLoggingInterceptor.build()
    }

    /**
     * 添加拦截器到集合中
     * @param interceptor 自定义的拦截器
     */
    fun addInterceptorList(interceptor: Interceptor) = apply {
        this.interceptorHashSet.add(interceptor)
    }

    /**
     * 添加拦截器集合
     * @param interceptorHashSet 拦截器集合 使用 HashSet 防止添加重复拦截器
     */
    fun setInterceptorList(interceptorHashSet: HashSet<Interceptor>) = apply {
        this.interceptorHashSet.addAll(interceptorHashSet)
    }

    /**
     * 设置是否使用带理 默认不使用
     * @param proxy 默认值为[Proxy.NO_PROXY]
     */
    fun setProxy(proxy: Proxy?) = apply {
        okHttpBuilder?.proxy(proxy)
    }

    inline fun setting(func: HttpManager.() -> Unit): HttpManager = apply {
        this.func()
    }

    /**
     * 设置网络请求的BaseUrl
     * @param baseUrl baseUrl
     */
    fun setBaseUrl(baseUrl: String = "") = apply {
        RetrofitUrlManager.getInstance().setGlobalDomain(baseUrl)
        this.baseUrl = baseUrl
    }

    /**
     * 配置多域名
     *
     * @param domain 域名的键值对
     */
    fun setDomain(domain: MutableMap<String, String>.() -> Unit = {}) = apply {
        val domainUrl = mutableMapOf<String, String>().apply(domain)
        RetrofitUrlManager.getInstance().apply {
            domainUrl.forEach {
                putDomain(it.key, it.value)
            }
        }
    }

    fun build() {
        initHttpBuilder()
    }
}