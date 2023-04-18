package com.mohamedragab.shouldwear.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val location:MyLocation,
    @SerializedName("current") val currentTemperature:CurrentTemperature,
)
