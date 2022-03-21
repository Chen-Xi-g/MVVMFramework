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
import java.util.*
import kotlin.system.exitProcess

/**
 * <h3> 作用类描述：栈内Activity管理</h3>
 *
 * @Package :        com.alvin.mvvm.manager
 * @Date :           2021/9/16-15:28
 * @author Alvin
 *
 * @param isBridge 是否使用桥接模式
 */
class AppManager private constructor(private val isBridge: Boolean) {
    companion object {
        val instance: AppManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AppManager(true)
        }
    }

    /**
     * AppManager管理activity的委托类
     */
    private val mDelegate: AppManagerDelegate = AppManagerDelegate.instance

    /**
     * 维护activity的栈结构
     */
    private val mActivityStack: Stack<Activity> = Stack()

    /**
     * 添加Activity到堆栈
     *
     * @param activity activity实例
     */
    fun addActivity(activity: Activity?) {
        if (isBridge) {
            mDelegate.addActivity(activity!!)
        } else {
            mActivityStack.add(activity)
        }
    }

    /**
     * 获取当前Activity（栈中最后一个压入的）
     *
     * @return 当前（栈顶）activity
     */
    fun currentActivity(): Activity? {
        return if (isBridge) {
            mDelegate.currentActivity()
        } else {
            if (!mActivityStack.isEmpty()) {
                mActivityStack.lastElement()
            } else null
        }
    }

    /**
     * 结束除当前activtiy以外的所有activity
     * 注意：不能使用foreach遍历并发删除，会抛出java.util.ConcurrentModificationException的异常
     *
     * @param activity 不需要结束的activity
     */
    fun finishOtherActivity(activity: Activity) {
        if (isBridge) {
            mDelegate.finishOtherActivity(activity)
        } else {
            val it: Iterator<Activity> = mActivityStack.iterator()
            while (it.hasNext()) {
                val temp = it.next()
                if (temp != activity) {
                    finishActivity(temp)
                }
            }
        }
    }

    /**
     * 结束除当前activtiy以外的所有activity
     * 注意：不能使用foreach遍历并发删除，会抛出java.util.ConcurrentModificationException的异常
     *
     * @param activity 不需要结束的activity
     */
    fun finishOtherActivity(activity: String) {
        if (isBridge) {
            mDelegate.finishOtherActivity(activity)
        } else {
            val it: Iterator<Activity> = mActivityStack.iterator()
            while (it.hasNext()) {
                val temp = it.next()
                if (temp.localClassName != activity) {
                    finishActivity(temp)
                }
            }
        }
    }

    /**
     * 结束除这一类activtiy以外的所有activity
     * 注意：不能使用foreach遍历并发删除，会抛出java.util.ConcurrentModificationException的异常
     *
     * @param cls 不需要结束的activity
     */
    fun finishOtherActivity(cls: Class<*>) {
        if (isBridge) {
            mDelegate.finishOtherActivity(cls)
        } else {
            val it: Iterator<Activity> = mActivityStack.iterator()
            while (it.hasNext()) {
                val activity = it.next()
                if (activity.javaClass != cls) {
                    finishActivity(activity)
                }
            }
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        if (isBridge) {
            mDelegate.finishActivity()
        } else {
            if (!mActivityStack.isEmpty()) {
                val activity = mActivityStack.lastElement()
                finishActivity(activity)
            }
        }
    }

    /**
     * 从栈中移除Activity 不执行Finish
     *
     * @param activity 指定的activity实例
     */
    fun removeActivity(activity: Activity?) {
        if (isBridge) {
            mDelegate.removeActivity(activity)
        } else {
            if (activity != null) {
                if (mActivityStack.contains(activity)) { // 兼容未使用AppManager管理的实例
                    mActivityStack.remove(activity)
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
        if (isBridge) {
            mDelegate.finishActivity(activity)
        } else {
            if (activity != null) {
                if (mActivityStack.contains(activity)) { // 兼容未使用AppManager管理的实例
                    mActivityStack.remove(activity)
                }
                activity.finish()
            }
        }
    }


    /**
     * 结束指定的Activity
     *
     * @param activity 指定的activity实例
     */
    fun finishActivity(activity: String?) {
        if (isBridge) {
            mDelegate.finishActivity(activity)
        } else {
            if (activity != null) {
                val map = mActivityStack.map { it.localClassName }
                if (map.contains(activity)) { // 兼容未使用AppManager管理的实例
                    val indexOf = map.indexOf(activity)
                    mActivityStack.remove(mActivityStack[indexOf])
                }
                mActivityStack[map.indexOf(activity)].finish()
            }
        }
    }

    /**
     * 结束指定类名的所有Activity
     *
     * @param cls 指定的类的class
     */
    fun finishActivity(cls: Class<*>) {
        if (isBridge) {
            mDelegate.finishActivity(cls)
        } else {
            val it: Iterator<Activity> = mActivityStack.iterator()
            while (it.hasNext()) {
                val activity = it.next()
                if (activity.javaClass == cls) {
                    finishActivity(activity)
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        if (isBridge) {
            mDelegate.finishAllActivity()
        } else {
            var i = 0
            val size = mActivityStack.size
            while (i < size) {
                if (null != mActivityStack[i]) {
                    mActivityStack[i].finish()
                }
                i++
            }
            mActivityStack.clear()
        }
    }

    /**
     * 退出应用程序
     */
    fun exitApp() {
        if (isBridge) {
            mDelegate.exitApp()
        } else {
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
}