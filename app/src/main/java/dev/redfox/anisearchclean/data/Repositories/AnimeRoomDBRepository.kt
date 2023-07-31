package dev.redfox.anisearchclean.data.Repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import dev.redfox.anisearchclean.data.dataclasses.Animes
import dev.redfox.anisearchclean.data.room.AnimesDao
import javax.inject.Inject

class AnimeRoomDBRepository @Inject constructor(private val animesDao: AnimesDao) {
    val allAnimesLists: LiveData<MutableList<Animes>> = animesDao.getAllAnimes()

    @WorkerThread
    fun insertAnimes(animes: Animes) {
        animesDao.insertAnimes(animes)
    }

    @WorkerThread
    fun deleteAnimes(animes: Animes) {
        animesDao.deleteAnimes(animes)
    }

    @WorkerThread
    fun updateAnimes(animes: Animes) {
        animesDao.updateAnimes(animes)
    }
}