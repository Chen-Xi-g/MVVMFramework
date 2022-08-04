[![MVVM](https://badgen.net/badge/Alvin/mvvm/green?icon=github)](https://github.com/Chen-Xi-g/MVVMFramework)  [![MVVM](https://jitpack.io/v/Chen-Xi-g/MVVMFramework.svg)](https://jitpack.io/#Chen-Xi-g/MVVMFramework)

# AlvinMVVM

结合Jetpack，构建快速开发的MVVM框架。

项目使用Jetpack：LiveData、ViewModel、Lifecycle、Navigation组件。

支持动态加载多状态布局：加载中、成功、失败、标题；

支持快速使用ListActivity、ListFragment；

支持使用插件快速生成适用于本框架的Activity、Fragment、ListActivity、ListFragment。

## 前言

> 随着`Google`对`Jetpack`的完善，对于开发者来说，`MVVM`显得越来越高效与方便。
>
> 对于使用`MVVM`的公司来说，都有一套自己的`MVVM`框架，但是我发现有些只是对框架进行非常简单的封装，导致在开发过程中会出现很多没必要的冗余代码。
>
> 这篇文章主要就是分享如何从0搭建一个高效的`MVVM`框架。

## 基于MVVM进行快速开发， 上手即用。（重构已完成，正在编写SampleApp）

> 对基础框架进行模块分离, 分为 `MVVM Library`--`MVVM Navigation Library`--`MVVM Network Library`--`RVAD`
> 可基于业务需求使用 `MVVM Library` 、`MVVM Navigation Library`、`MVVM Network Library`、`RVAD`

已开发一键生成代码模板, 创建适用于本框架的Activity和Fragment.
具体查看[AlvinMVVMPlugin_4_3](https://github.com/Chen-Xi-g/AlvinMVVMPlugin_4_3)

## 如何集成

| 说明            | 依赖地址                                                     | 版本号                                                       |
| --------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| BaseMVVM        | implementation 'com.github.Chen-Xi-g.MVVMFramework:base_mvvm:Tag' | [![MVVM](https://jitpack.io/v/Chen-Xi-g/MVVMFramework.svg)](https://jitpack.io/#Chen-Xi-g/MVVMFramework) |
| MVVM 基类       | implementation 'com.github.Chen-Xi-g.MVVMFramework:mvvm_framework:Tag' | [![MVVM](https://jitpack.io/v/Chen-Xi-g/MVVMFramework.svg)](https://jitpack.io/#Chen-Xi-g/MVVMFramework) |
| MVVM Network    | implementation 'com.github.Chen-Xi-g.MVVMFramework:mvvm_network:Tag' | [![MVVM](https://jitpack.io/v/Chen-Xi-g/MVVMFramework.svg)](https://jitpack.io/#Chen-Xi-g/MVVMFramework) |
| MVVM Navigation | implementation 'com.github.Chen-Xi-g.MVVMFramework:mvvm_navigation:Tag' | [![MVVM](https://jitpack.io/v/Chen-Xi-g/MVVMFramework.svg)](https://jitpack.io/#Chen-Xi-g/MVVMFramework) |
| RVAD            | implementation 'com.github.Chen-Xi-g.MVVMFramework:rvad:Tag' | [![MVVM](https://jitpack.io/v/Chen-Xi-g/MVVMFramework.svg)](https://jitpack.io/#Chen-Xi-g/MVVMFramework) |

To get a Git project into your build:

**Step 1**. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

**Step 2**. Add the dependency

```groovy
dependencies {
    // BaseMVVM 同时集成了 MVVM、MVVM Network、MVVM Navigation
    implementation 'com.github.Chen-Xi-g.MVVMFramework:base_mvvm:Tag'
    // MVVM 基类
    implementation 'com.github.Chen-Xi-g.MVVMFramework:mvvm_framework:Tag'
    // MVVM Network 只负责网络处理
    implementation 'com.github.Chen-Xi-g.MVVMFramework:mvvm_network:Tag'
    // MVVM Navigation 组件抽离
    implementation 'com.github.Chen-Xi-g.MVVMFramework:mvvm_navigation:Tag'
    // 快速使用RecyclerView的RVAD适配器
    implementation 'com.github.Chen-Xi-g.MVVMFramework:rvad:Tag'
}
```

### 合并集成

合并集成只需要引入`BaseMVVM`即可，内部 默认集成了`MVVM`、`MVVMNetwork`、`MVVMNavigation`、`RVAD`
，只需要实现`com.alvin.base_mvvm.base.IBaseMVVM`接口就可以直接使用。

#### 1.实现IBaseMVVM接口

建议在你的[Application](https://github.com/Chen-Xi-g/MVVMFramework/blob/main/sample/src/main/java/com/alvin/mvvm_framework/SampleApplication.kt)类，实现`IBaseMVVM`，并且需要在合适的位置进行配置和初始化相关参数，可以在这里配置网络请求框架的参数和UI全局参数。比如[拦截器](https://github.com/Chen-Xi-g/MVVMFramework/tree/main/sample/src/main/java/com/alvin/mvvm_framework/base/interceptor)和[多域名](https://github.com/Chen-Xi-g/MVVMFramework/blob/main/sample/src/main/java/com/alvin/mvvm_framework/base/Constant.kt)，全局[Activity](https://github.com/Chen-Xi-g/MVVMFramework/blob/main/sample/src/main/java/com/alvin/mvvm_framework/base/setting/BaseActivitySetting.kt)和[Fragment](https://github.com/Chen-Xi-g/MVVMFramework/blob/main/sample/src/main/java/com/alvin/mvvm_framework/base/setting/BaseFragmentSetting.kt)属性。

完整代码：

```kotlin
class SampleApplication2 : Application(), IBaseMVVM {
    override fun onCreate() {
        super.onCreate()
        // 使用默认配置
        initBaseMVVM(this, "https://www.wanandroid.com/")
        // 自定义配置
        initBaseMVVM(
            this,
            "https://www.wanandroid.com/",
            BaseActivitySetting(),
            BaseFragmentSetting(),
            isDebug = BuildConfig.DEBUG,
            timeUnit = TimeUnit.SECONDS,
            timeout = 30,
            retryOnConnection = true,
            domain = {
                Constant.domainList.forEach { map ->
                    map.forEach {
                        if (it.key.isNotEmpty() && it.value.isNotEmpty()) {
                            put(it.key, it.value)
                        }
                    }
                }
            },
            // 是否打印
            hideVerticalLine = true,
            // 请求标识
            requestTag = "Request 请求参数",
            // 响应标识
            responseTag = "Response 响应结果",
            // 拦截器
            ResponseInterceptor(),
            ParameterInterceptor()
        )
    }
}
```

对于ViewModel的网络请求已实现响应的扩展函数，参考单独集成`2`的使用。

### 单独集成

#### 1.实现IMVVM、INetWork接口

建议在你的[Application](https://github.com/Chen-Xi-g/MVVMFramework/blob/main/sample/src/main/java/com/alvin/mvvm_framework/SampleApplication.kt)类，实现`IMVVM、INetWork`，并且需要在合适的位置进行配置和初始化相关参数，可以在这里配置网络请求框架的参数和UI全局参数。比如[拦截器](https://github.com/Chen-Xi-g/MVVMFramework/tree/main/sample/src/main/java/com/alvin/mvvm_framework/base/interceptor)和[多域名](https://github.com/Chen-Xi-g/MVVMFramework/blob/main/sample/src/main/java/com/alvin/mvvm_framework/base/Constant.kt)，全局[Activity](https://github.com/Chen-Xi-g/MVVMFramework/blob/main/sample/src/main/java/com/alvin/mvvm_framework/base/setting/BaseActivitySetting.kt)和[Fragment](https://github.com/Chen-Xi-g/MVVMFramework/blob/main/sample/src/main/java/com/alvin/mvvm_framework/base/setting/BaseFragmentSetting.kt)属性。

完整代码：

```kotlin
class SampleApplication : Application(), IMVVMApplication, INetWorkApplication {

    override fun onCreate() {
        super.onCreate()
        // 初始化MVVM框架
        initMVVM(this, BaseActivitySetting(), BaseFragmentSetting(), BuildConfig.DEBUG)
        /* 两种配置网络请求，选择其一即可 */
        // 初始化网络请求，默认配置
        initNetwork(baseUrl = "https://www.wanandroid.com")
        // 初始化网络请求, 自定义配置
        initNetwork(
            // 基础url
            "https://www.wanandroid.com",
            // 时间单位
            TimeUnit.SECONDS,
            // 时间
            30,
            // 是否重试
            true,
            // 多域名配置
            domain = {
                Constant.domainList.forEach { map ->
                    map.forEach {
                        if (it.key.isNotEmpty() && it.value.isNotEmpty()) {
                            put(it.key, it.value)
                        }
                    }
                }
            },
            // 是否隐藏网络请求中的竖线
            true,
            // 请求标识
            "Request 请求参数",
            // 响应表示
            "Response 响应结果",
            // 是否Debug
            BuildConfig.DEBUG,
            // 拦截器
            ResponseInterceptor(),
            ParameterInterceptor()
        )
    }
}
```

#### 2.创建ViewModel扩展函数

所有模块需要依赖的base模块创建ViewModel相关的扩展函数[VMKxt](https://github.com/Chen-Xi-g/MVVMFramework/blob/main/sample/src/main/java/com/alvin/mvvm_framework/base/http/VMExt.kt)和Json实体类壳[BaseEntity](https://github.com/Chen-Xi-g/MVVMFramework/blob/main/sample/src/main/java/com/alvin/mvvm_framework/model/BaseEntity.kt)。

```kotlin
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
 * 不过滤服务器结果
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
```

以上代码封装了快速的网络请求扩展函数，并且可以根据自己的情况，选择脱壳或者不脱壳的回调处理。 调用示例：

```kotlin
/**
 * 加载列表数据
 */
fun getArticleListData(page: Int, pageSize: Int) {
    request(
        {
            filterArticleList(page, pageSize)
        }, {
            // 成功操作
            it?.let {
                _articleListData.postValue(it.datas)
            }
        }
    )
}
```

完成上面的操作，你就可以进入愉快的开发工作了。

### 引入一键生成代码插件(可选)

每次创建Activity、Fragment、ListActivity、ListFragment都是重复的工作，为了可以更高效的开发，减少这些枯燥的操作，特地编写的快速生成MVVM代码的插件，该插件只适用于当前MVVM框架，具体使用请前往[AlvinMVVMPlugin](https://github.com/Chen-Xi-g/AlvinMVVMPlugin_4_3)。集成后你就可以开始像创建`EmptyActivity`这样创建`MVVMActivity`。

## 框架结构

### mvvm

该组件对Activity和Fragment进行常用属性封装

* `base`包下封装了`MVVM`的基础组件。
    * `activity`实现`DataBinding + ViewModel`的封装，以及一些其他功能。
    * `adapter`实现`DataBinding + Adapter`的封装。
    * `fragment`实现`DataBinding + ViewModel`的封装，以及一些其他功能。
    * `livedata`实现`LiveData`的基础功能封装，如基本数据类型的非空返回值。
    * `view_model`实现`BaseViewModel`的处理。
* `help`包下封装了组件的辅助类，在BaseApplication中进行全局Actiivty、Fragment属性赋值。
* `manager`包下封装了对Activity的管理。
* `utils`包下封装了LogUtil工具类，通过BaseApplication进行初始化。

#### Activity封装

1. `AbstractActivity`是`Activity`的抽象基类，这个类里面的方法适用于全部`Activity`的需求。
   该类中封装了所有Activity必须实现的抽象方法。
2. `BaseActivity`封装了基础的`Activity`功能，主要用来初始化`Activity`公共功能：`DataBinding`的初始化、沉浸式状态栏、`AbstractActivity`抽象方法的调用、屏幕适配、空白区域隐藏软键盘。具体功能可以自行新增。
3. `BaseDialogActivity`只负责显示`Dialog Loading`弹窗，一般在提交请求或本地流处理时使用。也可以扩展其他的`Dialog`，比如时间选择器之类。
4. `BaseContentViewActivity`是对布局进行初始化操作的`Activity`，他是我们的核心。这里处理了每个`Activity`的每个状态的布局，一般情况下有：
    * `TitleLayout` 公共标题
    * `ContentLayout` 主要的内容布局，使我们需要程序内容的主要容器。
    * `ErrorLayout` 当网络请求发生错误，需要对用户进行友好的提示。
    * `LoadingLayout` 正在加载数据的布局，给用户一个良好的体验，避免首次进入页面显示的布局没有数据。
5. `BaseVMActivity`实现`ViewMode`的`Activity`基类，通过泛型对`ViewModel`进行实例化。并且通过`BaseViewModel`进行公共操作。
6. `BaseMVVMActivity` 所有`Activity`最终需要继承的`MVVM`类，通过传入`DataBinding`和`ViewModel`
   的泛型进行初始化操作，在构造参数中还需要获取`Layout`布局
7. `BaseListActivity`适用于列表的`Activity`，分页操作、上拉加载、下拉刷新、暂无更多数据封装。(
   如果需要使用内置的BaseListActivity则需要引入RVAD依赖，否则无法使用)

#### Fragment封装

根据你的需要进行不同的封装，我比较倾向于和`Activity`具有相同功能的封装，也就是`Activity`封装的功能我`Fragment`也要有。这样在使用`Navigation`的时候可以减少`Activity`和`Fragment`的差异。这里直接参考Activity的封装

#### LiveData封装

`LiveData`在使用的时候会出现数据倒灌的情况，用简单的话来描述数据倒灌：A订阅1月1日新闻信息，B订阅1月15日新闻信息，但是B在1月15日同时收到了1月1日的信息，这明显不符合我们生活中的逻辑，所以需要对`LiveData`进行封装，详细的可以查看`KunMinX`的**[UnPeek-LiveData](https://github.com/KunMinX/UnPeek-LiveData)**。

#### Navigation封装

通过重写 `FragmentNavigator` 将原来的 `FragmentTransaction.replace()` 方法替换为 `hide()/Show()`

#### ViewModel封装

在`BaseViewModel`中封装一个网络请求需要用的`LiveData`，下面是一个简单的示例

```kotlin
open class BaseViewModel : ViewModel() {

    // 默认的网络请求LiveData
    val httpCallback: HttpCallback by lazy { HttpCallback() }

    inner class HttpCallback {

        /**
         * 请求发生错误
         *
         * String = 网络请求异常
         */
        val onFailed by lazy { StringLiveData() }

        /**
         * 请求开始
         *
         * LoadingEntity 显示loading的实体类
         */
        val beforeNetwork by lazy { EventLiveData<LoadingEntity>() }

        /**
         * 请求结束后框架自动对 loading 进行处理
         *
         * false 关闭 loading or Dialog
         * true 不关闭 loading or Dialog
         */
        val afterNetwork by lazy { BooleanLiveData() }
    }
}
```

#### 辅助类封装

大部分的`Activity`和`Fragment`样式基本相同，比如布局中的`TitleLayout`、`LoadingLayout`这些都是统一样式。所以可以封装全局的辅助类来对Activity中的属性进行抽离。

* 定义接口`ISettingBaseActivity`添加抽离的方法，并且赋于默认值。
* 定义接口`ISettingBaseFragment`添加抽离的方法，并且赋于默认值。
* 创建`ISettingBaseActivity`和`ISettingBaseFragment`的实现类，进行默认的自定义操作。
* 创建`GlobalMVVMBuilder`进行赋值

#### 管理类封装

通过`Lifecycle`结合`AppManager`对Activity的进出栈管理。

### mvvm_navigation

分离Navigation，通过重写 FragmentNavigator 将原来的 FragmentTransaction.replace() 方法替换为 hide()/Show()。

### mvvm_network

使用 `Retrofit` + `OkHttp` + `Moshi` 对网络请求进行封装，使用密封类自定义异常处理。

* `application`初始化接口

* `exception`错误回调处理

    * 当网络请求发生错误时，可以在`string.xml`中覆盖原有内容，自定义全局错误文本。

      ```xml
      <string name="mv_net_unknown">请求失败，请稍后再试。</string>
      <string name="mv_net_parse">解析错误，请稍后再试。</string>
      <string name="mv_net_timeout">请求超时，请稍后再试。</string>
      <string name="mv_net_network">网络连接错误，请稍后重试。</string>
      <string name="mv_net_ssl">证书出错，请稍后再试。</string>
      ```

**HttpManager**提供了一些基础的函数，这些函数基本都是OkHttp的函数，只是为了方便设置。

* `instanceRetrofit()`获取Retrofit的实例对象
* `setBaseUrl()`设置网络请求的BaseUrl
* `setTimeUnit()`设置OkHttpBuilder默认时间类型。
* `setReadTimeout()`设置读取超时时间
* `setWriteTimeout()`设置写入超时时间
* `setConnectTimeout()`连接超时时间
* `setRetryOnConnectionFailure()`超时后是否自动重连
* `setLoggingInterceptor()`设置自定义拦截器或内置日志拦截器
* `addInterceptorList()`添加拦截器
* `setInterceptorList()`设置拦截器
* `setProxy()`设置是否使用带理 默认不使用
* `setDomain()`设置多域名

### RVAD

> 对于适配器的工作，一般都是一些重复的工作，比如创建Adapter，选择布局，设置各种事件回调，还有状态处理.
>
>为了处理上面的问题，使用Kotlin扩展函数与Lambda表达式进行封装，减少重复性工作.

#### ReuseAdapter

1. 公共变量
    1. `rv`RecyclerView
    2. `list`返回当前Adapter设置的数据集.
    3. `typeLayouts`已经设置的指定类型布局,适配多布局.
    4. `headerList`已经设置头布局集合
    5. `footerList`已经设置的尾布局集合
    6. `headerCount`头布局数量
    7. `footerCount`尾布局数量
    8. `shakeEnable`当前Adapter是否需要设置防抖
    9. `checkedPosition`已选择条目的Position
    10. `selectModel`设置当前选择模式
    11. `checkedCount`已选择条目数量
2. 函数
    1. `onBind()`基于Lambda为`onBindViewHolder()`添加回调,用于对Item内容进行操作.
    2. `addType<T>()`添加指定类型布局, 这里的泛型类型需要和Model类型一致, 否则无法找到向右布局.
    3. `onItemClick()`为Recyclerview添加ItemView的点击事件回调.
    4. `onItemLongClick()`为Recyclerview添加ItemView的长按事件回调.
    5. `onChecked()`选择回调.
    6. `onStickyAttachListener`吸顶监听.
    7. `addOnItemChildClickListener()`为Recyclerview添加ItemView的子View的点击事件回调.
    8. `addOnItemChildLongClickListener()`为Recyclerview添加ItemView的子View的长按事件回调.
    9. `addHeader()`添加头布局
    10. `setHeader()`设置头布局
    11. `removeHeader()`删除头布局
    12. `removeHeaderAll()`删除所有头布局
    13. `isHeader()`判断是否为头布局
    14. `addFooter()`添加尾布局
    15. `setFooter()`设置尾布局
    16. `removeFooter()`删除尾部据
    17. `removeFooterAll()`删除所有尾部据
    18. `isFooter()`判断是否为尾布局
    19. `scrollRv()`滑动RecyclerView到指定索引
    20. `checkedAll()`全选或取消全选.
    21. `setChecked()`设置指定索引选中状态.
    22. `checkedSwitch()`切换指定索引选中状态.
    23. `getData<T>()`根据索引获取模型数据.
    24. `setData()`设置数据.
    25. `addData()`新增数据.
    26. `removeAt()`删除数据.
    27. `isCheckedAll()`当前是否已经全选.
    28. `isSticky()`判断指定索引是否为吸顶布局

#### ReuseAdapter.BaseViewHolder

1. 公共变量
    1. `_item`获取当前Item的数据.
2. 函数
    1. `getBinding<T>()`获取当前ItemView的ViewDataBinding对象.
    2. `getHeaderBinding<T>()`获取头布局的ViewDataBinding对象
    3. `getFooterBinding()`获取底布局的ViewDataBinding对象
    4. `getType()`获取当前类型.
    5. `getItem<T>()`通过泛型获取指定数据类型.
    6. `getItemOrNull<T>()`获取当前类型数据, 如果找不到则返回null.
    7. `findView<V>()`通过ViewId返回指定泛型View.
    8. `expand()`展开Item.
    9. `collapse()`收起Item.
    10. `expandOrCollapse()`展开或收起Item.

## 更新日志

v1.x 初始化项目，功能基本完善。

v2.0 新增RVAD快速使用RecyclerView

## 鸣谢

> 该框架参考以下优秀开源项目,特此鸣谢. 不分先后按首字母排序.

* [AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode) 安卓工具类库
* [AVLoadingIndicatorView](https://github.com/HarlonWang/AVLoadingIndicatorView) 加载Loading动画
* [DKVideoPlayer](https://github.com/Doikki/DKVideoPlayer) 安卓视频播放器
* [material-dialogs](https://github.com/afollestad/material-dialogs)
  一个漂亮、流畅、可扩展的对话框API，适用于Kotlin和Android。
* [RecyclerViewDivider](https://github.com/fondesa/recycler-view-divider) 一个为RecyclerView配置分隔符的库。
* [SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout) Android智能下拉刷新框架
* [saf-logginginterceptor](https://github.com/fengzhizi715/saf-logginginterceptor)
  Android项目中，OKHttp的日志的拦截器
* [Square](https://github.com/square) Square公司的 Retrofit + OkHttp + Moshi
* [UnPeek-LiveData](https://github.com/KunMinX/UnPeek-LiveData) 解决LiveData数据倒灌

## 关于我

**如果你感觉对你有用的话请点一下Star吧，而且你还可以打赏一波(If you feel useful to you, please click Star, or you can reward it.)**

<img src="http://r.photo.store.qq.com/psb?/V12LSg7n0Vj1Fg/JIE.r7vzYd0JdQV4.U8AFDF2wy5d*DXixdQZ2ZFiV6I!/r/dEYBAAAAAAAA" height = "400" width = "300">      <img src="http://r.photo.store.qq.com/psb?/V12LSg7n0Vj1Fg/64q8qbMEanfoAXbFWxrESl6QXS7ITX63kCabiSRL440!/r/dLYAAAAAAAAA" height = "400" width = "300">

### 如何联系我(How to contact me)

**QQ:** 1217056667

**邮箱(Email):** a912816369@gmail.com

**小站:** https://me.minlukj.com

# License

	   Copyright 2022 高国峰
	
	   Licensed under the Apache License, Version 2.0 (the "License");
	   you may not use this file except in compliance with the License.
	   You may obtain a copy of the License at
	
	       http://www.apache.org/licenses/LICENSE-2.0
	
	   Unless required by applicable law or agreed to in writing, software
	   distributed under the License is distributed on an "AS IS" BASIS,
	   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	   See the License for the specific language governing permissions and
	   limitations under the License.