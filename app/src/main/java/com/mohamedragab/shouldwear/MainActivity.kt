package com.mohamedragab.shouldwear

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.mohamedragab.shouldwear.data.WeatherResponse
import com.mohamedragab.shouldwear.databinding.ActivityMainBinding
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val logInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeRequestOkHttp()
    }

    private fun makeRequestOkHttp() {
        val url = HttpUrl.Builder().scheme("http")
            .scheme("http")
            .host("api.weatherstack.com")
            .addPathSegment("current")
            .addQueryParameter("access_key", "e981dc11b9c12972fc6afca11f267171")
            .addQueryParameter("query", "cairo")
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string().let { jsonString ->
                    val result = Gson().fromJson(jsonString, WeatherResponse::class.java)
                    runOnUiThread {
                        binding.myText.text = result.currentTemperature.toString()
                    }
                }
            }

        })
    }

}