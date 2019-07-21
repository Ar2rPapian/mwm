package com.leet.monolith.viewmodel

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel() : ViewModel(){

    var userName: MutableLiveData<String> = MutableLiveData()
    var userLastName: MutableLiveData<String> = MutableLiveData()

    @Bindable
    var editText: MutableLiveData<String> = MutableLiveData()

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

}