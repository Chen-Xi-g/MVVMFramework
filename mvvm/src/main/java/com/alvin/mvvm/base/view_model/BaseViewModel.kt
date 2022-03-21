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
package com.alvin.mvvm.base.view_model

import androidx.lifecycle.ViewModel
import com.alvin.mvvm.base.livedata.BooleanLiveData
import com.alvin.mvvm.base.livedata.EventLiveData
import com.alvin.mvvm.base.livedata.StringLiveData

/**
 * <h3> 作用类描述：所有ViewModel的基类</h3>
 *
 * @Package :        com.alvin.mvvm.base.view_model
 * @Date :           2021/12/20-13:53
 * @author Alvin
 */
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