package com.alvin.mvvm_framework.base

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

/**
 * <h3> 作用类描述：全局扩展函数</h3>
 *
 * @Package :        com.alvin.mvvm_framework.base
 * @Date :           2022/3/4
 * @author 高国峰
 */

/**
 * 快速创建Intent， 用于Activity之间跳转
 *
 * @param cls 需要跳转的Activity
 * @param block 方法体
 * @return
 */
fun Context.intent(cls: Class<*>, block: Intent.() -> Unit = {}): Intent {
    return Intent(this, cls).apply(block)
}

/**
 * 快速创建Intent， 用于Activity之间跳转
 *
 * @param cls 需要跳转的Activity
 * @param block 方法体
 * @return
 */
fun Fragment.intent(cls: Class<*>, block: Intent.() -> Unit = {}): Intent {
    return Intent(requireContext(), cls).apply(block)
}