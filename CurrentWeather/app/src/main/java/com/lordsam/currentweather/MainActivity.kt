package com.lordsam.currentweather

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var edtCity: EditText
    private lateinit var btnGetWeather: Button
    private lateinit var btnCurrent: Button
    private lateinit var city: String
    private lateinit var fuse: FusedLocationProviderClient
    private val pCode = 6713

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtCity = findViewById(R.id.editTextMainCity)
        btnGetWeather = findViewById(R.id.buttonMainGetWeather)
        btnCurrent = findViewById(R.id.buttonMainGetWeatherAtCurrentLocation)

        btnCurrent.setOnClickListener(this)
        btnGetWeather.setOnClickListener(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty() && requestCode == pCode){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,
                "Permission Granted",
                Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,
                    "Permission Denied",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(p0: View?) {

        when (p0!!.id) {
            R.id.buttonMainGetWeather -> {
                city = edtCity.text.toString()
                val intentToReport = Intent(this, ReportActivity::class.java)
                intentToReport.putExtra("city", city)
                startActivity(intentToReport)
            }
            R.id.buttonMainGetWeatherAtCurrentLocation -> {

                val arrayOfPerm = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
                fuse = LocationServices.getFusedLocationProviderClient(this)

                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this, arrayOfPerm, pCode)
                } else {
                    fuse.lastLocation.addOnSuccessListener { location ->

                        if (location != null) {
                            val long = location.longitude.roundToInt()
                            val lat = location.latitude.roundToInt()
                            val intentToReport = Intent(this, ReportActivity::class.java)
                            intentToReport.putExtra("long", long)
                            intentToReport.putExtra("lat", lat)
                            startActivity(intentToReport)
                        }
                    }
                }
            }
        }
    }
}