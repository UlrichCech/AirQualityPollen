package com.example.android.airqualitypollen.platform.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.airqualitypollen.business.favorites.boundary.FavoritesDao
import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO

/**
 * The Room Database that contains the reminders table.
 */
@Database(entities = [FavoriteDTO::class], version = 1, exportSchema = false)
@TypeConverters(PersistentConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao
}