package com.mohamedragab.shouldwear.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object TimeToday {
    private val DataAndTime = Calendar.getInstance().time

    @SuppressLint("SimpleDateFormat")
    private val formatData = SimpleDateFormat("EEE, d MMM")

    @SuppressLint("SimpleDateFormat")
    private val formatTime = SimpleDateFormat("d")


    val todayDate: String = formatData.format(DataAndTime)
    val day: Int = formatTime.format(DataAndTime).toInt()
}