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
package com.alvin.mvvm.base.livedata

/**
 * <h3> 作用类描述：包装类</h3>
 *
 * @Package :        com.alvin.mvvm.base.livedata
 * @Date :           2021/12/20-11:45
 * @author Alvin
 */
open class PeekLiveData<T> : ProtectedUnPeekLiveData<T>() {
    public override fun setValue(value: T) {
        super.setValue(value)
    }

    public override fun postValue(value: T) {
        super.postValue(value)
    }

    open class Builder<T> {
        /**
         * 是否允许传入 null value
         */
        private var isAllowNullValue = false

        fun setAllowNullValue(isAllowNullValue: Boolean): Builder<T> {
            this.isAllowNullValue = isAllowNullValue
            return this
        }

        fun create(): PeekLiveData<T> {
            val liveData = PeekLiveData<T>()
            liveData.isAllowNullValue = this.isAllowNullValue
            return liveData
        }

    }
}