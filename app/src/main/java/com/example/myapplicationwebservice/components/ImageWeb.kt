package com.example.myapplicationwebservice.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun ImageWeb(url: String) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(url) {
        bitmap = getImageWeb(url)
    }
    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "",
            modifier = Modifier.size(150.dp)
        )
    }
}

suspend fun getImageWeb(url: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val httpConection = URL(url).openConnection() as HttpURLConnection
            httpConection.doInput = true
            httpConection.connect()
            val input: InputStream = httpConection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            println(e)
            null
        }
    }
}