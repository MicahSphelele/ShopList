package com.shoplist.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shoplist.R
import com.shoplist.databinding.ActivitySplashBinding
import com.shoplist.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        binding.appVersion = Constants.getAppVersion(this.application)
    }

    @SuppressLint("CheckResult")
    override fun onStart() {
        super.onStart()
        Completable.timer(
            3, TimeUnit.SECONDS,
            AndroidSchedulers.mainThread()
        ).subscribe {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}