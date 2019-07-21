package com.leet.monolith.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.leet.monolith.data.User
import com.leet.monolith.util.animateProgressBar
import com.leet.monolith.util.getStringFromSharedPreferences
import kotlin.random.Random


const val SPLASH_TIME_OUT_MAX = 3000
const val SPLASH_TIME_OUT_MIN = 1500


class SplashActivity : BaseActivity() {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(com.leet.monolith.R.layout.activity_splash)
        val splashTimeout:Int = Random.nextInt(SPLASH_TIME_OUT_MIN, SPLASH_TIME_OUT_MAX)

        animateProgressBar(findViewById(com.leet.monolith.R.id.progress_bar))

        Handler().postDelayed({
            val user = getStringFromSharedPreferences(this, "user_name")
            if(user != null && user.length > 0){
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }else{
                startActivity(Intent(this@SplashActivity, LaunchActivity::class.java))
            }
            finish()
        }, splashTimeout.toLong())
    }

}

