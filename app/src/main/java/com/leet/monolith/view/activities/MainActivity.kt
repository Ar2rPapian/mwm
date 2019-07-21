package com.leet.monolith.view.activities

import android.content.Intent
import android.os.Bundle
import com.leet.monolith.R
import com.leet.monolith.data.User
import com.leet.monolith.util.getStringFromSharedPreferences
import com.leet.monolith.util.removeStringFromSharedPreferences
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_main)
        val name = getStringFromSharedPreferences(this, "user_name")
        val lastName = getStringFromSharedPreferences(this, "user_last_name")
        val email = getStringFromSharedPreferences(this, "user_email")
        val user = User(name?:"ERROR", lastName?:"ERROR", email?:"ERROR", "******")
        userNameTv.text = user.name
        userLastNameTv.text = user.lastName

        signOutButton.setOnClickListener {
            removeStringFromSharedPreferences(this, "user_name")
            removeStringFromSharedPreferences(this, "user_last_name")
            removeStringFromSharedPreferences(this, "user_email")
            startActivity(Intent(this@MainActivity, LaunchActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
