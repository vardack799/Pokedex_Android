package com.example.myapplicationwebservice.services.driverAdapters

import com.example.myapplicationwebservice.services.controllers.ProductsServices
import com.example.myapplicationwebservice.services.controllers.SpriteServices
import com.example.myapplicationwebservice.services.models.Ability
import com.example.myapplicationwebservice.services.models.AbilityWrapper
import com.example.myapplicationwebservice.services.models.CaracPokemon
import com.example.myapplicationwebservice.services.models.OfficialArtwork
import com.example.myapplicationwebservice.services.models.Sprites

class ProductsDiverAdapter {
    private val service: ProductsServices = ProductsServices()

    fun allProducts(
        loadData: (list: List<CaracPokemon>) -> Unit,
        errorData: () -> Unit
    ) {
        this.service.getAllProducts(
            success = { loadData(it) },
            error = { errorData() }
        )
    }
}

class SpriteDiverAdapter {
    private val service: SpriteServices = SpriteServices()

    fun allAbility(
       name: String,
        loadData: (objeto: OfficialArtwork) -> Unit,
        onError: () -> Unit
    ) {
        this.service.getAbility(
            name = name,
            success = { loadData(it) },
            error = { onError() }
        )
    }
}