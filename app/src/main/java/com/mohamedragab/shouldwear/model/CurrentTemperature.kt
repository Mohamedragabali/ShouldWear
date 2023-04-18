package com.mohamedragab.shouldwear.model

import com.google.gson.annotations.SerializedName

data class CurrentTemperature(
    @SerializedName("temp_c") val temperature: String,
    @SerializedName("condition") val weather_descriptions: WeatherDescriptions
)
