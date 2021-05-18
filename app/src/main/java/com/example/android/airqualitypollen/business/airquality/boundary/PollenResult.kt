package com.example.android.airqualitypollen.business.airquality.boundary

import android.os.Parcelable
import com.example.android.airqualitypollen.business.airquality.entity.Pollen
import kotlinx.parcelize.Parcelize

/**
 * The main data class for JSON-result of the AMBEE-Endpoint for getting the pollen data.
 */
@Parcelize
data class PollenResult(
    val message: String,
    val data: List<PollenResultData>) : Parcelable {

    fun toEntity(): Pollen {
        return Pollen(
            this.data[0].countData.grassPollen?.toInt(),
            this.data[0].countData.treePollen?.toInt(),
            this.data[0].countData.weedPollen?.toInt(),
            this.data[0].riskData.grassPollen,
            this.data[0].riskData.treePollen,
            this.data[0].riskData.weedPollen)
    }

}


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
