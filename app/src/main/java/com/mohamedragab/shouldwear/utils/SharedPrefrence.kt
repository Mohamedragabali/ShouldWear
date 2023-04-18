package com.mohamedragab.shouldwear.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefrence(context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(APPLICATION, Context.MODE_PRIVATE)

    fun setOutFitNumberWearToday(value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(OUT_FIT_TODAY, value);
        editor.apply()

    }

    fun getLastOutFitWear() = sharedPreferences.getInt(OUT_FIT_TODAY, 0)


    fun setDayOpenApp(value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(DAY_OPEN_APP, value);
        editor.apply()

    }

    fun getLastDayOpenApp() = sharedPreferences.getInt(DAY_OPEN_APP, 0)


    companion object {
        private const val APPLICATION = "APPLICATION"
        private const val OUT_FIT_TODAY = "OUT_FIT_TODAY"
        private const val DAY_OPEN_APP = "DAY_OPEN_APP"
    }
}