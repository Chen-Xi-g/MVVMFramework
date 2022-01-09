package com.alvin.mvvm_framework.viewmodel.fragment.tv

import com.alvin.mvvm.base.livedata.EventLiveData
import com.alvin.mvvm.base.view_model.BaseViewModel
import com.alvin.mvvm_framework.data.Constant
import com.alvin.mvvm_framework.http.request
import com.alvin.mvvm_framework.model.entity.QQMVDetailEntity

/**
 * <h3> 作用类描述：TV详情ViewModel</h3>
 *
 * @Package :        com.alvin.mvvm_framework.viewmodel.fragment.tv
 * @Date :           2022/1/8
 * @author 高国峰
 */
class TVDetailViewModel : BaseViewModel(){

    private var isFirst = true

    val mvDetail by lazy { EventLiveData<QQMVDetailEntity>() }

    fun detail(msg: String, id: String){
        request(
            { Constant.service.detailMV(msg, id) },
            {
                mvDetail.setValue(it)
            },isLoading = isFirst
        )
        if (isFirst){
            isFirst = false
        }
    }
}