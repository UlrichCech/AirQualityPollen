package com.example.android.airqualitypollen.platform.persistence

import android.content.Context
import androidx.room.Room
import com.example.android.airqualitypollen.business.favorites.boundary.FavoritesDao

object EntityManager {

    private lateinit var INSTANCE: AppDatabase


    fun initialize(context: Context) {
        synchronized(AppDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "airqualitypollen.db").build()
            }
        }
    }

    fun getFavoriteDao(): FavoritesDao {
        return INSTANCE.favoritesDao()
    }

}