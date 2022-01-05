package com.alvin.mvvm.base.livedata

import androidx.lifecycle.MutableLiveData

/**
 * <h3> 作用类描述：自定义的Int类型 MutableLiveData 提供了默认值，避免取值的时候还要判空</h3>
 *
 * @Package :        com.alvin.mvvm.base.livedata
 * @Date :           2021/12/24-11:09
 * @author Alvin
 */
class IntLiveData : MutableLiveData<Int>() {

    override fun getValue(): Int {
        return super.getValue() ?: 0
    }
}