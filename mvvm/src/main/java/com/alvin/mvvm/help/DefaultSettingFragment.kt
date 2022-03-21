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
package com.alvin.mvvm.help

import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment

/**
 * <h3> 作用类描述：默认的Fragment设置</h3>
 *
 * @Package :        com.alvin.mvvm.help
 * @Date :           2021/9/2-17:12
 * @author Alvin
 */
class DefaultSettingFragment : ISettingBaseFragment {
    override fun isWidth(): Boolean? {
        return null
    }

    override fun setTitleLayoutView(
        fragment: Fragment?,
        ibBarBack: AppCompatImageButton?,
        tvBarTitle: AppCompatTextView?,
        tvBarRight: AppCompatTextView?,
        ibBarRight: AppCompatImageButton?
    ) {

    }

    override fun isErrorToast(): Boolean {
        return super.isErrorToast()
    }

}