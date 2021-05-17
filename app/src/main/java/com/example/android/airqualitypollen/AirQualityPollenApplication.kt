package com.example.android.airqualitypollen

import android.app.Application
import android.util.Log
import com.example.android.airqualitypollen.platform.persistence.EntityManager
import java.text.SimpleDateFormat
import java.util.*

class AirQualityPollenApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val date: Date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-10-07 08:00:00")

        Log.i("UCE", "$date")

        EntityManager.initialize(this@AirQualityPollenApplication)

    }
}