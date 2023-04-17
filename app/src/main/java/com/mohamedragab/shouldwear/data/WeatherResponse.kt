package com.mohamedragab.shouldwear.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val location:MyLocation,
    @SerializedName("current") val currentTemperature:CurrentTemperature,
)
