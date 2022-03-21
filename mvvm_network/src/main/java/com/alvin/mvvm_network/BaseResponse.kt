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
package com.alvin.mvvm_network

/**
 * <h3> 作用类描述：服务器返回数据的基类, 用来做JSON脱壳处理</h3>
 *
 * @Package :        com.alvin.mvvm_network
 * @Date :           2021/12/23-15:28
 * @author 高国峰
 */
abstract class BaseResponse<T> {

    /**
     * 通过服务器的Code码返回是否成功
     *
     * @return
     */
    abstract fun isSuccess(): Boolean

    /**
     * 服务器返回的Code
     *
     * @return Code值
     */
    abstract fun getResponseCode(): Int

    /**
     * 服务器返回的Data数据
     *
     * @return Data 数据
     */
    abstract fun getResponseData(): T?

    /**
     * 服务器返回的Message信息
     *
     * @return Message 信息
     */
    abstract fun getResponseMessage(): String
}