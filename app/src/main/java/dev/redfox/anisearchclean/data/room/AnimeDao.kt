package dev.redfox.anisearchclean.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.redfox.anisearchclean.data.dataclasses.Animes

@Dao
interface AnimesDao {
    @Query("SELECT * FROM anime_list_table")
    fun getAllAnimes(): LiveData<MutableList<Animes>>

    @Insert
    fun insertAnimes(vararg attendance: Animes)

    @Delete
    fun deleteAnimes(vararg attendance: Animes)

    @Update
    fun updateAnimes(vararg attendance: Animes)
}