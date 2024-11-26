package com.example.myapplicationwebservice.services.controllers

import androidx.collection.emptyObjectList
import androidx.lifecycle.viewModelScope
import com.example.myapplicationwebservice.services.endpoints.ProductsEndpoints

import com.example.myapplicationwebservice.services.models.Ability
import com.example.myapplicationwebservice.services.models.AbilityWrapper
import com.example.myapplicationwebservice.services.models.CaracPokemon
import com.example.myapplicationwebservice.services.models.FlavorTextAndLanguage
import com.example.myapplicationwebservice.services.models.FlavorTextEntries
import com.example.myapplicationwebservice.services.models.OfficialArtwork
import com.example.myapplicationwebservice.services.models.Other
import com.example.myapplicationwebservice.services.models.PokemonResponse
import com.example.myapplicationwebservice.services.models.Sprites

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsServices : BaseService() {

    fun getAllProducts(
        success: (pokemons: List<CaracPokemon>) -> Unit,
        error: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getRetrofit()
                    .create(ProductsEndpoints::class.java)
                    .getAllProducts()
                val data = response.body()
                println(data)
                when (data) {
                    null -> success(emptyList())
                    else -> success(data.results)
                }
            } catch (e: Exception) {
                println(e)
                error()
            }
        }
    }
}

class SpriteServices : BaseService() {

    fun getAbility(
        name:String,
        success: (sprites: PokemonResponse) -> Unit,
        error: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getRetrofit()
                    .create(ProductsEndpoints::class.java)
                    .getAbility(name)
                println(response.body())
                val data = response.body()
                println(data)
                when (data) {
                    null -> success( PokemonResponse( emptyList(),0,"",Sprites(Other(OfficialArtwork(""))), emptyList(),
                        emptyList()
                    ))
                    else -> success(data)
                }
            } catch (e: Exception) {
                println("peerra ${e}")
                error()
            }
        }
    }
}

class DescriptionServices : BaseService() {

    fun getDescription(
        id:String,
        success: (description: List<FlavorTextAndLanguage>) -> Unit,
        error: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getRetrofit()
                    .create(ProductsEndpoints::class.java)
                    .getDescription(id)
                println(response.body())
                val data = response.body()
                println(data)
                when (data) {
                    null -> success( emptyList())
                    else -> success(data.flavorTextEntries)
                }
            } catch (e: Exception) {
                println("peerra ${e}")
                error()
            }
        }
    }
}
