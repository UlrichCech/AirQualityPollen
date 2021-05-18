package com.example.android.airqualitypollen.business.airquality.boundary

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * Data class for JSON-result of the AMBEE-Endpoint for getting the pollen data.
 */
@Parcelize
data class PollenResultRiskData(@Json(name = "grass_pollen") val grassPollen: String?,
                                @Json(name = "tree_pollen") val treePollen: String?,
                                @Json(name = "weed_pollen") val weedPollen: String?) : Parcelable {}