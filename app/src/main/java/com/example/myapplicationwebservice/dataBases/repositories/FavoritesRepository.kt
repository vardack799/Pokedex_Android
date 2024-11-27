package com.example.myapplicationwebservice.dataBases.repositories

import com.example.myapplicationwebservice.dataBases.daos.FavoritesDao
import com.example.myapplicationwebservice.dataBases.entities.FavoritePokemonEntity
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(private val dao1: FavoritesDao) {
    fun getAllFavorites(): Flow<List<FavoritePokemonEntity>> = dao1.getAllFavorites()

    suspend fun addFavorite(favorite: FavoritePokemonEntity) {
        dao1.insert(favorite)
    }

    suspend fun removeFavorite(id: Int) {
        dao1.deleteById(id)
    }
}