package com.example.myapplicationwebservice.services.models

import com.google.gson.annotations.SerializedName

data class FlavorTextEntries(
    @SerializedName("evolution_chain") var evolutionChain: EvolutionChain,
    @SerializedName("flavor_text_entries") var flavorTextEntries: List<FlavorTextAndLanguage>
)

data class EvolutionChain(
    var url: String
)

data class FlavorTextAndLanguage(
    @SerializedName("flavor_text")var flavorText: String,
    var language: Language
)

data class Language(
    var name: String,
    var url: String
)