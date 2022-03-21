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
package com.alvin.mvvm.base.activity

import androidx.lifecycle.ViewModelProvider
import com.alvin.mvvm.base.view_model.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * <h3> 作用类描述：实现ViewModel的Activity基类</h3>
 *
 * @Package :        com.alvin.mvvm.base.activity
 * @Date :           2021/12/18
 * @author Alvin
 *
 * @property VM 继承BaseViewModel的ViewModel
 */
abstract class BaseVMActivity<VM : BaseViewModel> : BaseContentViewActivity() {

    /**
     * 获取泛型中的ViewModel实例
     */
    lateinit var viewModel: VM

    override fun initDataBinding() {
        super.initDataBinding()
        viewModel = createViewModel()
        registerHttpCallback()
        registerObserver()
    }

    /**
     * 初始化LiveData数据观察者
     */
    abstract fun registerObserver()

    /**
     * 注册HttpCallback
     */
    private fun registerHttpCallback() {
        // 请求结束
        viewModel.httpCallback.afterNetwork.observe(this) {
            afterNetwork()
        }
        // 请求开始
        viewModel.httpCallback.beforeNetwork.observe(this) {
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
        viewModel.httpCallback.onFailed.observe(this) {
            onFailed(it)
        }
    }

    /**
     * ViewModel 通过非泛型绑定时，注册Loading监听
     */
    protected fun addLoadingObserve(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            // 请求结束
            viewModel.httpCallback.afterNetwork.observe(this) {
                afterNetwork()
            }
            // 请求开始
            viewModel.httpCallback.beforeNetwork.observe(this) {
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
            viewModel.httpCallback.onFailed.observe(this) {
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

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }
}