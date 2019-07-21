package com.leet.monolith.view.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {

    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        supportActionBar?.hide()
        actionBar?.hide()
    }

    fun String.debug(tag: String){
        Log.d(tag, this)
    }
    fun String.error(tag: String){
        Log.e(tag, this)
    }
    fun String.info(tag: String){
        Log.i(tag, this)
    }
}
