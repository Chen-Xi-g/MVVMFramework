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

/**
 * <h3> 作用类描述：网络请求开始时， 是否显示 Loading 和 Dialog </h3>
 *
 * @Package :        com.alvin.mvvm.base.view_model
 * @Date :           2022/1/4-17:17
 * @author Alvin
 */
data class LoadingEntity(
    val isLoading: Boolean = false,
    val isDialog: Boolean = true,
    val dialogContent: String = ""
)