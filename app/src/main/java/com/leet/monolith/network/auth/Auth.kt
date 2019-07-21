package com.leet.monolith.network.auth

import com.google.firebase.firestore.FirebaseFirestore
import com.leet.monolith.data.*

object Auth {

    private val CONSUMERS_COLLECTION = "consumers"

    fun login(username: String, password: String): LoginResponse {
        val db = FirebaseFirestore.getInstance()
        var user: User? = null
        var error: String? = null
        var status: LoginStatus? = null
        val task = db.collection(CONSUMERS_COLLECTION).document(username.hashCode().toString()).get()
        while (!task.isComplete) {}
        val document = task.result!!
        if(document.exists()){
            if (document.getString("password") == password) {
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

    fun createUser(user: User): SignUpResponse {
        val db = FirebaseFirestore.getInstance()
        var error: String? = null
        var status: SignUpStatus? = null
        val userData = hashMapOf(
            "name" to user.name,
            "lastName" to user.lastName,
            "password" to user.password,
            "email" to user.email
        )
        val userId = user.email.hashCode().toString()
        val task = db.collection(CONSUMERS_COLLECTION).document(userId).get()
        while (!task.isComplete) {}
        val document = task.result!!
        if(document.exists()){
            status = SignUpStatus.FAILED
            error = "User already registered"
        }else{
            val registerTask = db.collection(CONSUMERS_COLLECTION).document(userId).set(userData)
            while (!registerTask.isComplete) {}
            if(registerTask.isSuccessful){
                status = SignUpStatus.SUCCESS
            } else{
                status = SignUpStatus.FAILED; error = registerTask.exception?.message
            }
        }
        return SignUpResponse(status, error, user)
    }

}