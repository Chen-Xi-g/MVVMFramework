package com.alvin.mvvm.base.livedata

import android.text.TextUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * <h3> 作用类描述：自定义LiveData  防止数据倒灌</h3>
 *
 * @Package :        com.alvin.mvvm.base.livedata
 * @Date :           2021/12/20-11:24
 * @author Alvin
 */
open class ProtectedUnPeekLiveData<T> : LiveData<T>() {

    /**
     * 在源码中，LiveData 是根据Version来判断观察者和被观察者消息
     */
    private val startVersion = -1

    private val mCurrentVersion = AtomicInteger(startVersion)

    /**
     * 是否允许空数据
     */
    var isAllowNullValue: Boolean = true


    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, createObserverWrapper(observer, mCurrentVersion.get()))
    }

    override fun observeForever(observer: Observer<in T>) {
        super.observeForever(createObserverForeverWrapper(observer, mCurrentVersion.get()))
    }

    fun observeSticky(owner: LifecycleOwner, observer: Observer<T>) {
        super.observe(owner, createObserverWrapper(observer, startVersion))
    }

    fun observeStickyForever(observer: Observer<in T>) {
        super.observeForever(createObserverForeverWrapper(observer, startVersion))
    }

    override fun setValue(value: T) {
        mCurrentVersion.getAndIncrement()
        super.setValue(value)
    }

    /**
     * 1.添加一个包装类，自己维护一个版本号判断，用于无需 map 的帮助也能逐一判断消费情况
     * 2.重写 equals 方法和 hashCode，在用于手动 removeObserver 时，忽略版本号的变化引起的变化
     */
    inner class ObserverWrapper constructor(
        private val observer: Observer<in T>,
        private val version: Int,
        private val isForever: Boolean
    ) : Observer<T> {

        constructor(
            observer: Observer<in T>,
            version: Int
        ) : this(observer, version, false)

        override fun onChanged(t: T) {
            if (mCurrentVersion.get() > version && (t != null || isAllowNullValue)) {
                observer.onChanged(t)
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }
            if (other == null || javaClass != other) {
                return false
            }
            val that = other as ProtectedUnPeekLiveData<*>.ObserverWrapper
            return Objects.equals(observer, that.observer)
        }

        override fun hashCode(): Int {
            return Objects.hashCode(observer)
        }

        override fun toString(): String {
            return if (isForever) {
                "IS_FOREVER"
            } else {
                ""
            }
        }
    }

    override fun removeObserver(observer: Observer<in T>) {
        if (TextUtils.isEmpty(observer.toString())) {
            super.removeObserver(observer)
        } else {
            super.removeObserver(createObserverWrapper(observer, startVersion))
        }
    }

    private fun createObserverForeverWrapper(
        observer: Observer<in T>,
        version: Int
    ): ObserverWrapper {
        return ObserverWrapper(observer, version, true)
    }

    private fun createObserverWrapper(observer: Observer<in T>, version: Int): ObserverWrapper {
        return ObserverWrapper(observer, version)
    }

    /**
     * 手动将消息从内存中清空，
     * 以免无用消息随着 SharedViewModel 的长时间驻留而导致内存溢出的发生。
     */
    fun clear() {
        super.setValue(null)
    }
}