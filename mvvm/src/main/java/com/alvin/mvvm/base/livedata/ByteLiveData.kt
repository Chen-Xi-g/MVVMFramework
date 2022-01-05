package com.alvin.mvvm.base.livedata

import androidx.lifecycle.MutableLiveData

/**
 * <h3> 作用类描述：自定义的Byte类型 MutableLiveData 提供了默认值，避免取值的时候还要判空</h3>
 *
 * @Package :        com.alvin.mvvm.base.livedata
 * @Date :           2021/12/24-11:05
 * @author Alvin
 */
class ByteLiveData : MutableLiveData<Byte>() {
    override fun getValue(): Byte {
        return super.getValue() ?: 0
    }
}