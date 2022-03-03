package com.alvin.mvvm.base.livedata

import androidx.lifecycle.MutableLiveData

/**
 * <h3> 作用类描述：自定义的Long类型 MutableLiveData 提供了默认值，避免取值的时候还要判空</h3>
 *
 * @Package :        com.alvin.mvvm.base.livedata
 * @Date :           2022/3/3
 * @author 高国峰
 */
class LongLiveData : MutableLiveData<Long>() {

    override fun getValue(): Long {
        return super.getValue() ?: 0L
    }
}