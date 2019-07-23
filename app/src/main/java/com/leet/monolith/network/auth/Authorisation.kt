package com.leet.monolith.network.auth

import com.google.firebase.firestore.FirebaseFirestore
import com.leet.monolith.data.Result
import com.leet.monolith.data.User
import com.leet.monolith.extensions.DatabaseExtensions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

object Authorisation : DatabaseExtensions {


    private const val CONSUMERS_COLLECTION = "consumers"

    suspend fun login(username:String, password:String): Result {
        return with(GlobalScope) {
            async{
                val db = FirebaseFirestore.getInstance()
                val document = db
                    .collection(CONSUMERS_COLLECTION)
                    .document(username.hashCode().toString())
                    .get()
                    .await()
                    .result!!
                if(document.exists()){
                    if (document.getString("password") == password) {
                        val user = User(
                            name = document.get("name").toString(),
                            lastName = document.get("lastName").toString(),
                            email = username,
                            password = password
                        )
                        return@async Result.SUCCESS(user)
                    } else {
                        return@async Result.FAILED("Incorrect Password")
                    }
                }
                return@async Result.FAILED("Incorrect Email")
            }.await()
        }
    }


    suspend fun createUser(user: User): Result {
        return with(GlobalScope) {
            async {
                val db = FirebaseFirestore.getInstance()
                val userData = hashMapOf(
                    "name" to user.name,
                    "lastName" to user.lastName,
                    "password" to user.password,
                    "email" to user.email
                )
                val userId = user.email.hashCode().toString()
                val document = db
                    .collection(CONSUMERS_COLLECTION)
                    .document(userId)
                    .get()
                    .await()
                    .result

                if(!document?.exists()!!) {
                    val registerTask = db
                        .collection(CONSUMERS_COLLECTION)
                        .document(userId)
                        .set(userData)
                        .await()
                    if (registerTask.isSuccessful) {
                        return@async Result.SUCCESS(user)
                    } else {
                        registerTask.exception?.message?.let {
                            return@async  Result.FAILED(it)
                        }
                    }
                }
                return@async Result.FAILED("User already registered")
            }.await()
        }
    }
}

