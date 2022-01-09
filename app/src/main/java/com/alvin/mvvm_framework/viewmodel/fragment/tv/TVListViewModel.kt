package com.alvin.mvvm_framework.viewmodel.fragment.tv

import android.view.View
import android.widget.Toast
import com.alvin.mvvm.BaseApplication
import com.alvin.mvvm.base.livedata.EventLiveData
import com.alvin.mvvm.base.livedata.StringLiveData
import com.alvin.mvvm.base.view_model.BaseViewModel
import com.alvin.mvvm_framework.TVApplication
import com.alvin.mvvm_framework.data.AppService
import com.alvin.mvvm_framework.data.Constant
import com.alvin.mvvm_framework.http.request
import com.alvin.mvvm_framework.model.entity.QQMVListEntity
import com.alvin.mvvm_network.HttpManager

/**
 * <h3> 作用类描述：TV列表</h3>
 *
 * @Package :        com.alvin.mvvm_framework.viewmodel.fragment.tv
 * @Date :           2022/1/7
 * @author 高国峰
 */
class TVListViewModel : BaseViewModel() {

    private var isFirst = true

    val searchMsg by lazy { StringLiveData() }

    /**
     * 获取QQMV列表
     */
    val qqMVList by lazy { EventLiveData<List<QQMVListEntity>>() }

    /**
     * 搜索QQMV
     *
     * @param msg MV名称
     */
    fun searchQQMV(msg: String) {
        if (msg.trim().isEmpty()){
            httpCallback.onFailed.value = "请输入内容"
            return
        }
        request(
            { Constant.service.searchQQMV(msg) },
            {
                qqMVList.setValue(it)
            }, isLoading = isFirst
        )
        if (isFirst){
            isFirst = false
        }
    }

}