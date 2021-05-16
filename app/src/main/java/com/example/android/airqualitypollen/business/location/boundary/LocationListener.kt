package com.example.android.airqualitypollen.business.location.boundary

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log

class LocationListener: LocationListener {

    override fun onLocationChanged(location: Location) {
        Log.i("UCE", location.toString())
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        super.onStatusChanged(provider, status, extras)
    }
}