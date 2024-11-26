package com.example.myapplicationwebservice.services.models

import com.google.gson.annotations.SerializedName

data class EvolChain(
    var chain: Chain
)
data class Chain(
    @SerializedName("evolves_to")var evolvesTo: List<EvolvesTo>,
)

data class EvolvesTo(
    @SerializedName("evolves_to")var evolvesTo: List<EvolvesTo>,
    var species: Species
)
data class Species(
     var name: String,
     var url: String
)