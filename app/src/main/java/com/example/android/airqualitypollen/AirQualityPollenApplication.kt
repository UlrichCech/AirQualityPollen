package com.example.android.airqualitypollen

import android.app.Application
import com.example.android.airqualitypollen.platform.persistence.EntityManager

class AirQualityPollenApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        EntityManager.initialize(this@AirQualityPollenApplication)

    }
}