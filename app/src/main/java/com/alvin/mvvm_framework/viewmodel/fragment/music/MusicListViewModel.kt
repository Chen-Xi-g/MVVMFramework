package com.alvin.mvvm_framework.viewmodel.fragment.music

import com.alvin.mvvm.base.livedata.StringLiveData
import com.alvin.mvvm.base.view_model.BaseViewModel

/**
 * <h3> 作用类描述：Music列表</h3>
 *
 * @Package :        com.alvin.mvvm_framework.viewmodel.fragment.music
 * @Date :           2022/1/7
 * @author 高国峰
 */
class MusicListViewModel : BaseViewModel(){

    /**
     * Item内容
     */
    val itemContent by lazy { StringLiveData() }
}