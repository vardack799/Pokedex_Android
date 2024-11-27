package com.example.myapplicationwebservice.dataBases.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pokemons")
data class FavoritePokemonEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image_url") val imageUrl: String
)