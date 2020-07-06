package com.lordsam.currentweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class ReportActivity : AppCompatActivity() {

    private lateinit var txtHumidity: TextView
    private lateinit var txtCity: TextView
    private lateinit var apiKey: String
    private lateinit var urlCity: String
    private lateinit var urlLoc: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        txtCity = findViewById(R.id.textViewReportCity)
        txtHumidity = findViewById(R.id.textViewReportHumidity)

        val bundle = intent.extras!!
        val city = bundle.getString("city")
        val long = bundle.getInt("long")
        val lat = bundle.getInt("lat")

        apiKey = "2107de0cd623911183917128461a49d1"

        if (city != null) {
            urlCity = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey"
            getReport(urlCity)
        } else {
            urlLoc =
                "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$long&appid=$apiKey"
            getReport(urlLoc)
        }
    }

    private fun getReport(url: String) {
        val queue = Volley.newRequestQueue(this)
        val jsonObj = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.i("response", response.toString())
                val main = response.getJSONObject("main")
                val city = response.getString("name")
                val hum = main.getInt("humidity").toString()
                txtCity.text = "City :$city"
                txtHumidity.text = "Humidity :$hum"
            },
            Response.ErrorListener {
                txtHumidity.text = "error"
            })
        queue.add(jsonObj)
    }
}