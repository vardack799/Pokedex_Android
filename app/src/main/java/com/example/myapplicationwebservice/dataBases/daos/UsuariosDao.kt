package com.example.myapplicationwebservice.dataBases.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplicationwebservice.dataBases.entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuariosDao {
    @Query("select * from usuarios")
    fun getAll(): Flow<List<UsuarioEntity>>

    @Query("select * from usuarios where id=:id")
    fun find(id: Int): Flow<UsuarioEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg usuario:UsuarioEntity)
}