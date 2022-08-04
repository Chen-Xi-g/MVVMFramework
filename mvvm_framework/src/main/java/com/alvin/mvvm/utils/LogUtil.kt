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
package com.alvin.mvvm.utils

import android.util.Log
import java.util.*

/**
 * <h3> 作用类描述：日志工具类</h3>
 *
 * @Package :        com.alvin.utils
 * @Date :           2021/8/31-16:15
 * @author 高国峰
 */
object LogUtil {
    /**
     * 最小堆栈抵消数
     */
    private const val MIN_STACK_OFFSET = 3

    /**
     * 日志默认Tag
     */
    var defaultTag = "LogUtil"

    /**
     * 解析分隔符
     */
    private val lineSeparator = System.getProperty("line.separator", "/n")

    private const val V = Log.VERBOSE
    private const val D = Log.DEBUG
    private const val I = Log.INFO
    private const val W = Log.WARN
    private const val E = Log.ERROR
    private const val A = Log.ASSERT

    private const val TOP_BORDER =
        "╔═══════════════════════════════════════════════════════════════════════════════════════════════════"
    private const val LEFT_BORDER = "║ "
    private const val BOTTOM_BORDER =
        "╚═══════════════════════════════════════════════════════════════════════════════════════════════════"
    private const val MAX_LEN = 1000

    /**
     * 是否开启日志
     * 建议在Debug模式下开启。
     */
    var open = true

    /**
     * 输出堆栈内容 便于定位
     */
    private fun processTagAndHead(): String {
        val elements = Thread.currentThread().stackTrace
        val offset = getStackOffset(elements)
        val targetElement = elements[offset]
        val head = Formatter()
            .format(
                "%s [%s(%s:%d)]",
                "In Thread: " + Thread.currentThread().name,
                targetElement.methodName,
                targetElement.fileName,
                targetElement.lineNumber
            )

        return head.toString()
    }

    private fun processMsgBody(msg: String, flag: Int, tag: String = defaultTag) {
        printTop(flag, tag)
        // 首先打印调用信息
        printLog(flag, tag)

        val lineCount = msg.length / MAX_LEN
        if (lineCount == 0) {
            printLog(flag, tag, msg)
        } else {
            var index = 0
            var i = 0
            while (true) {
                printLog(flag, tag, msg.substring(index, index + MAX_LEN))
                index += MAX_LEN
                if ((++i) >= lineCount)
                    break
            }
        }
        printBottom(flag, tag)
    }

    private fun getStackOffset(trace: Array<StackTraceElement>): Int {
        var i = MIN_STACK_OFFSET
        while (i < trace.size) {
            val e = trace[i]
            val name = e.className
            if (name != LogUtil::class.java.name) {
                return i
            }
            i++
        }
        return 2
    }

    /* 虽然 kotlin 有默认值这种操作，但是 Log.i(tag,msg) 这种比较符合平时的操作，所以还是提供类似的重载，
     * 而非 LogUtil.i(msg: String,tag: String = defaultTAG) 这种带默认值参数的方法 */

    /**
     * 输出大于等于`VERBOSE`的日志
     *
     * @param msg 日志内容
     */
    fun v(msg: String) {
        v(defaultTag, msg)
    }

    /**
     * 输出大于等于`INFO`的日志
     *
     * @param msg 日志内容
     */
    fun i(msg: String) {
        i(defaultTag, msg)
    }

    /**
     * 输出大于等于`DEBUG`的日志
     *
     * @param msg 日志内容
     */
    fun d(msg: String) {
        d(defaultTag, msg)
    }

    /**
     * 输出大于等于`WARN`的日志
     *
     * @param msg 日志内容
     */
    fun w(msg: String) {
        w(defaultTag, msg)
    }

    /**
     * 输出大于等于`ERROR`的日志
     *
     * @param msg 日志内容
     */
    fun e(msg: String) {
        e(defaultTag, msg)
    }

    /**
     * 输出大于等于VERBOSE的日志
     *
     * @param tag 日志Tag值
     * @param msg 日志内容
     */
    fun v(tag: String, msg: String) {
        if (!open) {
            return
        }
        processMsgBody(msg, V, tag)
    }

    /**
     * 输出大于等于`INFO`的日志
     *
     * @param tag 日志Tag值
     * @param msg 日志内容
     */
    fun i(tag: String, msg: String) {
        if (!open) {
            return
        }
        processMsgBody(msg, I, tag)
    }

    /**
     * 输出大于等于`DEBUG`的日志
     *
     * @param tag 日志Tag值
     * @param msg 日志内容
     */
    fun d(tag: String, msg: String) {
        if (!open) {
            return
        }
        processMsgBody(msg, D, tag)
    }

    /**
     * 输出大于等于`WARN`的日志
     *
     * @param tag 日志Tag值
     * @param msg 日志内容
     */
    fun w(tag: String, msg: String) {
        if (!open) {
            return
        }
        processMsgBody(msg, W, tag)
    }

    /**
     * 输出`ERROR`的日志
     *
     * @param tag 日志Tag值
     * @param msg 日志内容
     */
    fun e(tag: String, msg: String) {
        if (!open) {
            return
        }
        processMsgBody(msg, E, tag)
    }

    /**
     * 输出日志内容
     *
     * @param tag 日志Tag值
     * @param msg 日志内容
     */
    fun printLog(flag: Int, tag: String, msg: String = processTagAndHead()) {
        Log.println(flag, tag, LEFT_BORDER + msg)
    }

    /**
     * 输出底部日志模板
     *
     * @param flag 设置日志的优先级
     * @param tag 日志Tag值
     */
    fun printBottom(flag: Int, tag: String) {
        Log.println(flag, tag, BOTTOM_BORDER)
    }

    /**
     * 输出顶部日志模板
     *
     * @param flag 设置日志的优先级
     * @param tag 日志Tag值
     */
    fun printTop(flag: Int, tag: String) {
        Log.println(flag, tag, TOP_BORDER)
    }

    /**
     * 关闭所有Log
     */
    fun closeLog() {
        this.open = false
    }
}