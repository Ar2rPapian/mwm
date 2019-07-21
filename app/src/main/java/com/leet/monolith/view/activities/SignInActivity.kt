package com.leet.monolith.view.activities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.leet.monolith.R
import com.leet.monolith.data.LoginResponse
import com.leet.monolith.data.LoginStatus
import com.leet.monolith.data.User
import com.leet.monolith.util.animateProgressBar
import kotlinx.android.synthetic.main.activity_launch.sing_in_button
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

        sing_in_button.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                showProgressBar()
                val response = fetchUser(sign_in_email_et.text.toString().hashCode().toString(), sign_in_password_et.text.toString().hashCode().toString())
                hideProgressBar()
                when(response.status){
                    LoginStatus.SUCCESS -> {
                        errorMessageContainer.text = ""
                        Toast.makeText(applicationContext, "SUCCESS USER IS - ${response.user?.name}", Toast.LENGTH_LONG).show()
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
            login(username, password)
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


    fun login(username: String, password: String): LoginResponse {
        val db = FirebaseFirestore.getInstance()
        var user: User? = null
        var error: String? = null
        var status: LoginStatus? = null
        val task = db.collection("consumers").document(username).get()
        while (!task.isComplete) {}
        val document = task.result!!
        if(document.exists()){
            if (document.getString("password").equals(password)) {
                status = LoginStatus.SUCCESS
                user = User(
                    name = document.get("name").toString(),
                    lastName = document.get("lastName").toString(),
                    email = username,
                    password = document.get("password").toString()
                )
            } else {
                status = LoginStatus.FAILED
                error = "Incorrect Password"
            }
        }else{
            status = LoginStatus.FAILED
            error = "Incorrect Email"
        }

        return LoginResponse(status, error, user)
    }

}