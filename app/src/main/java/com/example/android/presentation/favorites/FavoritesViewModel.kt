package com.example.android.presentation.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class FavoritesViewModel : ViewModel() {

    var currentMarker: Marker? = null

    val reminderSelectedLocationStr = MutableLiveData<String>()
    val selectedPOI = MutableLiveData<LatLng>()
    val latitude = MutableLiveData<Double>()
    val longitude = MutableLiveData<Double>()


    fun setNewSelectedMarker(newMarker: Marker?) {
        currentMarker?.remove()
        currentMarker = newMarker
    }

}
