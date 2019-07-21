package com.leet.monolith.view.activities

import android.content.Intent
import android.os.Bundle
import com.leet.monolith.R
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : BaseActivity() {



    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_launch)
        initClicks()
    }

    private fun initClicks(){
        signInButton.setOnClickListener {
            startActivity(Intent(this@LaunchActivity, SignInActivity::class.java))
        }
        signUpButton.setOnClickListener {
            startActivity(Intent(this@LaunchActivity, SignUpActivity::class.java))
        }
    }

}