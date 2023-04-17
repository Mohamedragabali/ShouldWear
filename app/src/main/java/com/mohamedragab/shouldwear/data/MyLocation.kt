package com.mohamedragab.shouldwear.data

import com.google.gson.annotations.SerializedName

data class MyLocation(
    @SerializedName("region") val cityName:String,
    val country:String,
    val localtime:String
)
