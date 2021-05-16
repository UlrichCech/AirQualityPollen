package com.example.android.airqualitypollen.business.favorites.boundary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO

/**
 * Data Access Object for the favorites table.
 */
@Dao
interface FavoritesDao {

    /**
     * @return all favorites.
     */
    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<FavoriteDTO>

    /**
     * @param favId the id of the favorite entry
     * @return the favorite object with the id
     */
    @Query("SELECT * FROM favorites where _id = :favId")
    suspend fun getFavoriteById(favId: String): FavoriteDTO?

    /**
     * Insert a favorite in the database. If the favorite already exists, replace it.
     *
     * @param favorite the favorite entry to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(favorite: FavoriteDTO)

    /**
     * Delete favorite.
     */
    @Query("DELETE FROM favorites where _id = :favId")
    suspend fun deleteFavoriteById(favId: String)

}