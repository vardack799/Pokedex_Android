package com.example.myapplicationwebservice.services.models

import com.google.gson.annotations.SerializedName

data class PokemonResponse(

    val sprites: Sprites
)

data class Sprites(
    val other: Other
)

data class Other(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtwork
)

data class OfficialArtwork(
    @SerializedName("front_default") val frontDefault: String
)