package com.alvin.mvvm_framework.ui.fragment.tv

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import com.alvin.mvvm.base.fragment.BaseMVVMFragment
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.FragmentTvDetailBinding
import com.alvin.mvvm_framework.viewmodel.fragment.tv.TVDetailViewModel
import xyz.doikki.videocontroller.StandardVideoController
import xyz.doikki.videoplayer.player.VideoView

/**
 * <h3> 作用类描述：TV详情</h3>
 *
 * @Package :        com.alvin.mvvm_framework.ui.fragment.tv
 * @Date :           2022/1/8
 * @author 高国峰
 */
class TVDetailFragment : BaseMVVMFragment<TVDetailViewModel, FragmentTvDetailBinding>(R.layout.fragment_tv_detail){

    lateinit var id: String
    lateinit var msg: String
    lateinit var song: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showLoadingLayout()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        id = arguments?.getString("id")?:""
        msg = arguments?.getString("msg")?:""
        song = arguments?.getString("song")?:""
        setTitleName(song)
    }

    override fun obtainData() {
/*        if (activity.requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            dataBinding.player.startFullScreen()
        }*/
    }

    override fun lazyLoadData() {
        loadData()
    }

    private fun loadData(){
        viewModel.detail(msg, id)
    }

    override fun registerObserver() {
        viewModel.mvDetail.observe(this){
            dataBinding.player.setUrl(it.link)
            val controller = StandardVideoController(activity)
            controller.setEnableOrientation(true)
            controller.addDefaultControlComponent("${it.mvtitle}  --  ${it.singer}",false)
            dataBinding.player.setVideoController(controller)
            dataBinding.player.start()
        }
    }

    override fun reload() {
        super.reload()
        hideErrorLayout()
        loadData()
    }

    override fun noNetClickId(): Int {
        return R.id.btnRetry
    }

    override fun onFailed(errorMsg: String?) {
        super.onFailed(errorMsg)
        showErrorLayout()
    }

    override fun onPause() {
        super.onPause()
        dataBinding.player.pause()
    }

    override fun onResume() {
        super.onResume()
        dataBinding.player.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBinding.player.release()
    }
}