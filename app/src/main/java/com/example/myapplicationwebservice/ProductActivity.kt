package com.example.myapplicationwebservice

import android.content.Intent
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplicationwebservice.components.ImageWeb
import com.example.myapplicationwebservice.services.driverAdapters.DescriptionDiverAdapter
import com.example.myapplicationwebservice.services.driverAdapters.SpriteDiverAdapter
import com.example.myapplicationwebservice.services.models.EvolutionChain
import com.example.myapplicationwebservice.services.models.FlavorTextAndLanguage
import com.example.myapplicationwebservice.services.models.FlavorTextEntries
import com.example.myapplicationwebservice.services.models.OfficialArtwork
import com.example.myapplicationwebservice.services.models.Other
import com.example.myapplicationwebservice.services.models.PokemonResponse
import com.example.myapplicationwebservice.services.models.Sprites
import com.example.myapplicationwebservice.ui.theme.MyApplicationWebServiceTheme

class ProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val spriteDiverAdapter by lazy { SpriteDiverAdapter() }
        val descriptionDiverAdapter by lazy { DescriptionDiverAdapter() }



        setContent {

            val idPokemon = intent.getStringExtra("id")

            val regionPok = when {
                idPokemon.toString().toInt() < 152 -> "Kanto"
                idPokemon.toString().toInt() in 152..251 -> "Johto"
                idPokemon.toString().toInt() in 252..386 -> "Hoenn"
                idPokemon.toString().toInt() in 387..493 -> "Sinnoh"
                idPokemon.toString().toInt() in 494..649 -> "Unova"
                idPokemon.toString().toInt() in 650..721 -> "Kalos"
                idPokemon.toString().toInt() in 722..809 -> "Alola"
                idPokemon.toString().toInt() in 810..905 -> "Galar"
                idPokemon.toString().toInt() in 906..1010 -> "Paldea"

                else -> "esta mal"
            }
            var pokemonSprite by remember { mutableStateOf<PokemonResponse>( PokemonResponse(
                emptyList(),0,"",Sprites(Other(OfficialArtwork(""))), emptyList(), emptyList()))}
            var loadProducts by remember { mutableStateOf<Boolean>(false) }
            var description by remember { mutableStateOf<FlavorTextEntries>(FlavorTextEntries(EvolutionChain(""), emptyList())) }
            var descriptionText by remember { mutableStateOf<String>("") }
            var iterator = 0;

            if (!loadProducts) {
                spriteDiverAdapter.allAbility(
                    name = idPokemon.toString(),

                    loadData = {
                        pokemonSprite = it
                        loadProducts = true
                        println(it)
                    },
                    onError = {
                        println("error")
                        loadProducts = true
                    }
                )
                descriptionDiverAdapter.allDescription(
                    id = idPokemon.toString(),
                    loadData = {
                        description = it
                        println(description)
                    },
                    onError = {}
                )
            }

            if (description.flavorTextEntries.isNotEmpty()) {
                do {

                    if (description.flavorTextEntries[iterator].language.name == "es") {
                        descriptionText = description.flavorTextEntries[iterator].flavorText
                        println(descriptionText)
                        break
                    }
                    iterator++
                } while (descriptionText.isEmpty() || iterator < description.flavorTextEntries.size)
            println(idPokemon)
                val scrollState = rememberScrollState()
     pokemonScreen(pokemonSprite = pokemonSprite,region = regionPok,description = descriptionText,scrollState = scrollState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pokemonScreen(pokemonSprite: PokemonResponse,region: String,description: String,scrollState: ScrollState) {
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
        ){ innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                val PokemonFont = FontFamily(
                    Font(R.font.pokemon_solid, FontWeight.Light)

                )
                val offset = Offset(3.0f, 15.0f)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Añade padding externo para darle espacio a la tarjeta
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .padding(16.dp), // Añade padding interno para contenido dentro de la tarjeta
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically // Alinea verticalmente al centro
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos en la columna
                        verticalArrangement = Arrangement.Center // Asegura que los elementos estén centrados verticalmente
                    ) {
                        Text(
                            text = pokemonSprite.name,
                            textAlign = TextAlign.Center, // Añade estilo al texto
                            style = TextStyle( fontFamily = PokemonFont, fontSize = 28.sp, color = colorResource( R.color.yellow),
                                shadow = Shadow(color = colorResource(R.color.blue),offset = offset, blurRadius = 20f)
                            )                            )

                        Spacer(modifier = Modifier.height(8.dp)) // Añade espacio entre el texto y la imagen

                        ImageWeb(url = pokemonSprite.sprites.other.officialArtwork.frontDefault)

                        Spacer(modifier = Modifier.height(8.dp)) // Añade espacio entre el texto y la imagen

                        Text(
                            text = description,
                            textAlign = TextAlign.Center, // Añade estilo al texto
                            style = TextStyle( fontFamily = PokemonFont, fontSize = 18.sp,
                                shadow = Shadow(color = colorResource(R.color.yellow),offset = offset, blurRadius = 20f)
                            )                            )
                    }
                }
                var statsNameAll = pokemonSprite.stats.map { it.stat.name }.toString()
                statsNameAll = statsNameAll.replace("[", "").replace("]", "")
                var stats = statsNameAll.split(",").toTypedArray()

                var statsPointAll = pokemonSprite.stats.map { it.baseStat }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .padding(16.dp)
                        .wrapContentWidth()
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stats[0],style = TextStyle( fontFamily = PokemonFont, fontSize = 20.sp, color = colorResource( R.color.yellow),
                            shadow = Shadow(color = colorResource(R.color.blue),offset = offset, blurRadius = 20f)
                        )        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = statsPointAll[0].toString(),
                            style = TextStyle( fontFamily = PokemonFont, fontSize = 18.sp,
                                shadow = Shadow(color = colorResource(R.color.yellow),offset = offset, blurRadius = 20f)))
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stats[1],style = TextStyle( fontFamily = PokemonFont, fontSize = 20.sp, color = colorResource( R.color.yellow),
                            shadow = Shadow(color = colorResource(R.color.blue),offset = offset, blurRadius = 20f)
                        )        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = statsPointAll[1].toString(),
                            style = TextStyle( fontFamily = PokemonFont, fontSize = 18.sp,
                                shadow = Shadow(color = colorResource(R.color.yellow),offset = offset, blurRadius = 20f)))
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stats[2],style = TextStyle( fontFamily = PokemonFont, fontSize = 20.sp, color = colorResource( R.color.yellow),
                            shadow = Shadow(color = colorResource(R.color.blue),offset = offset, blurRadius = 20f)
                        )        )
                        Spacer(modifier = Modifier.height(8.dp),)
                        Text(text = statsPointAll[2].toString(),
                            style = TextStyle( fontFamily = PokemonFont, fontSize = 18.sp,
                                shadow = Shadow(color = colorResource(R.color.yellow),offset = offset, blurRadius = 20f)))
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .padding(16.dp)
                        .wrapContentWidth()
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stats[3],style = TextStyle( fontFamily = PokemonFont, fontSize = 14.sp, color = colorResource( R.color.yellow),
                            shadow = Shadow(color = colorResource(R.color.blue),offset = offset, blurRadius = 20f)
                        )        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = statsPointAll[3].toString(),
                            style = TextStyle( fontFamily = PokemonFont, fontSize = 18.sp,
                                shadow = Shadow(color = colorResource(R.color.yellow),offset = offset, blurRadius = 20f) ))
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stats[4],style = TextStyle( fontFamily = PokemonFont, fontSize = 14.sp, color = colorResource( R.color.yellow),
                            shadow = Shadow(color = colorResource(R.color.blue),offset = offset, blurRadius = 20f)
                        )        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = statsPointAll[4].toString(),
                            style = TextStyle( fontFamily = PokemonFont, fontSize = 18.sp,
                                shadow = Shadow(color = colorResource(R.color.yellow),offset = offset, blurRadius = 20f)))
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stats[5],style = TextStyle( fontFamily = PokemonFont, fontSize = 20.sp, color = colorResource( R.color.yellow),
                            shadow = Shadow(color = colorResource(R.color.blue),offset = offset, blurRadius = 20f)
                        )        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = statsPointAll[5].toString(),
                            style = TextStyle( fontFamily = PokemonFont, fontSize = 18.sp,
                                shadow = Shadow(color = colorResource(R.color.yellow),offset = offset, blurRadius = 20f)))
                    }
                }





                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Añade padding externo para darle espacio a la tarjeta
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .padding(16.dp), // Añade padding interno para contenido dentro de la tarjeta
                    verticalAlignment = Alignment.CenterVertically // Alinea verticalmente al centro
                ) {

                        Text(
                            text = "Region: ",
                            textAlign = TextAlign.Left, // Añade estilo al texto
                            style = TextStyle( fontFamily = PokemonFont, fontSize = 24.sp, color = colorResource( R.color.yellow),
                                    shadow = Shadow(color = colorResource(R.color.blue),offset = offset, blurRadius = 20f)
                                )
                        )
                        Spacer(modifier = Modifier.height(8.dp)) // Añade espacio entre el texto y la imagen
                        Text(
                            text = region,
                            textAlign = TextAlign.Left, // Añade estilo al texto
                            style = TextStyle(fontFamily = PokemonFont, fontSize = 18.sp)
                        )


                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Añade padding externo para darle espacio a la tarjeta
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .padding(16.dp), // Añade padding interno para contenido dentro de la tarjeta
                    verticalAlignment = Alignment.CenterVertically // Alinea verticalmente al centro
                ) {

                    Text(
                        text = "Habilidades: ",
                        textAlign = TextAlign.Left, // Añade estilo al texto
                        style = TextStyle( fontFamily = PokemonFont, fontSize = 24.sp, color = colorResource( R.color.yellow),
                            shadow = Shadow(color = colorResource(R.color.blue),offset = offset, blurRadius = 20f)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp)) // Añade espacio entre el texto y la imagen
                    var abilities = pokemonSprite.abilities.map {it.ability.name}.toString()
                    abilities = abilities.replace("[", "").replace("]", "")
                    Text(
                        text = abilities,
                        textAlign = TextAlign.Left, // Añade estilo al texto
                        style = TextStyle(fontFamily = PokemonFont, fontSize = 18.sp)
                    )


                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Añade padding externo para darle espacio a la tarjeta
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .padding(16.dp), // Añade padding interno para contenido dentro de la tarjeta
                    verticalAlignment = Alignment.CenterVertically // Alinea verticalmente al centro
                ) {

                    Text(
                        text = "Tipos: ",
                        textAlign = TextAlign.Left, // Añade estilo al texto
                        style = TextStyle( fontFamily = PokemonFont, fontSize = 28.sp, color = colorResource( R.color.yellow),
                            shadow = Shadow(color = colorResource(R.color.blue),offset = offset, blurRadius = 20f)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp)) // Añade espacio entre el texto y la imagen
                    var types = pokemonSprite.types.map {when (it.type.name){
                        "grass" -> "Planta"
                        "poison" -> "Veneno"
                        "rock" -> "roca"
                        "fire" -> "Fuego"
                        "water" -> "Agua"
                        "bug" -> "Bicho"
                        "normal" -> "Normal"
                        "electric" -> "Electrico"
                        "ground" -> "Tierra"
                        "fairy" -> "Hada"
                        "fighting" -> "Lucha"
                        "psychic" -> "Psiquico"
                        "ghost" -> "Fantasma"
                        "ice" -> "hielo"
                        "dragon" -> "Dragon"
                        else -> "no"
                    } }.toString()
                    types = types.replace("[", "").replace("]", "")
                    var typesArray = 
                    Text(
                        text = types,
                        textAlign = TextAlign.Left, // Añade estilo al texto
                        style = TextStyle(fontFamily = PokemonFont, fontSize = 18.sp)
                    )


                }

            }
        }
    }
}
}



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyApplicationWebServiceTheme {
//        Greeting("Android")
//    }
//}