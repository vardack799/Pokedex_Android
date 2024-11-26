package com.example.myapplicationwebservice.services.models

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    val abilities: List<AbilityWrapper>,
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val stats : List<Stat>,
    val types : List<SlotType>
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

data class Stat (
        @SerializedName("base_stat") val baseStat: Int,
        val stat: opcStat
        )

data class opcStat (
        val name: String
        )

data class SlotType(
    val slot: Int,
    val type: Type
)


data class Type(
    val name: String,
    val url: String

)