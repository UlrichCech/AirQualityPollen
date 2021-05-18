package com.example.android.airqualitypollen.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.airqualitypollen.business.airquality.boundary.AmbeeApi
import com.example.android.airqualitypollen.business.airquality.entity.AirQuality
import com.example.android.airqualitypollen.business.configuration.GlobalAppConfiguration
import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO
import com.example.android.airqualitypollen.business.location.boundary.GeoLocation
import com.example.android.airqualitypollen.platform.persistence.EntityManager
import kotlinx.coroutines.launch
import java.util.*

class DetailsAirQualityViewModel : ViewModel() {

    private val _fetchAirQuality = MutableLiveData<Boolean>()
    val fetchAirQuality: LiveData<Boolean>
        get() = _fetchAirQuality

    private val _currentAirQuality = MutableLiveData<AirQuality>()
    val currentAirQuality: LiveData<AirQuality>
        get() = _currentAirQuality


    fun prepareData(favorite: FavoriteDTO?) {
        if (favorite == null) {
            // fetch values and show for current location (no persistence)
            _fetchAirQuality.value = true
        } else if (! isLastUpdateOnSameDay(favorite.updatedAt)) {
            // fetch and update new values
            _fetchAirQuality.value = true
        } else {
            // return the data from "favorite"
            _currentAirQuality.value = favorite.getAirQualityData()
        }
    }


    fun fetchAirQuality(favorite: FavoriteDTO?, geoLocation: GeoLocation?) {
        viewModelScope.launch {
            try {
                _fetchAirQuality.value = false
                var lat = geoLocation?.lat
                var lng = geoLocation?.lng
                if (favorite != null) {
                    lat = favorite.lat
                    lng = favorite.lng
                }
                val newAirQualityData = AmbeeApi.RETROFIT_SERVICE.getAirQualityForCurrentLocation(
                    lat.toString(),
                    lng.toString(),
                    GlobalAppConfiguration.ambeeApiKey
                ).toEntity()
                _currentAirQuality.value = newAirQualityData
                // update the favorite data
                if (favorite != null) {
                    Log.i("UCE", "Should update the favorite.")
                    favorite.updateAirQuality(newAirQualityData)
                    EntityManager.getFavoriteDao().saveFavorite(favorite)
                }
            } catch (e: Exception) {
                if (favorite != null) {
                    _currentAirQuality.value = favorite.getAirQualityData()
                }
                Log.e("UCE", e.message, e)
            }
        }
    }

    private fun isLastUpdateOnSameDay(updatedAt: Date?): Boolean {
        if (updatedAt == null) return false
        val currentTime = GregorianCalendar()
        currentTime.time = Date()
        val lastUpdate = GregorianCalendar()
        lastUpdate.time = updatedAt
        return currentTime.get(Calendar.YEAR) == lastUpdate.get(Calendar.YEAR)
                && currentTime.get(Calendar.MONTH) == lastUpdate.get(Calendar.MONTH)
                && currentTime.get(Calendar.DAY_OF_MONTH) == lastUpdate.get(Calendar.DAY_OF_MONTH)
    }

}
