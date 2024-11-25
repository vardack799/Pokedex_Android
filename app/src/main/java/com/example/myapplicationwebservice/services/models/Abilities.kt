package com.example.myapplicationwebservice.services.models

data class Abilities(
    val abilities: List<AbilityWrapper>
)

data class AbilityWrapper(
    val ability: Ability,
//    val is_hidden: Boolean,
//    val slot: Int
)

data class Ability(
    val name: String,
    val url: String
)

