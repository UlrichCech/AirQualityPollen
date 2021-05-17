package com.example.android.airqualitypollen.presentation.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class FavoritesViewModel : ViewModel() {

    var currentMarker: Marker? = null

    val reminderSelectedLocationStr = MutableLiveData<String>()
    val selectedLatLng = MutableLiveData<LatLng>()
    val latitude = MutableLiveData<Double>()
    val longitude = MutableLiveData<Double>()


    fun setNewSelectedMarker(newMarker: Marker?) {
        currentMarker?.remove()
        currentMarker = newMarker
    }

}
