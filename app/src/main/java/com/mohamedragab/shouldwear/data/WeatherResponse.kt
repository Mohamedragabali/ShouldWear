package com.mohamedragab.shouldwear.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val location:Location ,
    @SerializedName("current") val currentTemperature:CurrentTemperature,
)
