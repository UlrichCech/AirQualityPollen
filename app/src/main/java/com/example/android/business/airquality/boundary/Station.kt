package com.example.android.business.airquality.boundary

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Station(@Json(name = "NO2") val no2: Double?,
                   @Json(name = "PM10") val pm10: Double?,
                   @Json(name = "PM25") val pm25: Double?,
                   @Json(name = "CO") val co: Double?,
                   @Json(name = "SO2") val so2: Double?,
                   @Json(name = "OZONE") val ozone: Double?,
                   val city: String?,
                   val countryCode: String?,
                   val division: String?,
                   val lat: Double?,
                   val lng: Double?,
                   val placeName: String?,
                   val postalCode: String?,
                   val state: String?,
                   @Json(name = "AQI") val aqi: Double?,
                   val aqiInfo: AqiInfo?,
                   val updatedAt: String?) : Parcelable {}
