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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * <h3> 作用类描述：Activity的抽象基类，这个类里面的方法适用于全部activity的需求 </h3>
 *
 * @ProjectName:    MVVM_Framework
 * @Package:        com.alvin.mvvm.base.activity
 * @ClassName:      AbstractActivity
 * @CreateDate:      2021/12/20
 * @Description:    Activity的抽象基类，这个类里面的方法适用于全部activity的需求
 * @author:          Alvin
 */
abstract class AbstractActivity : AppCompatActivity() {

    /**
     * 实现initView来做视图相关的初始化
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 实现obtainData来做数据的初始化
     */
    abstract fun obtainData()
}