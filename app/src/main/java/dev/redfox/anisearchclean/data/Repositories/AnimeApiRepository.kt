package dev.redfox.anisearchclean.data.Repositories

import dev.redfox.anisearchclean.data.dataclasses.topMangaModel.TopManga
import dev.redfox.anisearchclean.domain.retrofit.AnimeApiInterface
import dev.refox.anitrack.models.topAnimeModel.TopAnime
import retrofit2.Response
import javax.inject.Inject

class AnimeApiRepository @Inject constructor(private val animeApiInterface: AnimeApiInterface) {
    suspend fun getTopAnime(): Response<TopAnime> {
        return  animeApiInterface.getTopAnime()
    }

    suspend fun getTopManga(): Response<TopManga> {
        return animeApiInterface.getTopManga()
    }

    suspend fun getAnimeSearch(queryString: String): Response<TopAnime> {
        return animeApiInterface.getAnimeSearch(queryString)
    }

    suspend fun getMangaSearch(queryString: String): Response<TopManga> {
        return animeApiInterface.getMangaSearch(queryString)
    }
}