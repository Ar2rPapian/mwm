package com.leet.monolith.extensions

import com.google.android.gms.tasks.Task

interface DatabaseExtensions {

    fun <T> Task<T>.await(): Task<T> {
        while (!isComplete){}
        return this
    }
}