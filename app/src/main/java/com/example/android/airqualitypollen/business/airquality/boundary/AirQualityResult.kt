package com.example.android.airqualitypollen.business.airquality.boundary

import android.os.Parcelable
import com.example.android.airqualitypollen.business.airquality.entity.AirQuality
import kotlinx.parcelize.Parcelize

/**
 * The main data class for JSON-result of the AMBEE-Endpoint for getting the air-quality.
 */
@Parcelize
data class AirQualityResult(val message: String,
                            val stations: List<Station>) : Parcelable {

    fun toEntity() : AirQuality {
        return AirQuality(this.stations[0].no2,
                          this.stations[0].pm10,
                          this.stations[0].pm25,
                          this.stations[0].co,
                          this.stations[0].so2,
                          this.stations[0].ozone,
                          this.stations[0].city,
                          this.stations[0].countryCode,
                          this.stations[0].division,
                          this.stations[0].lat,
                          this.stations[0].lng,
                          this.stations[0].placeName,
                          this.stations[0].postalCode,
                          this.stations[0].state,
                          this.stations[0].aqi,
                          this.stations[0].aqiInfo?.pollutant,
                          this.stations[0].aqiInfo?.concentration,
                          this.stations[0].aqiInfo?.category,
                          this.stations[0].updatedAt
        )
    }

}
