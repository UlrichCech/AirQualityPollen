package com.example.android.airqualitypollen.business.favorites.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "favorites")
data class FavoriteDTO(
    @ColumnInfo(name = "latitude") var latitude: Double?,
    @ColumnInfo(name = "longitude") var longitude: Double?,
    @ColumnInfo(name = "place_info") var placeInfo: String?,


    @PrimaryKey @ColumnInfo(name = "_id") val id: String = UUID.randomUUID().toString()
)
