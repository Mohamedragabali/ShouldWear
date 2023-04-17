package com.mohamedragab.shouldwear

import android.content.SharedPreferences

class SavedInSharedPrefrence ( private val sharedPreferences : SharedPreferences){

    fun setOutFitToday(value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(OUT_FIT_TODAY, value);
        editor.apply()

    }

   fun getLastOutFit(): Int {
        return sharedPreferences.getInt(OUT_FIT_TODAY, 0)
    }

    fun setDayOpenApp(value : Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(DAY_OPEN_APP, value);
        editor.apply()

    }

    fun getLastDayOpenApp(): Int{
        return sharedPreferences.getInt(DAY_OPEN_APP, 0 )
    }

    companion object {
        private const val OUT_FIT_TODAY = "OUT_FIT_TODAY"
        private const val DAY_OPEN_APP ="DAY_OPEN_APP"
    }
}