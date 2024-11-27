package com.example.myapplicationwebservice.dataBases.viewsModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplicationwebservice.dataBases.ConexionDB
import com.example.myapplicationwebservice.dataBases.entities.FavoritePokemonEntity
import com.example.myapplicationwebservice.dataBases.entities.UsuarioEntity
import com.example.myapplicationwebservice.dataBases.repositories.FavoritesRepository
import com.example.myapplicationwebservice.dataBases.repositories.UsuariosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsuaiosViewModel(val context: Context) : ViewModel() {
    private val repository: UsuariosRepository

    init {
        val dao = ConexionDB.getDataBase(context).usuariosDao()
        this.repository = UsuariosRepository(dao)
    }

    fun loadUsers(
        data: (users: List<UsuarioEntity>) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.getAll().collect {
                data(it)
            }
        }
    }

    fun searchUser(id:Int,result:(user:UsuarioEntity)->Unit){
        viewModelScope.launch (Dispatchers.Main){
            repository.find(id).collect{
                result(it)
            }
        }
    }

    fun saveNewUser(user:UsuarioEntity){
        viewModelScope.launch (Dispatchers.Main){
            repository.save(user)
        }
    }
}

class PokemonsViewModel(context: Context) : ViewModel() {
    private val repository1: FavoritesRepository

    init {
        val dao = ConexionDB.getDataBase(context).favoritosDao()
        repository1 = FavoritesRepository(dao)
    }

    val favoritePokemons = repository1.getAllFavorites().asLiveData()

    fun addToFavorites(pokemon: FavoritePokemonEntity) {
        viewModelScope.launch {
            repository1.addFavorite(pokemon)
        }
    }

    fun removeFromFavorites(id: Int) {
        viewModelScope.launch {
            repository1.removeFavorite(id)
        }
    }
}
