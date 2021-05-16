package com.example.android.airqualitypollen.business.airquality.boundary

import android.os.Parcelable
import com.example.android.airqualitypollen.business.airquality.entity.Pollen
import kotlinx.parcelize.Parcelize

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
