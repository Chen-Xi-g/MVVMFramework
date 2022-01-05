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