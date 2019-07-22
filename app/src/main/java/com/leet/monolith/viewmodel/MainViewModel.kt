package com.leet.monolith.viewmodel

import android.util.Log
import android.widget.EditText
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel() : ViewModel(){

    @Bindable
    var editText: MutableLiveData<String> = MutableLiveData()

    @Bindable
    var switched: MutableLiveData<Boolean> = MutableLiveData()

    var userName: MutableLiveData<String> = MutableLiveData()
    var userLastName: MutableLiveData<String> = MutableLiveData()

    fun setRandomName(){
        this.userName.value = Random.nextInt().toString()
    }
    fun setRandomLastName(){
        this.userLastName.value = Random.nextInt().toString()
    }
    fun switchFullName(){
        setRandomName()
        setRandomLastName()
    }
    fun switchW(){
        this.switched.value = !this.switched.value!!
    }

    init {
        switched.value = false
    }

    fun listener(){
        userName.value = editText.value
        userLastName.value = editText.value
    }


}