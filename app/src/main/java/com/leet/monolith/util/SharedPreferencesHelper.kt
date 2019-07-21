package com.leet.monolith.util

import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE



fun addString2SharedPreferences(context: Context,key:String,  value: String){
    val editor : SharedPreferences.Editor = context.getSharedPreferences("monolith" , Context.MODE_PRIVATE).edit()
    editor.putString(key, value).apply()
}

fun getStringFromSharedPreferences(context: Context, key:String):String?{
    val prefs = context.getSharedPreferences("monolith", MODE_PRIVATE)
    return prefs.getString(key, "")
}

fun removeStringFromSharedPreferences(context: Context, key:String){
    val editor : SharedPreferences.Editor = context.getSharedPreferences("monolith" , Context.MODE_PRIVATE).edit()
    editor.remove(key).apply()
}