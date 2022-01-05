package com.alvin.mvvm.base.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.alvin.mvvm.base.view_model.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * <h3> 作用类描述：ViewModelFragment基类，自动把ViewModel注入Fragment</h3>
 *
 * @Package :        com.alvin.mvvm.base.fragment
 * @Date :           2021/12/22-15:35
 * @author Alvin
 */
abstract class BaseVMFragment<VM : BaseViewModel> : BaseContentViewFragment() {
    /**
     * 发送懒加载请求
     */
    private val handler = Handler(Looper.getMainLooper())

    //是否第一次加载 适配懒加载
    private var isFirst: Boolean = true

    // BaseViewModel
    lateinit var viewModel: VM

    // Activity
    lateinit var activity: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AppCompatActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataBinding()
        isFirst = true
        viewModel = createViewModel()
        initView(view, savedInstanceState)
        registerHttpCallback()
        registerObserver()
        obtainData()
    }

    /**
     * 在OnCreate之前对ViewBinding初始化
     *
     */
    abstract fun initDataBinding()

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }

    /**
     * 注册HttpCallback
     */
    private fun registerHttpCallback() {
        // 请求结束
        viewModel.httpCallback.afterNetwork.observe(viewLifecycleOwner) {
            afterNetwork()
        }
        // 请求开始
        viewModel.httpCallback.beforeNetwork.observe(viewLifecycleOwner) {
            when {
                // 显示 Loading 布局
                it.isLoading -> {
                    beforeNetwork(true, null)
                }
                // 显示 Dialog 布局
                it.isDialog -> {
                    beforeNetwork(false, it.dialogContent)
                }
                // 什么都不显示
                else -> {
                    beforeNetwork(false, null)
                }
            }
        }
        // 请求发生错误
        viewModel.httpCallback.onFailed.observe(viewLifecycleOwner) {
            onFailed(it)
        }
    }

    /**
     * 初始化LiveData数据观察者
     */
    abstract fun registerObserver()

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            handler.postDelayed({
                lazyLoadData()
                isFirst = false
            }, lazyLoadTime())
        }
    }

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    open fun lazyLoadTime(): Long {
        return 300
    }

    /**
     * 将非该Fragment绑定的ViewModel添加 loading回调 防止出现请求时不显示 loading 弹窗bug
     */
    protected fun addLoadingObserve(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            // 请求结束
            viewModel.httpCallback.afterNetwork.observe(viewLifecycleOwner) {
                afterNetwork()
            }
            // 请求开始
            viewModel.httpCallback.beforeNetwork.observe(viewLifecycleOwner) {
                when {
                    // 显示 Loading 布局
                    it.isLoading -> {
                        beforeNetwork(true, null)
                    }
                    // 显示 Dialog 布局
                    it.isDialog -> {
                        beforeNetwork(false, it.dialogContent)
                    }
                    // 什么都不显示
                    else -> {
                        beforeNetwork(false, null)
                    }
                }
            }
            // 请求发生错误
            viewModel.httpCallback.onFailed.observe(viewLifecycleOwner) {
                onFailed(it)
            }
        }
    }

    /**
     * 获取当前类绑定的泛型ViewModel-clazz
     */
    @Suppress("UNCHECKED_CAST")
    private fun <VM> getVmClazz(obj: Any): VM {
        return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}