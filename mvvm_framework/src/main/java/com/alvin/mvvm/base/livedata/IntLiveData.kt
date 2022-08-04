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

import androidx.lifecycle.MutableLiveData

/**
 * <h3> 作用类描述：自定义的Int类型 MutableLiveData 提供了默认值，避免取值的时候还要判空</h3>
 *
 * @Package :        com.alvin.mvvm.base.livedata
 * @Date :           2021/12/24-11:09
 * @author Alvin
 */
class IntLiveData : MutableLiveData<Int>() {

    override fun getValue(): Int {
        return super.getValue() ?: 0
    }
}