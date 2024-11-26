package com.example.myapplicationwebservice.services.controllers

import androidx.lifecycle.viewModelScope
import com.example.myapplicationwebservice.services.endpoints.ProductsEndpoints


import com.example.myapplicationwebservice.services.models.CaracPokemon
import com.example.myapplicationwebservice.services.models.Chain
import com.example.myapplicationwebservice.services.models.EvolChain
import com.example.myapplicationwebservice.services.models.EvolutionChain

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

                when (data) {
                    null -> success( PokemonResponse( emptyList(),0,"",Sprites(Other(OfficialArtwork(""))), emptyList(),
                        emptyList()
                    ))
                    else -> success(data)
                }
            } catch (e: Exception) {

                error()
            }
        }
    }
}

class DescriptionServices : BaseService() {

    fun getDescription(
        id:String,
        success: (description: FlavorTextEntries) -> Unit,
        error: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getRetrofit()
                    .create(ProductsEndpoints::class.java)
                    .getDescription(id)

                val data = response.body()

                when (data) {
                    null -> success(FlavorTextEntries(EvolutionChain(""), emptyList()))
                    else -> success(data)
                }
            } catch (e: Exception) {

                error()
            }
        }
    }
}
class EvolutionServices : BaseService() {

    fun getEvolution(
        id:String,
        success: (description: EvolChain) -> Unit,
        error: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getRetrofit()
                    .create(ProductsEndpoints::class.java)
                    .getEvolution(id)
                val data = response.body()
println(data)
                when (data) {
                    null -> success( EvolChain(Chain(emptyList())))
                    else -> success(data)
                }
            } catch (e: Exception) {

                error()
            }
        }
    }
}
