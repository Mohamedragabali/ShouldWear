package com.mohamedragab.shouldwear

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.mohamedragab.shouldwear.data.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*

class GetUiComponent(sharedPreferences: SharedPreferences) {
    private val savedInSharedPrefrence = SavedInSharedPrefrence(sharedPreferences)
    private lateinit var cityName: String
    private lateinit var tempreaturDescription: String
    private lateinit var tempreatureDegree: String
    private lateinit var time: String
    private var teshart: Int = 0
    private var bantalon: Int = 0
    private var hour: Int = 0
    private var shoes: Int = 0
    private lateinit var day: String
    private val DataAndTime = Calendar.getInstance().getTime()

    @SuppressLint("SimpleDateFormat")
    val formatData = SimpleDateFormat("EEE, d MMM")

    @SuppressLint("SimpleDateFormat")
    private val formatTime = SimpleDateFormat("d")
    private val lastOutFit = savedInSharedPrefrence.getLastOutFit()
    private val lastDayOpenApp = savedInSharedPrefrence.getLastDayOpenApp()
    private val listOfMyOutFitclothes = listOf(1, 2, 3, 4, 5, 6, 7)
    private var numberOfOutFit = 0

    fun setUiView(weather: WeatherResponse) {
        cityName = weather.location.cityName
        tempreaturDescription = weather.currentTemperature.weather_descriptions.text
        tempreatureDegree = weather.currentTemperature.temperature
        time = formatData.format(DataAndTime)
        day = formatTime.format(DataAndTime)

        chooseOutFitNumber(weather.currentTemperature.temperature.toDouble(), day.toInt())
    }

    private fun chooseOutFitNumber(temperature: Double, day: Int) {

        if (lastDayOpenApp == day) {
            numberOfOutFit = lastOutFit
        } else {
            if (lastDayOpenApp == 0) {
                numberOfOutFit = listOfMyOutFitclothes.random()
            } else {
                numberOfOutFit = listOfMyOutFitclothes.filter { it != lastOutFit }.random()
            }
            savedInSharedPrefrence.setOutFitToday(numberOfOutFit)
            savedInSharedPrefrence.setDayOpenApp(day)
        }
        if (temperature < 15.0) {
            ColdClothes(numberOfOutFit)

        } else {
            SunnyClothes(numberOfOutFit)
        }

    }

    private fun SunnyClothes(numberOutFit: Int) {
        when (numberOutFit) {
            1 -> {
                changeClothesInUi(
                    R.drawable.bantalon1,
                    R.drawable.hour1,
                    R.drawable.shouse1,
                    R.drawable.tesheart1
                )
            }
            2 -> {
                changeClothesInUi(
                    R.drawable.bantalon2,
                    R.drawable.hour2,
                    R.drawable.shouse2,
                    R.drawable.tesheart2
                )
            }
            3 -> {
                changeClothesInUi(
                    R.drawable.bantalon3,
                    R.drawable.hour3,
                    R.drawable.shouse3,
                    R.drawable.tesheart3
                )
            }
            4 -> {
                changeClothesInUi(
                    R.drawable.bantalon4,
                    R.drawable.hour4,
                    R.drawable.shouse4,
                    R.drawable.tesheart4
                )
            }
            5 -> {
                changeClothesInUi(
                    R.drawable.bantalon5,
                    R.drawable.hour5,
                    R.drawable.shouse5,
                    R.drawable.tesheart5
                )
            }
            6 -> {
                changeClothesInUi(
                    R.drawable.bantalon6,
                    R.drawable.hour6,
                    R.drawable.shouse6,
                    R.drawable.tesheart6
                )
            }
            else -> {
                changeClothesInUi(
                    R.drawable.bantalon7,
                    R.drawable.hour7,
                    R.drawable.shouse7,
                    R.drawable.tesheart7
                )
            }
        }
    }

    private fun ColdClothes(numberOutFit: Int) {
        when (numberOutFit) {
            1 -> {
                changeClothesInUi(
                    R.drawable.bantalon1,
                    R.drawable.hour1,
                    R.drawable.shouse1,
                    R.drawable.jacket1
                )
            }
            2 -> {
                changeClothesInUi(
                    R.drawable.bantalon2,
                    R.drawable.hour2,
                    R.drawable.shouse2,
                    R.drawable.jacket2
                )
            }
            3 -> {
                changeClothesInUi(
                    R.drawable.bantalon3,
                    R.drawable.hour3,
                    R.drawable.shouse3,
                    R.drawable.jacket3
                )
            }
            4 -> {
                changeClothesInUi(
                    R.drawable.bantalon4,
                    R.drawable.hour4,
                    R.drawable.shouse4,
                    R.drawable.jacket4
                )
            }
            5 -> {
                changeClothesInUi(
                    R.drawable.bantalon5,
                    R.drawable.hour5,
                    R.drawable.shouse5,
                    R.drawable.jacket5
                )
            }
            6 -> {
                changeClothesInUi(
                    R.drawable.bantalon6,
                    R.drawable.hour6,
                    R.drawable.shouse6,
                    R.drawable.jacket6
                )
            }
            else -> {
                changeClothesInUi(
                    R.drawable.bantalon7,
                    R.drawable.hour7,
                    R.drawable.shouse7,
                    R.drawable.jacket7
                )
            }
        }
    }

    private fun changeClothesInUi(bantalon: Int, hour: Int, shoes: Int, teshart: Int) {
       this.bantalon =bantalon
        this.hour = hour
        this.teshart = teshart
        this.shoes = shoes
    }


    fun getCityName() =cityName
    fun getTempreaturDescription() =tempreaturDescription
    fun gettempreatureDegree() =tempreatureDegree
    fun getTime() = time
    fun getTeshart() =teshart
    fun getBantalon() =bantalon
    fun gethour() =hour
    fun getShoes() =shoes

}