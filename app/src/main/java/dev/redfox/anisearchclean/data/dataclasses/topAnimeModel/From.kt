package dev.refox.anitrack.models.topAnimeModel


import com.google.gson.annotations.SerializedName

data class From(
    @SerializedName("day")
    val day: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("year")
    val year: Int
)