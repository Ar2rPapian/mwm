package com.leet.monolith.view.activities

import android.content.Intent
import android.os.Bundle
import com.leet.monolith.R
import com.leet.monolith.data.Result
import com.leet.monolith.data.User
import com.leet.monolith.network.auth.Authorisation
import com.leet.monolith.util.addString2SharedPreferences
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpActivity: BaseActivity() {
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_sign_up)
        signInLink.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
        }

        signUpButton.setOnClickListener {
            val name = signUpNameEt.text.toString()
            val lastName = signUpLastNameEt.text.toString()
            val email = signUpEmailEt.text.toString()
            val password = signUpPasswordEt.text.toString()

            if(name.length > 3 && lastName.length > 3 && email.contains("@") && email.length > 6 && password.length > 6) {
                val user = User(name = name, lastName = lastName, email = email, password = password)
                GlobalScope.launch(Dispatchers.Main) {
                    when(val result =  Authorisation.createUser(user)){
                        is Result.SUCCESS -> {
                            application showToast "SUCCESS USER IS - ${result.user}"
                            addString2SharedPreferences(applicationContext,"user_name", result.user.name )
                            addString2SharedPreferences(applicationContext,"user_last_name", result.user.lastName )
                            addString2SharedPreferences(applicationContext,"user_email", result.user.email)
                            startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                            finish()
                        }
                        is Result.FAILED -> {
                            application showToast "ERROR - ${result.error}"
                        }
                    }
                }
            } else {
                application showToast "Please check your data"
            }

        }
    }

}