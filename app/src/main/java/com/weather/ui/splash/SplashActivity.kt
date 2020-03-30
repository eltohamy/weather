package com.weather.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.weather.R
import com.weather.databinding.ActivitySplashBinding
import com.weather.ui.base.BaseActivity
import com.weather.ui.home.HomeActivity

/**
 * Created by Tohamy on 30/03/2020
 */
class SplashActivity : BaseActivity() {

    companion object {
        const val SPLASH_TIME: Long = 2000
    }

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        startAnimation()
        Handler().postDelayed({
            openHomeActivity()
        }, SPLASH_TIME)
    }

    private fun openHomeActivity() {
        finish()
        val intent = Intent(this@SplashActivity, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun startAnimation() {
        val set = AnimatorSet()

        val animator1 = ObjectAnimator.ofFloat(binding.logo, "alpha", 0f, 1f)
        animator1.repeatCount = 0
        animator1.duration = 1000

        val animator2 = ObjectAnimator.ofFloat(binding.logo, "scaleX", 0f, 1f)
        animator2.repeatCount = 0
        animator2.duration = 1000

        val animator3 = ObjectAnimator.ofFloat(binding.logo, "scaleY", 0f, 1f)
        animator3.repeatCount = 0
        animator3.duration = 1000

        set.playTogether(animator1, animator2, animator3)

        set.start()
    }
}