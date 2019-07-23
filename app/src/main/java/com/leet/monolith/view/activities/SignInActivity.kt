package com.leet.monolith.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.leet.monolith.R
import com.leet.monolith.data.Result
import com.leet.monolith.network.auth.Authorisation.login
import com.leet.monolith.util.addString2SharedPreferences
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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
                val result = login(signInEmailEt.text.toString(), signInPasswordEt.text.toString())
                hideProgressBar()
                when (result) {
                    is Result.SUCCESS -> {
                        errorMessageContainer.text = ""
                        addString2SharedPreferences(applicationContext, "user_name", result.user.name)
                        addString2SharedPreferences(applicationContext, "user_last_name", result.user.lastName)
                        addString2SharedPreferences(applicationContext, "user_email", result.user.email)
                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                    }
                    is Result.FAILED -> {
                        errorMessageContainer.text = result.error
                    }
                }
            }
        }
    }

    private fun showProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar);
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar);
        progressBar.visibility = View.GONE
    }
}