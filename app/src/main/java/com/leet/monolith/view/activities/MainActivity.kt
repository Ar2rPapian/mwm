package com.leet.monolith.view.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.leet.monolith.R
import com.leet.monolith.databinding.ActivityMainBinding
import com.leet.monolith.viewmodel.MainViewModel


class MainActivity : BaseActivity() {



    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        val mainActivity :ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val mainViewModel:MainViewModel = ViewModelProviders.of( this).get(MainViewModel::class.java)

        mainActivity.mainViewModel = mainViewModel
        mainActivity.lifecycleOwner = this
    }

    override fun onBackPressed() {
        super.finishAffinity()
    }
}
