package com.mohamedragab.shouldwear


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.mohamedragab.shouldwear.data.WeatherResponse
import com.mohamedragab.shouldwear.databinding.ActivityMainBinding
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val logInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocation()
    }

    private fun makeRequestOkHttp(latitudeAndLongitude: String) {
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("api.weatherapi.com")
            .addPathSegment("v1")
            .addPathSegment("current.json")
            .addQueryParameter("key", "226051f0f0564da7bbd212417231604")
            .addQueryParameter("q", latitudeAndLongitude)
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.v("ActivityMain", "$e.message")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string().let { jsonString ->
                    val result = Gson().fromJson(jsonString, WeatherResponse::class.java)
                    Log.v("ActivityMain", "$result")
                    runOnUiThread {
                        setUiView(result)
                    }
                }
            }

        })
    }

    private fun getCurrentLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this, android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) !=
                    PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        Toast.makeText(this, "Null Recived", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Successful Git", Toast.LENGTH_SHORT).show()
                        makeRequestOkHttp("${location.latitude} , ${location.longitude}")
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location ", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_PEQEST_ACCESS_LOCATION
        )
    }

    companion object {
        private const val PERMISSION_PEQEST_ACCESS_LOCATION = 100
    }

    private fun checkPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT).show()
            getCurrentLocation()
        } else {
            Toast.makeText(applicationContext, "Denied", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setUiView(weather: WeatherResponse) {
        val DataAndTime = Calendar.getInstance().getTime()
        val formatData= SimpleDateFormat("EEE, d MMM")
        val formatTime= SimpleDateFormat("d")
        val date = formatData.format(DataAndTime)
        val day = formatTime.format(DataAndTime)
        binding.time.text = date
        binding.cityName.text = weather.location.cityName
        binding.tempreaturDescription.text = weather.currentTemperature.weather_descriptions.text
        binding.tempreatureDegree.text = weather.currentTemperature.temperature
        chooseOutFit(weather.currentTemperature.temperature.toDouble()  ,day.toInt())
    }

    fun chooseOutFit(temperature: Double , day : Int ) {
        var  numberOfOutFit  = 0
        val lastOutFit = getLastOutFit()
        val lastDayOpenApp = getLastDayOpenApp()
        val listOfMyOutFitclothes = listOf(1, 2, 3, 4, 5, 6, 7)
        if(lastDayOpenApp == day ){
            numberOfOutFit = lastOutFit
        }
        else{
            if(lastDayOpenApp == 0 ){
                numberOfOutFit = listOfMyOutFitclothes.random()
            }
            else{
                numberOfOutFit = listOfMyOutFitclothes.filter { it != lastOutFit }.random()
            }
            setOutFitToday(numberOfOutFit)
            setDayOpenApp(day)
        }
        if (temperature < 15.0) {
            chooseColdClothes(numberOfOutFit)

        } else {
            chooseSunnyClothes(numberOfOutFit)
        }

    }


    private fun changeClothesInUi(bantalon: Int, hour: Int, shoes: Int, teshart: Int) {
        binding.pantalon.setImageResource(bantalon)
        binding.hour.setImageResource(hour)
        binding.shouse.setImageResource(shoes)
        binding.teshert.setImageResource(teshart)

    }

    private fun setOutFitToday(value: Int) {
        val sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        val editor = sharedPref.edit()
        editor.putInt("LAST_BUTTON", value);
        editor.apply()

    }

    private fun getLastOutFit(): Int {
        val sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        return sharedPref.getInt("LAST_BUTTON", 0)
    }

    private fun setDayOpenApp(value : Int) {
        val sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        val editor = sharedPref.edit()
        editor.putInt("TheNamberDay", value);
        editor.apply()

    }

    private fun getLastDayOpenApp(): Int{
        val sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        return sharedPref.getInt("TheNamberDay", 0 )
    }

    private fun chooseSunnyClothes(numberOutFit : Int ){
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
    private fun chooseColdClothes(numberOutFit: Int){
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
}
