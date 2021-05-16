package com.example.android.airqualitypollen.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.airqualitypollen.business.airquality.boundary.AmbeeApi
import com.example.android.airqualitypollen.business.airquality.entity.Pollen
import com.example.android.airqualitypollen.business.configuration.GlobalAppConfiguration
import com.example.android.airqualitypollen.business.location.boundary.GeoLocation
import kotlinx.coroutines.launch

class DetailsPollenViewModel : ViewModel() {

    private val _selectedGeoLocation = MutableLiveData<GeoLocation>()
    val selectedGeoLocation: LiveData<GeoLocation>
        get() = _selectedGeoLocation

    private val _currentPollen = MutableLiveData<Pollen>()
    val currentPollen: LiveData<Pollen>
        get() = _currentPollen


    fun updateSelectedLocation(location: GeoLocation) {
        _selectedGeoLocation.postValue(location)
    }

    fun fetchPollen(geoLocation: GeoLocation) {
        viewModelScope.launch {
            try {
                val pollenForCurrentLocation =
                    AmbeeApi.RETROFIT_SERVICE.getPollenForCurrentLocation(
                        geoLocation.lat.toString(),
                        geoLocation.lng.toString(),
                        GlobalAppConfiguration.ambeeApiKey
                    )
                _currentPollen.value = pollenForCurrentLocation.toEntity()
            } catch (e: Exception) {
                Log.e("UCE", e.message, e)
            }
        }
    }

}