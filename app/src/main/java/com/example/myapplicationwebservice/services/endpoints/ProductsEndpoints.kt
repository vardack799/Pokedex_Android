package com.example.myapplicationwebservice.services.endpoints


import com.example.myapplicationwebservice.services.models.Abilities
import com.example.myapplicationwebservice.services.models.Ability
import com.example.myapplicationwebservice.services.models.Pokemon
import com.example.myapplicationwebservice.services.models.PokemonResponse
import com.example.myapplicationwebservice.services.models.Sprites
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsEndpoints {
    @GET("pokemon/?limit=1025")
    suspend fun getAllProducts(): Response<Pokemon>

    @GET("pokemon/{name}")
    suspend fun getAbility(@Path("name") name:String ): Response<PokemonResponse>
}