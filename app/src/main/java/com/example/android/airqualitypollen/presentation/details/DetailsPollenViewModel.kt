package com.example.android.airqualitypollen.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.airqualitypollen.business.airquality.boundary.AmbeeApi
import com.example.android.airqualitypollen.business.airquality.entity.Pollen
import com.example.android.airqualitypollen.business.configuration.GlobalAppConfiguration
import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO
import com.example.android.airqualitypollen.business.location.boundary.GeoLocation
import com.example.android.airqualitypollen.platform.persistence.EntityManager
import kotlinx.coroutines.launch
import java.util.*

class DetailsPollenViewModel : ViewModel() {

    private val _fetchPollen = MutableLiveData<Boolean>()
    val fetchPollen: LiveData<Boolean>
        get() = _fetchPollen

    private val _currentPollen = MutableLiveData<Pollen>()
    val currentPollen: LiveData<Pollen>
        get() = _currentPollen


    fun prepareData(favorite: FavoriteDTO?) {
        if (favorite == null) {
            // fetch values and show for current location (no persistence)
            _fetchPollen.value = true
        } else if (! isLastUpdateOnSameDay(favorite.updatedAt)) {
            // fetch and update new values
            _fetchPollen.value = true
        } else {
            // return the data from "favorite"
            _currentPollen.value = favorite.getPollenData()
        }
    }



    fun fetchPollen(favorite: FavoriteDTO?, geoLocation: GeoLocation?) {
        viewModelScope.launch {
            try {
                _fetchPollen.value = false
                var lat = geoLocation?.lat
                var lng = geoLocation?.lng
                if (favorite != null) {
                    lat = favorite.lat
                    lng = favorite.lng
                }
                val pollenForCurrentLocation =
                    AmbeeApi.RETROFIT_SERVICE.getPollenForCurrentLocation(
                        lat.toString(),
                        lng.toString(),
                        GlobalAppConfiguration.ambeeApiKey
                    ).toEntity()
                _currentPollen.value = pollenForCurrentLocation
                if (favorite != null) {
                    favorite.updatePollen(pollenForCurrentLocation)
                    Log.i("UCE", "Should update the favorite.")
                    EntityManager.getFavoriteDao().saveFavorite(favorite)
                }
            } catch (e: Exception) {
                if (favorite != null) {
                    _currentPollen.value = favorite.getPollenData()
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