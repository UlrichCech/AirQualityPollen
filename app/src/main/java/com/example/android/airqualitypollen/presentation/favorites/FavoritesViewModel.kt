package com.example.android.airqualitypollen.presentation.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class FavoritesViewModel : ViewModel() {

    private var currentMarker: Marker? = null

    val selectedLatLng = MutableLiveData<LatLng>()

    val showErrorMessage: MutableLiveData<String> = MutableLiveData()


    fun setNewSelectedMarker(newMarker: Marker?) {
        currentMarker?.remove()
        currentMarker = newMarker
    }

}
