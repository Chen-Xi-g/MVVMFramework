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
package com.alvin.mvvm.base.fragment

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.alvin.mvvm.base.view_model.BaseViewModel

/**
 * <h3> 作用类描述：ViewModelFragment基类，自动把ViewModel注入Fragment和Databind注入进来了. </h3>
 *
 * @Package :        com.alvin.mvvm.base.fragment
 * @Date :           2021/12/22-15:52
 * @author Alvin
 * @param layoutRes 布局ID
 * @param isGoneContent false = 显示内容布局,不显示加载布局
 *                      true = 显示加载布局,网络请求成功后自动显示内容布局
 */
abstract class BaseMVVMFragment<VM : BaseViewModel, DB : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int,
    private val isGoneContent: Boolean = false
) : BaseVMFragment<VM>() {

    lateinit var dataBinding: DB

    override fun initDataBinding() {
        // 加载内容布局是否显示
        if (isGoneContent) {
            showLoadingLayout()
        } else {
            hideLoadingLayout()
        }
        dataBinding = putContentView(layoutRes)
        dataBinding.lifecycleOwner = this
    }
}