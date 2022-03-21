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
package com.alvin.mvvm.manager

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * <h3> 作用类描述：生命周期回调</h3>
 *
 * @Package :        com.alvin.mvvm.manager
 * @Date :           2021/9/16-15:41
 * @author Alvin
 */
class LifecycleCallback : Application.ActivityLifecycleCallbacks {

    private val appManager = AppManager.instance

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        appManager.addActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        // 使用RemoveActivity而不是使用FinishActivity。
        // Activity横竖屏切换、主题切换时，Activity会重新执行生命周期，避免应用崩溃。
        appManager.removeActivity(activity)
    }
}