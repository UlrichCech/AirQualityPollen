package com.example.android.business.airquality.boundary

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PollenResultData(@Json(name="Count") val countData: PollenResultCountData,
                            @Json(name = "Risk") val riskData: PollenResultRiskData): Parcelable {}


//{
//    "message": "success",
//    "data": [
//        {
//            "Count": {
//                "grass_pollen": 25,
//                "tree_pollen": 16,
//                "weed_pollen": 375
//            },
//            "Risk": {
//                "grass_pollen": "Low",
//                "tree_pollen": "Low",
//                "weed_pollen": "High"
//            }
//        }
//    ]
//}
