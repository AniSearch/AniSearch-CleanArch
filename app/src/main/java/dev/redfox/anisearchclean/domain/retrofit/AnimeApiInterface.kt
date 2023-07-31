package dev.redfox.anisearchclean.domain.retrofit

import dev.redfox.anisearchclean.data.dataclasses.topMangaModel.TopManga
import dev.refox.anitrack.models.topAnimeModel.TopAnime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApiInterface {
    @GET("v4/top/anime/")
    suspend fun getTopAnime(): Response<TopAnime>

    @GET("v4/top/manga/")
    suspend fun getTopManga(): Response<TopManga>

    @GET("v4/anime/")
    suspend fun getAnimeSearch(@Query("q")queryString: String): Response<TopAnime>

    @GET("v4/manga/")
    suspend fun getMangaSearch(@Query("q")queryString: String): Response<TopManga>
}