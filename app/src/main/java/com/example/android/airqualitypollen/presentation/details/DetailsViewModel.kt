package com.example.android.airqualitypollen.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.airqualitypollen.business.airquality.boundary.PollenResult
import com.example.android.airqualitypollen.business.airquality.entity.AirQuality
import com.example.android.airqualitypollen.business.location.boundary.GeoLocation

class DetailsViewModel : ViewModel() {

    private val _selectedGeoLocation = MutableLiveData<GeoLocation>()
    val selectedGeoLocation: LiveData<GeoLocation>
        get() = _selectedGeoLocation

    private val _currentAirQuality = MutableLiveData<AirQuality>()
    val currentAirQuality: LiveData<AirQuality>
        get() = _currentAirQuality

    private val _currentPollen = MutableLiveData<PollenResult>()
    val currentPollen: LiveData<PollenResult>
        get() = _currentPollen


//    fun updateSelectedLocation(location: GeoLocation) {
//        _selectedGeoLocation.postValue(location)
//    }
//
//    fun fetchAirQualityAndPollen(geoLocation: GeoLocation) {
//        viewModelScope.launch {
//            try {
//                _currentAirQuality.value =
//                    AmbeeApi.RETROFIT_SERVICE.getAirQualityForCurrentLocation(
//                        geoLocation.lat.toString(), geoLocation.lng.toString(), GlobalAppConfiguration.ambeeApiKey).toEntity()
//                _currentPollen.value =
//                    AmbeeApi.RETROFIT_SERVICE.getPollenForCurrentLocation(
//                        geoLocation.lat.toString(), geoLocation.lng.toString(), GlobalAppConfiguration.ambeeApiKey)
//            } catch (e: Exception) {
//                Log.e("UCE", e.message, e)
//            }
//        }
//    }
//
//
}