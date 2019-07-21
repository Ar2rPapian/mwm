package com.leet.monolith.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.leet.monolith.R
import com.leet.monolith.data.SignUpResponse
import com.leet.monolith.data.SignUpStatus
import com.leet.monolith.data.User
import com.leet.monolith.network.auth.Auth
import com.leet.monolith.util.addString2SharedPreferences
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
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
                    val response = createNewUser(user)
                    when(response.status){
                        SignUpStatus.SUCCESS -> {
                            response.user.toString().debug("GVIDON")
                            Toast.makeText(applicationContext, "SUCCESS USER IS - ${response.user!!.toString()}", Toast.LENGTH_LONG).show()
                            addString2SharedPreferences(applicationContext,"user_name", response.user.name )
                            addString2SharedPreferences(applicationContext,"user_last_name", response.user.lastName )
                            addString2SharedPreferences(applicationContext,"user_email", response.user.email)
                            startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                            finish()
                        }
                        SignUpStatus.FAILED -> {
                            Toast.makeText(applicationContext, "ERROR - ${response.error}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
//
            } else {
                Toast.makeText(applicationContext, "Please check your data", Toast.LENGTH_SHORT).show()
            }

        }
    }


    private suspend fun createNewUser(user:User): SignUpResponse {
        return GlobalScope.async(Dispatchers.IO) {
            Auth.createUser(user)
        }.await()
    }
}