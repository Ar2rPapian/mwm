package com.leet.monolith.data


sealed class Result(){
    class SUCCESS(val user:User) : Result()
    class FAILED(val error: String) : Result()
}