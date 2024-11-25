package com.example.myapplicationwebservice.dataBases.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplicationwebservice.dataBases.daos.UsuariosDao
import com.example.myapplicationwebservice.dataBases.entities.UsuarioEntity

@Database(
    entities = [
        UsuarioEntity::class
    ],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun usuariosDao(): UsuariosDao
}