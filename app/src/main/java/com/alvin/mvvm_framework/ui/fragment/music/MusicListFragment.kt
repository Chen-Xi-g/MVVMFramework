package com.alvin.mvvm_framework.ui.fragment.music

import com.alvin.mvvm.base.fragment.BaseListFragment
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.FragmentMusicListBinding
import com.alvin.mvvm_framework.ui.fragment.music.adapter.MusicListAdapter
import com.alvin.mvvm_framework.viewmodel.fragment.music.MusicListViewModel

/**
 * <h3> 作用类描述：Music列表</h3>
 *
 * @Package :        com.alvin.mvvm_framework.ui.fragment.music
 * @Date :           2022/1/7
 * @author 高国峰
 */
class MusicListFragment : BaseListFragment<MusicListViewModel, FragmentMusicListBinding>(R.layout.fragment_music_list){

    private val list = mutableListOf("音乐1","音乐2","音乐3")

    private val adapter by lazy(LazyThreadSafetyMode.NONE){
        MusicListAdapter()
    }

    override fun obtainData() {
    }

    override fun lazyLoadData() {
        loadData()
    }

    override fun loadData() {
        initTest()
    }

    override fun titleLayoutView(): Boolean {
        return false
    }

    /**
     * 本地测试数据
     */
    private fun initTest(){
        recyclerView.adapter = adapter
        rootRefresh.finish(list,adapter)
    }

    override fun registerObserver() {
    }
}