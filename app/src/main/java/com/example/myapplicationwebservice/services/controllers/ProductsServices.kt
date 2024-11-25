package com.example.myapplicationwebservice.services.controllers

import androidx.collection.emptyObjectList
import androidx.lifecycle.viewModelScope
import com.example.myapplicationwebservice.services.endpoints.ProductsEndpoints
import com.example.myapplicationwebservice.services.models.Abilities
import com.example.myapplicationwebservice.services.models.Ability
import com.example.myapplicationwebservice.services.models.AbilityWrapper
import com.example.myapplicationwebservice.services.models.CaracPokemon
import com.example.myapplicationwebservice.services.models.OfficialArtwork
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
        success: (abilities: OfficialArtwork) -> Unit,
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
                    null -> success(OfficialArtwork(""))
                    else -> success(data.sprites.other.officialArtwork)
                }
            } catch (e: Exception) {
                println("peerra ${e}")
                error()
            }
        }
    }
}
