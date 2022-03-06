

[![MVVM](https://badgen.net/badge/Alvin/mvvm/green?icon=github)](https://github.com/Chen-Xi-g/MVVMFramework)  [![MVVM](https://jitpack.io/v/Chen-Xi-g/MVVMFramework.svg)](https://jitpack.io/#Chen-Xi-g/MVVMFramework)

# 基于MVVM进行快速开发， 上手即用。（正在重构中，敬请期待）

> 对基础框架进行模块分离, 分为 `MVVM Library`--`MVVM Navigation Library`--`MVVM Network Library`
> 可基于业务需求使用 `MVVM Library` 、`MVVM Navigation Library`、`MVVM Network Library`

## How to

> 具体使用请查看library中的注释,已经非常清楚. 一定要看 Demo

To get a Git project into your build:

**Step 1**. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

**Step 2**. Add the dependency

```
dependencies {
	// MVVM 基类
	implementation 'com.github.Chen-Xi-g.MVVMFramework:mvvm_framework:Tag'
	// MVVM Network 只负责网络处理
	implementation 'com.github.Chen-Xi-g.MVVMFramework:mvvm_network:Tag'
	// MVVM Navigation 组件抽离
	implementation 'com.github.Chen-Xi-g.MVVMFramework:mvvm_navigation:Tag'
}
```

| 说明            | 依赖地址                                            | 版本号                                                       |
| --------------- | --------------------------------------------------- | ------------------------------------------------------------ |
| MVVM 基类        | implementation 'com.github.Chen-Xi-g.MVVMFramework:mvvm_framework:Tag'  | [![MVVM](https://jitpack.io/v/Chen-Xi-g/MVVMFramework.svg)](https://jitpack.io/#Chen-Xi-g/MVVMFramework) |
| MVVM Network    | implementation 'com.github.Chen-Xi-g.MVVMFramework:mvvm_network:Tag'    | [![MVVM](https://jitpack.io/v/Chen-Xi-g/MVVMFramework.svg)](https://jitpack.io/#Chen-Xi-g/MVVMFramework) |
| MVVM Navigation | implementation 'com.github.Chen-Xi-g.MVVMFramework:mvvm_navigation:Tag' | [![MVVM](https://jitpack.io/v/Chen-Xi-g/MVVMFramework.svg)](https://jitpack.io/#Chen-Xi-g/MVVMFramework) |

> 随着`Google`对`Jetpack`的完善，对于开发者来说，`MVVM`显得越来越高效与方便。
>
> 对于使用`MVVM`的公司来说，都有一套自己的`MVVM`框架，但是我发现有些只是对框架进行非常简单的封装，导致在开发过程中会出现很多没必要的冗余代码。
>
> 这篇文章主要就是分享如何从0搭建一个高效的`MVVM`框架。

## 框架结构

在封装之前，要确定自己需要封装的功能，列如常用的`Activity`、`Fragment`、`LiveData`、`ViewModel`、`Navigation`、以及其他的`Widget`。
所以你要在开始开发之前确定好包的分包结构。下面是我对`MVVM`框架的分包结构。

![MVVM Library](https://blog.minlukj.com/wp-content/uploads/2022/01/16415230451.png)

从上图可以看到每次包都是非常明确

* `base`包下封装了`MVVM`的基础组件
  * `activity`实现`DataBinding + ViewModel`的封装，以及一些其他功能
  * `adapter`实现`DataBinding + Adapter`的封装
  * `fragment`实现`DataBinding + ViewModel`的封装，以及一些其他功能
  * `livedata`实现`LiveData`的基础功能封装，如非空返回值
  * `navigation`实现导航的统一处理
  * `view_model`实现`BaseViewModel`的处理
* `callback`包下封装了组件的回调接口
* `help`包下封装了组件的辅助类
* `manager`包下封装了对于组件的管理

## 开始封装

> 通过上面的框架结构，现在已经有了明确的目标。
>
> 这时候需要对`BaseActivity`的功能进行抽离，避免所有的功能都写在一个类中，导致维护与扩展成本太高。
>
> 以下只展示Activity的具体实现内容，注释已经非常明确。

在结束后，你会得到下图结构的`Activity`。

![](https://blog.minlukj.com/wp-content/uploads/2022/01/Snipaste_2022-01-07_11-13-23.png)

### Activity封装

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
6. `BaseMVVMActivity` 所有`Activity`最终需要继承的`MVVM`类，通过传入`DataBinding`和`ViewModel`的泛型进行初始化操作，在构造参数中还需要获取`Layout`布局
7. `BaseListActivity`适用于列表的`Activity`，分页操作、上拉加载、下拉刷新、空布局、头布局、底布局封装。

### Fragment封装

根据你的需要进行不同的封装，我比较倾向于和`Activity`具有相同功能的封装，也就是`Activity`封装的功能我`Fragment`也要有。这样在使用`Navigation`的时候可以减少`Activity`和`Fragment`的差异。这里直接参考Activity的封装

### Adapter封装

每个项目中肯定会有列表的页面，所以还需要对`Adapter`进行`DataBinding`适配，这里使用的Adapter是[BRVAH](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)。

```kotlin
abstract class BaseBindingListAdapter<T, DB : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : BaseQuickAdapter<T, BaseViewHolder>(layoutResId) {

    abstract fun convert(holder: BaseViewHolder, item: T, dataBinding: DB?)

    override fun convert(holder: BaseViewHolder, item: T) {
        convert(holder, item, DataBindingUtil.bind(holder.itemView))
    }
}
```

### LiveData封装

`LiveData`在使用的时候会出现数据倒灌的情况，用简单的话来描述数据倒灌：A订阅1月1日新闻信息，B订阅1月15日新闻信息，但是B在1月15日同时收到了1月1日的信息，这明显不符合我们生活中的逻辑，所以需要对`LiveData`进行封装，详细的可以查看`KunMinX`的**[UnPeek-LiveData](https://github.com/KunMinX/UnPeek-LiveData)**。

### Navigation封装

通过重写 `FragmentNavigator` 将原来的 `FragmentTransaction.replace()` 方法替换为 `hide()/Show()`

### ViewModel封装

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

### Callback 回调

在封装以上基础组件时，需要自定义回调的接口统一存放到`callback`目录下

### 辅助类封装

大部分的`Activity`和`Fragment`样式基本相同，比如布局中的`TitleLayout`、`LoadingLayout`这些都是统一样式。所以可以封装全局的辅助类来对Activity中的属性进行抽离。

* 定义接口`ISettingBaseActivity`添加抽离的方法，并且赋于默认值。
* 定义接口`ISettingBaseFragment`添加抽离的方法，并且赋于默认值。
* 创建`ISettingBaseActivity`和`ISettingBaseFragment`的实现类，进行默认的自定义操作。
* 创建`GlobalMVVMBuilder`进行赋值

### 管理类封装

通过`Lifecycle`结合`AppManager`对Activity的进出栈管理。

## 鸣谢

> 该框架参考以下优秀开源项目,特此鸣谢. 不分先后按首字母排序.

* [AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode) 安卓工具类库
* [AVLoadingIndicatorView](https://github.com/HarlonWang/AVLoadingIndicatorView) 加载Loading动画
* [BRVAH](https://github.com/CymChad/BaseRecyclerViewAdapterHelper) 万能Adapter
* [DKVideoPlayer](https://github.com/Doikki/DKVideoPlayer) 安卓视频播放器
* [JetPackMvvm](https://github.com/hegaojian/JetpackMvvm) 基于MVVM模式集成JetPack玩Android项目
* [material-dialogs](https://github.com/afollestad/material-dialogs)
  一个漂亮、流畅、可扩展的对话框API，适用于Kotlin和Android。
* [RecyclerViewDivider](https://github.com/fondesa/recycler-view-divider) 一个为RecyclerView配置分隔符的库。
* [SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout) Android智能下拉刷新框架
* [saf-logginginterceptor](https://github.com/fengzhizi715/saf-logginginterceptor)
  Android项目中，OKHttp的日志的拦截器
* [Square](https://github.com/square) Square公司的 Retrofit + OkHttp + Moshi
* [UnPeek-LiveData](https://github.com/KunMinX/UnPeek-LiveData) 解决LiveData数据倒灌
* [优客API](https://api.iyk0.com/) 免费的API接口服务

## 源码

具体的实现过程已经在[Github](https://github.com/Chen-Xi-g/MVVMFramework)开源，并且有非常完整的注释，后续将会对`Sample`完善。

如果不想关注具体的实现，直接通过`Gradle`引入`MVVM`的依赖即可使用。

## 关于我

**如果你感觉对你有用的话请点一下Star吧，而且你还可以打赏一波(If you feel useful to you, please click Star, or you can reward it.)**

 <img src="http://r.photo.store.qq.com/psb?/V12LSg7n0Vj1Fg/JIE.r7vzYd0JdQV4.U8AFDF2wy5d*DXixdQZ2ZFiV6I!/r/dEYBAAAAAAAA" height = "400" width = "300">      <img src="http://r.photo.store.qq.com/psb?/V12LSg7n0Vj1Fg/64q8qbMEanfoAXbFWxrESl6QXS7ITX63kCabiSRL440!/r/dLYAAAAAAAAA" height = "400" width = "300">

 ### 如何联系我(How to contact me)

 **QQ:** 1217056667

 **邮箱(Email):** a912816369@gmail.com

 **小站:** https://me.minlukj.com

MIT License
=====

    Copyright (c) 2022 忞鹿
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
