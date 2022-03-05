package com.alvin.mvvm_framework

import android.animation.Animator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.alvin.mvvm_framework.base.intent
import com.alvin.mvvm_framework.ui.activity.SampleMainActivity
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar

/**
 * <h3> 作用类描述：启动页Activity</h3>
 *
 * @Package :        com.alvin.mvvm_framework
 * @Date :           2022/3/5
 * @author 高国峰
 */
class LaunchActivity : AppCompatActivity() {

    private lateinit var lottieAnimator: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_activity_launch)
        immersionBar {
            hideBar(BarHide.FLAG_HIDE_BAR)
        }
        lottieAnimator = findViewById(R.id.lottieAnimation)
        lottieAnimator.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                startActivity(intent(SampleMainActivity::class.java))
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }
}