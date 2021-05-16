package com.example.android.airqualitypollen.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.airqualitypollen.business.airquality.boundary.AmbeeApi
import com.example.android.airqualitypollen.business.airquality.entity.AirQuality
import com.example.android.airqualitypollen.business.configuration.GlobalAppConfiguration
import com.example.android.airqualitypollen.business.location.boundary.GeoLocation
import kotlinx.coroutines.launch

class DetailsAirQualityViewModel : ViewModel() {

    private val _selectedGeoLocation = MutableLiveData<GeoLocation>()
    val selectedGeoLocation: LiveData<GeoLocation>
        get() = _selectedGeoLocation

    private val _currentAirQuality = MutableLiveData<AirQuality>()
    val currentAirQuality: LiveData<AirQuality>
        get() = _currentAirQuality


    fun updateSelectedLocation(location: GeoLocation) {
        _selectedGeoLocation.postValue(location)
    }

    fun fetchAirQuality(geoLocation: GeoLocation) {
        viewModelScope.launch {
            try {
                _currentAirQuality.value =
                    AmbeeApi.RETROFIT_SERVICE.getAirQualityForCurrentLocation(
                        geoLocation.lat.toString(), geoLocation.lng.toString(), GlobalAppConfiguration.ambeeApiKey).toEntity()
            } catch (e: Exception) {
                Log.e("UCE", e.message, e)
            }
        }
    }

}