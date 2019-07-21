package com.leet.monolith.data

enum class LoginStatus(){
    SUCCESS,
    FAILED
}



data class LoginResponse (val status:LoginStatus?,val error:String?, val user:User? )