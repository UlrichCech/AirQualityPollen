package com.example.android.business.airquality.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity @Parcelize
data class AirQuality(val no2: Double?,
                      val pm10: Double?,
                      val pm25: Double?,
                      val co: Double?,
                      val so2: Double?,
                      val ozone: Double?,
                      val city: String?,
                      val countryCode: String?,
                      val division: String?,
                      val lat: Double?,
                      val lng: Double?,
                      val placeName: String?,
                      val postalCode: String?,
                      val state: String?,
                      val aqi: Double?,
                      val pollutant: String?,
                      val concentration: Double?,
                      val category: String?,
                      val updatedAt: String?) : Parcelable
