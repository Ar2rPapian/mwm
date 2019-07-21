package com.leet.monolith.data


enum class SignUpStatus(){
    SUCCESS,
    FAILED
}

class SignUpResponse(val status:SignUpStatus?,val error:String?, val user:User?)