package com.mohamedragab.shouldwear.model

import com.google.gson.annotations.SerializedName

data class MyLocation(
    @SerializedName("region") val cityName:String,
    val country:String,
    val localtime:String
)
