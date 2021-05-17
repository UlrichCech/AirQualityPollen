package com.example.android.airqualitypollen.business.favorites.boundary

import com.example.android.airqualitypollen.business.favorites.entity.FavoriteDTO
import kotlinx.coroutines.*

/**
 * Concrete implementation of a data source as a db.
 *
 * @param favoritesDao the dao that does the Room db operations
 * @param ioDispatcher a coroutine dispatcher to offload the blocking IO tasks
 */
class FavoritesRepository(
    private val favoritesDao: FavoritesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun getAllFavorites(): Result<List<FavoriteDTO>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.success(favoritesDao.getAllFavorites())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    suspend fun saveFavorite(favorite: FavoriteDTO) =
        withContext(ioDispatcher) {
            favoritesDao.saveFavorite(favorite)
        }

    suspend fun getFavorite(id: String) : Result<FavoriteDTO> = withContext(ioDispatcher) {
        return@withContext try {
            val favorite = favoritesDao.getFavoriteById(id)
            if (favorite != null) {
                Result.success(favorite)
            }
            Result.failure(Exception("No favorite with the ID <$id> found."))
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    suspend fun deleteReminderById(id: String) {
        withContext(ioDispatcher) {
            favoritesDao.deleteFavoriteById(id)
        }
    }

    suspend fun deleteAllReminders() {
        withContext(ioDispatcher) {
            favoritesDao.deleteAllFavorites()
        }
    }
}
