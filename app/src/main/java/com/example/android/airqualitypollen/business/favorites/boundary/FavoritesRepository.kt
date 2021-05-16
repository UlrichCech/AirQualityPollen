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

//    override suspend fun saveReminder(reminder: ReminderDTO) =
//        withContext(ioDispatcher) {
//            favoritesDao.saveReminder(reminder)
//        }
//
//    override suspend fun getReminder(id: String): Result<ReminderDTO> = withContext(ioDispatcher) {
//        try {
//            val reminder = favoritesDao.getReminderById(id)
//            if (reminder != null) {
//                return@withContext Result.Success(reminder)
//            } else {
//                return@withContext
//            }
//        } catch (e: Exception) {
//            return@withContext Result.Error(e.localizedMessage)
//        }
//    }
//
//    override suspend fun deleteAllReminders() {
//        withContext(ioDispatcher) {
//            favoritesDao.deleteAllReminders()
//        }
//    }
}
