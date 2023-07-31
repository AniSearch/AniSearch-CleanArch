package dev.redfox.anisearchclean.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.redfox.anisearchclean.data.Repositories.AnimeApiRepository
import dev.redfox.anisearchclean.data.dataclasses.topMangaModel.TopManga
import dev.refox.anitrack.models.topAnimeModel.TopAnime
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(private val repository: AnimeApiRepository): ViewModel() {

    //Anime Response
    private val _searchAnimeResponse = MutableLiveData<Response<TopAnime>>()
    val searchAnimeResponse: LiveData<Response<TopAnime>> get() = _searchAnimeResponse
    private val _topAnimeResponse = MutableLiveData<Response<TopAnime>>()
    val topAnimeResponse: LiveData<Response<TopAnime>> get() = _topAnimeResponse

    //Manga Response
    private val _searchMangaResponse = MutableLiveData<Response<TopManga>>()
    val searchMangaResponse: LiveData<Response<TopManga>> get() = _searchMangaResponse
    private val _topMangaResponse = MutableLiveData<Response<TopManga>>()
    val topMangaeResponse: LiveData<Response<TopManga>> get() = _topMangaResponse


    fun getTopAnime() {
        viewModelScope.launch {
            val tResponse = repository.getTopAnime()
            _topAnimeResponse.value = tResponse
        }
    }

    fun getAnimeSearch(queryString: String){
        viewModelScope.launch {
            val sResponse = repository.getAnimeSearch(queryString)
            _searchAnimeResponse.value = sResponse
        }
    }


    fun getTopManga() {
        viewModelScope.launch {
            val tResponse = repository.getTopManga()
            _topMangaResponse.value = tResponse
        }
    }

    fun getMangaSearch(queryString: String){
        viewModelScope.launch {
            val sResponse = repository.getMangaSearch(queryString)
            _searchMangaResponse.value = sResponse
        }
    }

}