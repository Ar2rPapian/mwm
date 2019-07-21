package com.leet.monolith.util

import android.view.View
import android.widget.ProgressBar

fun animateProgressBar(progressBar: ProgressBar){
    val anim = ProgressBarAnimation(progressBar, 0F, 100.0F)
    progressBar.startAnimation(anim)
}