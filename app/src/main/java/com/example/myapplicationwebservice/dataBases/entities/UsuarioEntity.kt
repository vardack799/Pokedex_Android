package com.example.myapplicationwebservice.dataBases.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "nombre") val name:String,
    @ColumnInfo(name = "correo") val email:String
):Serializable
