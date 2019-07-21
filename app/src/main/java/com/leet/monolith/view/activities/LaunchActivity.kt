package com.leet.monolith.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.leet.monolith.R
import kotlinx.android.synthetic.main.activity_launch.*
import kotlinx.android.synthetic.main.activity_launch.sing_in_button
import kotlinx.android.synthetic.main.activity_sign_in.*

class LaunchActivity : BaseActivity() {



    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_launch)
        initClicks()
    }

    private fun initClicks(){
        sing_in_button.setOnClickListener {
            startActivity(Intent(this@LaunchActivity, SignInActivity::class.java))
        }
        sing_up_button.setOnClickListener {
            startActivity(Intent(this@LaunchActivity, SignUpActivity::class.java))
        }
    }

}