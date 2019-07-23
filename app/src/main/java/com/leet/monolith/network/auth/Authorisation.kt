package com.leet.monolith.network.auth

import com.google.firebase.firestore.FirebaseFirestore
import com.leet.monolith.data.Result
import com.leet.monolith.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object Authorisation {

    private const val CONSUMERS_COLLECTION = "consumers"

    suspend fun login(username:String, password:String): Result {
        return withContext(Dispatchers.IO) {
            val db = FirebaseFirestore.getInstance()
            val task = db.collection(CONSUMERS_COLLECTION).document(username.hashCode().toString()).get()
            while (!task.isComplete) {}
            val document = task.result!!
            if(document.exists()){
                if (document.getString("password") == password) {
                    val user = User(
                        name = document.get("name").toString(),
                        lastName = document.get("lastName").toString(),
                        email = username,
                        password = password
                    )
                    Result.SUCCESS(user)
                } else {
                    Result.FAILED("Incorrect Password")
                }
            }
            Result.FAILED("Incorrect Email")
        }
    }


    suspend fun createUser(user: User): Result {
        return withContext(Dispatchers.IO) {
            val db = FirebaseFirestore.getInstance()
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
            if(!document.exists()) {
                val registerTask = db.collection(CONSUMERS_COLLECTION).document(userId).set(userData)
                while (!registerTask.isComplete) {}
                if (registerTask.isSuccessful) {
                    Result.SUCCESS(user)
                } else {
                    registerTask.exception?.message?.let {
                        Result.FAILED(it)
                    }
                }
            }
            Result.FAILED("User already registered")
        }
    }

}