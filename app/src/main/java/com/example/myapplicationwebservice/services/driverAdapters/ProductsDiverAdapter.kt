package com.example.myapplicationwebservice.services.driverAdapters

import com.example.myapplicationwebservice.services.controllers.DescriptionServices
import com.example.myapplicationwebservice.services.controllers.ProductsServices
import com.example.myapplicationwebservice.services.controllers.SpriteServices
import com.example.myapplicationwebservice.services.models.Ability
import com.example.myapplicationwebservice.services.models.AbilityWrapper
import com.example.myapplicationwebservice.services.models.CaracPokemon
import com.example.myapplicationwebservice.services.models.FlavorTextAndLanguage
import com.example.myapplicationwebservice.services.models.OfficialArtwork
import com.example.myapplicationwebservice.services.models.PokemonResponse
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
        loadData: (objeto: PokemonResponse) -> Unit,
        onError: () -> Unit
    ) {
        this.service.getAbility(
            name = name,
            success = { loadData(it) },
            error = { onError() }
        )
    }
}
class DescriptionDiverAdapter {
    private val service: DescriptionServices = DescriptionServices()

    fun allDescription(
        id: String,
        loadData: (desciption: List<FlavorTextAndLanguage>) -> Unit,
        onError: () -> Unit
    ) {
        this.service.getDescription(
            id = id,
            success = { loadData(it) },
            error = { onError() }
        )
    }
}