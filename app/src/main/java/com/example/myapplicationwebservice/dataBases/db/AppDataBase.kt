package com.example.myapplicationwebservice.dataBases.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplicationwebservice.dataBases.daos.FavoritesDao
import com.example.myapplicationwebservice.dataBases.daos.UsuariosDao
import com.example.myapplicationwebservice.dataBases.entities.FavoritePokemonEntity
import com.example.myapplicationwebservice.dataBases.entities.UsuarioEntity
@Database(
    entities = [
        UsuarioEntity::class,
        FavoritePokemonEntity::class
    ],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun usuariosDao(): UsuariosDao
    abstract fun favoritosDao(): FavoritesDao

}