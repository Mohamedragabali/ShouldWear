package com.mohamedragab.shouldwear.ui


import android.annotation.SuppressLint
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
import com.mohamedragab.shouldwear.utils.OutFitClothes
import com.mohamedragab.shouldwear.model.WeatherResponse
import com.mohamedragab.shouldwear.databinding.ActivityMainBinding
import com.mohamedragab.shouldwear.utils.TimeToday
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val logInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var outFitClothes: OutFitClothes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        outFitClothes = OutFitClothes(applicationContext)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(applicationContext)
        getCurrentLocation()

    }

    @SuppressLint("SetTextI18n")
    private fun setupUi(weather: WeatherResponse) {
        binding.cityName.text = weather.location.cityName
        binding.tempreaturDescription.text = weather.currentTemperature.weather_descriptions.text
        binding.tempreatureDegree.text = "${weather.currentTemperature.temperature}c"
        binding.time.text = TimeToday.todayDate

        val outFitComponent = outFitClothes.chooseOutFit(
            weather.currentTemperature.temperature.toDouble(), TimeToday.day
        )
        binding.teshert.setImageResource(outFitComponent.tesheart)
        binding.pantalon.setImageResource(outFitComponent.bantalon)
        binding.shouse.setImageResource(outFitComponent.shouse)
        binding.hour.setImageResource(outFitComponent.hour)
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
                    runOnUiThread {
                        setupUi(result)
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

    companion object {
        private const val PERMISSION_PEQEST_ACCESS_LOCATION = 100
    }


}
