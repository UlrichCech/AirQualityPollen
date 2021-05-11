package com.example.android.business.airquality.boundary

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AqiInfo(val pollutant: String?,
              val concentration: Double?,
              val category: String?) : Parcelable {}
