package com.mohamedragab.shouldwear.utils

import android.content.Context
import com.mohamedragab.shouldwear.R
import com.mohamedragab.shouldwear.model.OutFit

class OutFitClothes(context: Context) {

    private val sharedPrefrence = SharedPrefrence(context)
    private val lastOutFit = sharedPrefrence.getLastOutFitWear()
    private val lastDayOpenApp = sharedPrefrence.getLastDayOpenApp()
    private val listOfMyOutFitclothes = listOf(1, 2, 3, 4, 5, 6, 7)
    private var numberOfOutFit = 0

    fun chooseOutFit(temperature: Double, day: Int): OutFit {

        if (lastDayOpenApp == day) {
            numberOfOutFit = lastOutFit
        } else {
            if (lastDayOpenApp == 0) {
                numberOfOutFit = listOfMyOutFitclothes.random()
            } else {
                numberOfOutFit = listOfMyOutFitclothes.filter { it != lastOutFit }.random()
            }
            sharedPrefrence.setOutFitNumberWearToday(numberOfOutFit)
            sharedPrefrence.setDayOpenApp(day)
        }
        if (temperature < 15.0) {
            return getColdClothes(numberOfOutFit)

        } else {
            return getSunnyClothes(numberOfOutFit)
        }

    }

    private fun getSunnyClothes(numberOutFit: Int): OutFit {
        when (numberOutFit) {
            1 -> {
                return OutFit(
                    R.drawable.bantalon1,
                    R.drawable.hour1,
                    R.drawable.shouse1,
                    R.drawable.tesheart1
                )
            }
            2 -> {
                return OutFit(
                    R.drawable.bantalon2,
                    R.drawable.hour2,
                    R.drawable.shouse2,
                    R.drawable.tesheart2
                )
            }
            3 -> {
                return OutFit(
                    R.drawable.bantalon3,
                    R.drawable.hour3,
                    R.drawable.shouse3,
                    R.drawable.tesheart3
                )
            }
            4 -> {
                return OutFit(
                    R.drawable.bantalon4,
                    R.drawable.hour4,
                    R.drawable.shouse4,
                    R.drawable.tesheart4
                )
            }
            5 -> {
                return OutFit(
                    R.drawable.bantalon5,
                    R.drawable.hour5,
                    R.drawable.shouse5,
                    R.drawable.tesheart5
                )
            }
            6 -> {
                return OutFit(
                    R.drawable.bantalon6,
                    R.drawable.hour6,
                    R.drawable.shouse6,
                    R.drawable.tesheart6
                )
            }
            else -> {
                return OutFit(
                    R.drawable.bantalon7,
                    R.drawable.hour7,
                    R.drawable.shouse7,
                    R.drawable.tesheart7
                )
            }
        }
    }

    private fun getColdClothes(numberOutFit: Int): OutFit {
        when (numberOutFit) {
            1 -> {
                return OutFit(
                    R.drawable.bantalon1,
                    R.drawable.hour1,
                    R.drawable.shouse1,
                    R.drawable.jacket1
                )
            }
            2 -> {
                return OutFit(
                    R.drawable.bantalon2,
                    R.drawable.hour2,
                    R.drawable.shouse2,
                    R.drawable.jacket2
                )
            }
            3 -> {
                return OutFit(
                    R.drawable.bantalon3,
                    R.drawable.hour3,
                    R.drawable.shouse3,
                    R.drawable.jacket3
                )
            }
            4 -> {
                return OutFit(
                    R.drawable.bantalon4,
                    R.drawable.hour4,
                    R.drawable.shouse4,
                    R.drawable.jacket4
                )
            }
            5 -> {
                return OutFit(
                    R.drawable.bantalon5,
                    R.drawable.hour5,
                    R.drawable.shouse5,
                    R.drawable.jacket5
                )
            }
            6 -> {
                return OutFit(
                    R.drawable.bantalon6,
                    R.drawable.hour6,
                    R.drawable.shouse6,
                    R.drawable.jacket6
                )
            }
            else -> {
                return OutFit(
                    R.drawable.bantalon7,
                    R.drawable.hour7,
                    R.drawable.shouse7,
                    R.drawable.jacket7
                )
            }
        }
    }


}