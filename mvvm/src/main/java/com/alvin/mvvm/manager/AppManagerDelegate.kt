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
import android.os.Process
import android.util.Log
import java.lang.ref.WeakReference
import java.util.*
import kotlin.system.exitProcess

/**
 * <h3> 作用类描述：</h3>
 *
 * @Package :        com.alvin.mvvm.manager
 * @Date :           2021/9/16-15:29
 * @author Alvin
 */
class AppManagerDelegate private constructor() {
    companion object {
        val instance: AppManagerDelegate by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppManagerDelegate()
        }
    }

    // 使用弱引用是因为存在未使用AppManager的finish方法来释放的activity，但mActivityStack并未断开对其应用导致内存泄露的问题
    private val mActivityStack: Stack<WeakReference<Activity>> = Stack()

    /**
     * 添加Activity到堆栈
     *
     * @param activity activity实例
     */
    fun addActivity(activity: Activity) {
        mActivityStack.add(WeakReference(activity))
    }

    /**
     * 检查弱引用是否释放，若释放，则从栈中清理掉该元素
     */
    fun checkWeakReference() {
        val it = mActivityStack.iterator()
        while (it.hasNext()) {
            val activityReference = it.next()
            val temp = activityReference.get()
            if (temp == null) {
                it.remove() // 使用迭代器来进行安全的加锁操作
            }
        }
    }

    /**
     * 获取当前Activity（栈中最后一个压入的）
     *
     * @return 当前（栈顶）activity
     */
    fun currentActivity(): Activity? {
        checkWeakReference()
        return if (!mActivityStack.isEmpty()) {
            mActivityStack.lastElement().get()
        } else null
    }

    /**
     * 结束除当前activity以外的所有activity
     * 注意：不能使用foreach遍历并发删除，会抛出java.util.ConcurrentModificationException的异常
     *
     * @param activity 不需要结束的activity
     */
    fun finishOtherActivity(activity: Activity?) {
        if (activity != null) {
            val it = mActivityStack.iterator()
            while (it.hasNext()) {
                val activityReference = it.next()
                val temp = activityReference.get()
                // 清理掉已经释放的activity
                if (temp == null) {
                    it.remove()
                    continue
                }
                // 使用迭代器来进行安全的加锁操作
                if (temp != activity) {
                    it.remove()
                    temp.finish()
                }
            }
        }
    }

    /**
     * 结束除当前activity以外的所有activity
     * 注意：不能使用foreach遍历并发删除，会抛出java.util.ConcurrentModificationException的异常
     *
     * @param activity 不需要结束的activity
     */
    fun finishOtherActivity(activity: String?) {
        if (activity != null) {
            val it = mActivityStack.iterator()
            while (it.hasNext()) {
                val activityReference = it.next()
                val temp = activityReference.get()
                // 清理掉已经释放的activity
                if (temp == null) {
                    it.remove()
                    continue
                }
                // 使用迭代器来进行安全的加锁操作
                if (temp.localClassName != activity) {
                    it.remove()
                    temp.finish()
                }
            }
        }
    }

    /**
     * 结束除这一类activtiy以外的所有activity
     *
     * @param cls 指定的某类activity
     */
    fun finishOtherActivity(cls: Class<*>) {
        val it = mActivityStack.iterator()
        while (it.hasNext()) {
            val activityReference = it.next()
            val activity = activityReference.get()
            // 清理掉已经释放的activity
            if (activity == null) {
                it.remove()
                continue
            }
            // 使用迭代器来进行安全的加锁操作
            if (activity.javaClass != cls) {
                it.remove()
                activity.finish()
            }
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = currentActivity()
        activity?.let { finishActivity(it) }
    }

    /**
     * 移除指定的Activity
     *
     * @param activity 指定的activity实例
     */
    fun removeActivity(activity: Activity?) {
        if (activity != null) {
            val it = mActivityStack.iterator()
            while (it.hasNext()) {
                val activityReference = it.next()
                val temp = activityReference.get()
                if (temp == null) { // 清理掉已经释放的activity
                    it.remove()
                    continue
                }
                if (temp == activity) {
                    it.remove()
                }
            }
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param activity 指定的activity实例
     */
    fun finishActivity(activity: Activity?) {
        if (activity != null) {
            val it = mActivityStack.iterator()
            while (it.hasNext()) {
                val activityReference = it.next()
                val temp = activityReference.get()
                if (temp == null) { // 清理掉已经释放的activity
                    it.remove()
                    continue
                }
                if (temp == activity) {
                    it.remove()
                }
            }
            activity.finish()
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param activity 指定的activity实例
     */
    fun finishActivity(activity: String?) {
        if (activity != null) {
            var finishActivity: Activity? = null
            val it = mActivityStack.iterator()
            while (it.hasNext()) {
                val activityReference = it.next()
                val temp = activityReference.get()
                if (temp == null) { // 清理掉已经释放的activity
                    it.remove()
                    continue
                }
                if (temp.localClassName == activity) {
                    finishActivity = temp
                    it.remove()
                }
            }
            finishActivity?.finish()
        }
    }

    /**
     * 结束指定类名的所有Activity
     *
     * @param cls 指定的类的class
     */
    fun finishActivity(cls: Class<*>) {
        val it = mActivityStack.iterator()
        while (it.hasNext()) {
            val activityReference = it.next()
            val activity = activityReference.get()
            if (activity == null) { // 清理掉已经释放的activity
                it.remove()
                continue
            }
            if (activity.javaClass == cls) {
                it.remove()
                activity.finish()
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        val it: Iterator<WeakReference<Activity>> = mActivityStack.iterator()
        while (it.hasNext()) {
            val activityReference = it.next()
            val activity = activityReference.get()
            activity?.finish()
        }
        mActivityStack.clear()
    }

    /**
     * 退出应用程序
     */
    fun exitApp() {
        try {
            finishAllActivity()
            // 从操作系统中结束掉当前程序的进程
            Process.killProcess(Process.myPid())
            // 退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            exitProcess(0)
        } catch (e: Exception) {
            Log.e("Exit exception", e.message!!)
        }
    }
}