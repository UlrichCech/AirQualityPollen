package com.example.android.airqualitypollen.business.airquality.boundary

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * Data class for JSON-result of the AMBEE-Endpoint for getting the pollen data
 *   (wrapper class for count- and risk-data).
 */
@Parcelize
data class PollenResultData(@Json(name="Count") val countData: PollenResultCountData,
                            @Json(name = "Risk") val riskData: PollenResultRiskData): Parcelable
