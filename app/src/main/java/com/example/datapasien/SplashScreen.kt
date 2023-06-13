package com.example.datapasien

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.datapasien.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val SPLASH_DELAY: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fadeInAnimator = ObjectAnimator.ofFloat(binding.ivLogo, "alpha", 0f, 1f)
        fadeInAnimator.duration = 2000
        fadeInAnimator.start()

        Handler().postDelayed({
            val fadeOutAnimator = ObjectAnimator.ofFloat(binding.ivLogo, "alpha", 1f, 0f)
            fadeOutAnimator.duration = 2000
            fadeOutAnimator.start()
            fadeOutAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                    finish()
                }
            })
        }, SPLASH_DELAY)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}