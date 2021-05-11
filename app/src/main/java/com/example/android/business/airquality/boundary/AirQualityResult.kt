package com.example.android.business.airquality.boundary

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AirQualityResult(val message: String,
                            val stations: List<Station>) : Parcelable {
}
