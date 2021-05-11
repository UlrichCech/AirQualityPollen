package com.example.android.business.airquality.boundary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.business.configuration.GlobalAppConfiguration
import kotlinx.coroutines.launch

class AirQualityViewModel : ViewModel() {

    private val _currentAirQuality = MutableLiveData<AirQualityResult>()
    val currentAirQuality: LiveData<AirQualityResult>
        get() = _currentAirQuality

    private val _currentPollen = MutableLiveData<PollenResult>()
    val currentPollen: LiveData<PollenResult>
        get() = _currentPollen


    fun getAirQualityForLocation(lat: Double, lng: Double) {
        viewModelScope.launch {
            try {
                // TODO: get the current position
                _currentAirQuality.postValue(
                    AmbeeApi.RETROFIT_SERVICE.getAirQualityForCurrentLocation(
                        lat.toString(), lng.toString(), GlobalAppConfiguration.ambeeApiKey))
                _currentPollen.postValue(
                    AmbeeApi.RETROFIT_SERVICE.getPollenForCurrentLocation(
                        lat.toString(), lng.toString(), GlobalAppConfiguration.ambeeApiKey))
            } catch (e: Exception) {
                Log.e("UCE", e.message, e)
            }
        }

    }
}