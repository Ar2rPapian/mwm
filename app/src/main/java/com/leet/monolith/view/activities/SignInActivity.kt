package com.leet.monolith.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.leet.monolith.R
import com.leet.monolith.data.LoginResponse
import com.leet.monolith.data.LoginStatus
import com.leet.monolith.network.auth.Auth
import com.leet.monolith.util.addString2SharedPreferences
import com.leet.monolith.util.animateProgressBar
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class SignInActivity : BaseActivity() {

    private val TAG = "GVIDON"


    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_sign_in)
        signUpLink.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }
        signInButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                showProgressBar()
                val response = fetchUser(signInEmailEt.text.toString(), signInPasswordEt.text.toString())
                hideProgressBar()
                when(response.status){
                    LoginStatus.SUCCESS -> {
                        errorMessageContainer.text = ""
                        Toast.makeText(applicationContext, "SUCCESS USER IS - ${response.user!!.name}", Toast.LENGTH_LONG).show()
                        addString2SharedPreferences(applicationContext,"user_name", response.user.name )
                        addString2SharedPreferences(applicationContext,"user_last_name", response.user.lastName )
                        addString2SharedPreferences(applicationContext,"user_email", response.user.email)
                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                    }
                    LoginStatus.FAILED -> {
                        errorMessageContainer.text = response.error
                    }
                }
            }
        }
    }


    suspend fun fetchUser(username:String, password:String): LoginResponse {
        return GlobalScope.async(Dispatchers.IO) {
            Auth.login(username, password)
        }.await()
    }

    private fun showProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        animateProgressBar(progressBar)
    }

    private fun hideProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.GONE
        progressBar.animation = null
    }



}