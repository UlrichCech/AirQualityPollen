package com.example.android.airqualitypollen.business.airquality.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pollen(val countGrassPollen: Int?,
                  val countTreePollen: Int?,
                  val countWeedPollen: Int?,
                  val riskGrassPollen: String?,
                  val riskTreePollen: String?,
                  val riskWeedPollen: String?) : Parcelable
