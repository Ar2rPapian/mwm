package com.leet.monolith.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.leet.monolith.R
import com.leet.monolith.data.User
import com.leet.monolith.util.animateProgressBar
import com.leet.monolith.util.getStringFromSharedPreferences
import kotlinx.android.synthetic.main.activity_splash.*
import kotlin.random.Random


const val SPLASH_TIME_OUT_MAX = 3000
const val SPLASH_TIME_OUT_MIN = 1500


class SplashActivity : BaseActivity() {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_splash)
        val splashTimeout:Int = Random.nextInt(SPLASH_TIME_OUT_MIN, SPLASH_TIME_OUT_MAX)

        animateProgressBar(progressBar = progress_bar)



        Handler().postDelayed({
            val user = getStringFromSharedPreferences(this, "user_name_")
            if(user != null && user.isNotEmpty()){
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }else{
                startActivity(Intent(this@SplashActivity, LaunchActivity::class.java))
            }
            finish()
        }, splashTimeout.toLong())
    }

}

