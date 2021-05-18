package com.example.android.airqualitypollen.business.airquality.boundary

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class for JSON-result of the AMBEE-Endpoint for getting the air-quality.
 */
@Parcelize
data class AqiInfo(val pollutant: String?,
              val concentration: Double?,
              val category: String?) : Parcelable {}
