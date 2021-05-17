package com.example.android.airqualitypollen.business.favorites.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.airqualitypollen.business.airquality.entity.AirQuality
import com.example.android.airqualitypollen.business.airquality.entity.Pollen
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "favorites")
data class FavoriteDTO(
    @ColumnInfo(name = "latitude") var latitude: Double? = null,
    @ColumnInfo(name = "longitude") var longitude: Double? = null,
    @ColumnInfo(name = "place_info") var placeInfo: String? = null,
    @ColumnInfo(name = "no2") var no2: Double? = null,
    @ColumnInfo(name = "pm10") var pm10: Double? = null,
    @ColumnInfo(name = "pm25") var pm25: Double? = null,
    @ColumnInfo(name = "co") var co: Double? = null,
    @ColumnInfo(name = "so2") var so2: Double? = null,
    @ColumnInfo(name = "ozone") var ozone: Double? = null,
    @ColumnInfo(name = "city") var city: String? = null,
    @ColumnInfo(name = "country_code") var countryCode: String? = null,
    @ColumnInfo(name = "division") var division: String? = null,
    @ColumnInfo(name = "lat") var lat: Double? = null,
    @ColumnInfo(name = "lng") var lng: Double? = null,
    @ColumnInfo(name = "place_name") var placeName: String? = null,
    @ColumnInfo(name = "postal_code") var postalCode: String? = null,
    @ColumnInfo(name = "state") var state: String? = null,
    @ColumnInfo(name = "aqi") var aqi: Double? = null,
    @ColumnInfo(name = "pollutant") var pollutant: String? = null,
    @ColumnInfo(name = "concentration") var concentration: Double? = null,
    @ColumnInfo(name = "category") var category: String? = null,
    @ColumnInfo(name = "updated_at") var updatedAt: Date? = null,
    @ColumnInfo(name = "count_grass_pollen") var countGrassPollen: Int? = null,
    @ColumnInfo(name = "count_tree_pollen") var countTreePollen: Int? = null,
    @ColumnInfo(name = "count_weed_pollen") var countWeedPollen: Int? = null,
    @ColumnInfo(name = "risk_grass_pollen") var riskGrassPollen: String? = null,
    @ColumnInfo(name = "risk_tree_pollen") var riskTreePollen: String? = null,
    @ColumnInfo(name = "risk_w3eed_pollen") var riskWeedPollen: String? = null,
    @PrimaryKey @ColumnInfo(name = "_id") val id: String = UUID.randomUUID().toString()
) {
    fun updateAirQuality(airQuality: AirQuality) {
        placeInfo = createPlaceInfo(airQuality)
        no2 = airQuality.no2
        pm10 = airQuality.pm10
        pm25 = airQuality.pm25
        so2 = airQuality.so2
        ozone = airQuality.ozone
        city = airQuality.city
        countryCode = airQuality.countryCode
        division = airQuality.division
        lat = airQuality.lat
        lng = airQuality.lng
        placeName = airQuality.placeName
        postalCode = airQuality.postalCode
        state = airQuality.state
        aqi = airQuality.aqi
        pollutant = airQuality.pollutant
        concentration = airQuality.concentration
        category = airQuality.category
        updatedAt = fromStringToTimestamp(airQuality.updatedAt)
    }

    fun updatePollen(pollen: Pollen) {
        countGrassPollen = pollen.countGrassPollen
        countTreePollen = pollen.countTreePollen
        countWeedPollen = pollen.countWeedPollen
        riskGrassPollen = pollen.riskGrassPollen
        riskTreePollen = pollen.riskTreePollen
        riskWeedPollen = pollen.riskWeedPollen
    }

    private fun createPlaceInfo(airQuality: AirQuality): String {
        val data = ArrayList<String>()
        if (airQuality.placeName != null) {
            data.add(airQuality.placeName)
        }
        if (airQuality.postalCode != null) {
            data.add(airQuality.postalCode)
        }
        if (airQuality.division != null) {
            data.add(airQuality.division)
        }
        if (airQuality.state != null) {
            data.add(airQuality.state)
        }
        if (airQuality.countryCode != null) {
            data.add(airQuality.countryCode)
        }
        return data.joinToString(separator = ", ")
    }

    private fun fromStringToTimestamp(value: String?): Date? {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return if (value != null) {
            try {
                return sdf.parse(value)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            null
        } else {
            null
        }
    }

}