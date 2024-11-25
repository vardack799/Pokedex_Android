package com.example.myapplicationwebservice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplicationwebservice.components.ImageWeb
import com.example.myapplicationwebservice.dataBases.entities.UsuarioEntity
import com.example.myapplicationwebservice.dataBases.viewsModels.UsuaiosViewModel
import com.example.myapplicationwebservice.services.driverAdapters.ProductsDiverAdapter
import com.example.myapplicationwebservice.services.driverAdapters.SpriteDiverAdapter
import com.example.myapplicationwebservice.services.models.Abilities
import com.example.myapplicationwebservice.services.models.Ability
import com.example.myapplicationwebservice.services.models.AbilityWrapper
import com.example.myapplicationwebservice.services.models.CaracPokemon
import com.example.myapplicationwebservice.services.models.OfficialArtwork
import com.example.myapplicationwebservice.services.models.Sprites
import com.example.myapplicationwebservice.ui.theme.MyApplicationWebServiceTheme

class MainActivity : ComponentActivity() {
    val productsDiverAdapter by lazy { ProductsDiverAdapter() }
    val spriteDiverAdapter by lazy { SpriteDiverAdapter() }
//    val usuariosViewModel by lazy { UsuaiosViewModel(this) }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
//            usuariosViewModel.saveNewUser(UsuarioEntity(
//                id = 0,
//                name = "Pepito",
//                email = "pepito@test.com"
//            ))
            var pokemons by remember { mutableStateOf<List<CaracPokemon>>(emptyList()) }
            var loadProducts by remember { mutableStateOf<Boolean>(false) }
            if (!loadProducts) {
                this.productsDiverAdapter.allProducts(
                    loadData = {
                        pokemons = it
                        loadProducts = true
                    },
                    errorData = {
                        println("Error en el servicio")

                        loadProducts = true
                    }
                )
            }
            ProductsScreen(    spriteDiverAdapter = spriteDiverAdapter,pokemons = pokemons, onClickProduct = { goToDetail() })
        }
    }

    fun goToDetail() {
        val intent = Intent(this, ProductActivity::class.java)
        startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    spriteDiverAdapter: SpriteDiverAdapter,
    pokemons: List<CaracPokemon>,
    onClickProduct: () -> Unit
) {
    MyApplicationWebServiceTheme {
        Scaffold(
            topBar = {
               TopAppBar(
                   title = { Text(text = stringResource(id = R.string.pokemones)) },
                   colors = TopAppBarDefaults.topAppBarColors(
                       containerColor = MaterialTheme.colorScheme.primary,
                       titleContentColor = MaterialTheme.colorScheme.inversePrimary,
                   )
               )
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(
                        items = pokemons,

                    ) {
                        Card( modifier = Modifier.padding(8.dp)) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                            var pokemonSprite by remember { mutableStateOf<OfficialArtwork?>(null) }


                            LaunchedEffect(it.name) {
                                spriteDiverAdapter.allAbility(
                                    name = it.name,
                                    loadData = {
                                        pokemonSprite = it
                                    },
                                    onError = {
                                        pokemonSprite = OfficialArtwork("")
                                    }
                                )
                            }

                            Row {
                                Text(text = it.name ,textAlign = TextAlign.Center)
                            }

                            ImageWeb(url = pokemonSprite?.frontDefault ?: "" )
                            Button(onClick = { onClickProduct() }) {
                                Text(text = stringResource(id = R.string.go_to_product))
                            }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, widthDp = 360)
@Composable
fun ProductsScreenPreview() {
    ProductsScreen(
        spriteDiverAdapter = SpriteDiverAdapter(),
        pokemons = emptyList(),
        onClickProduct = {}
    )
}
