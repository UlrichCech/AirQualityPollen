package com.udacity.project4.locationreminders.data.local

import android.content.Context
import androidx.room.Room
import com.example.android.airqualitypollen.business.favorites.boundary.FavoritesDao
import com.example.android.airqualitypollen.platform.persistence.AppDatabase


object AppDB {

    fun createAppDao(context: Context): FavoritesDao {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "airqualitypollen.db"
        ).build().favoritesDao()
    }

}