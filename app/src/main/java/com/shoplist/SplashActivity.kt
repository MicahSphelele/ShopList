package com.shoplist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.shoplist.util.Constants
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        txtVersion.text = Constants.getAppVersion(this.application)
    }

    @SuppressLint("CheckResult")
    override fun onStart() {
        super.onStart()
        Completable.timer(
            3, TimeUnit.SECONDS,
            AndroidSchedulers.mainThread()
        ).subscribe {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}