package com.alvin.mvvm.base.livedata

import androidx.lifecycle.MutableLiveData

/**
 * <h3> 作用类描述：自定义的String类型 MutableLiveData 提供了默认值，避免取值的时候还要判空</h3>
 *
 * @Package :        com.alvin.mvvm.base.livedata
 * @Date :           2021/12/24-11:10
 * @author Alvin
 */
class StringLiveData : MutableLiveData<String>() {

    override fun getValue(): String {
        return super.getValue() ?: ""
    }

}