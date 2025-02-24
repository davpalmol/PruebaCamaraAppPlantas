package com.example.pruebacamaraappplantas

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

class MainActivity : AppCompatActivity() {

    private lateinit var imageUri: Uri
    private val apiKey = "9McR4b7a7Jtfb2FRU46OwhoYMDu6xkfZBFYD4CwnwlwyVsEsCV"  // Reemplaza con tu API Key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            captureImage()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
        }
    }

    private fun captureImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "plant.jpg")
        imageUri = FileProvider.getUriForFile(this, "${packageName}.provider", photoFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            processImage(imageUri)
        }
    }

    private fun processImage(uri: Uri) {
        try {
            val imageFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "plant.jpg")
            val base64Image = convertImageToBase64(imageFile)

            if (base64Image != null) {
                identifyPlant(base64Image)
            } else {
                Log.e("PlantId", "Error al convertir la imagen a Base64")
                Toast.makeText(this, "Error al convertir la imagen", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Log.e("PlantId", "Error al procesar la imagen", e)
        }
    }

    private fun convertImageToBase64(file: File): String? {
        return try {
            val inputStream = FileInputStream(file)
            val bytes = inputStream.readBytes()
            Base64.encodeToString(bytes, Base64.NO_WRAP)
        } catch (e: Exception) {
            Log.e("PlantId", "Error al convertir imagen a Base64", e)
            null
        }
    }

    private fun identifyPlant(base64Image: String) {
        val request = PlantRequest(images = listOf(base64Image))

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.instance.identifyPlant(apiKey, request)

                if (response.isSuccessful && response.body() != null) {
                    val plantData = response.body()!!.result.classification.suggestions

                    if (plantData.isNotEmpty()) {
                        val plant = plantData[0]  // Tomamos la mejor coincidencia
                        val plantName = plant.name
                        val confidence = plant.probability

                        Log.d("PlantId", "Planta identificada: $plantName, Confianza: ${confidence * 100}%")

                        withContext(Dispatchers.Main) {
                            Toast.makeText(applicationContext, "Planta: $plantName\nConfianza: ${(confidence * 100).toInt()}%", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(applicationContext, "No se pudo identificar la planta", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Log.e("PlantId", "Error en la respuesta: ${response.errorBody()?.string()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "Error en la identificación", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("PlantId", "Error al procesar la imagen", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Error al procesar la imagen", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
@Composable
fun PlantApp(plants: MutableList<String>, captureImage: () -> Unit) {
    var newPlant by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mi Biblioteca de Plantas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                captureImage() // Llamamos la función pasada como parámetro
            }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            TextField(
                value = newPlant,
                onValueChange = { newPlant = it },
                label = { Text("Nombre de la planta") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            PlantList(plants)
        }
    }
}

@Composable
fun PlantList(plants: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(plants) { plant ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = 4.dp
            ) {
                Text(
                    text = plant,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlantList() {
    PlantApp(plants = mutableListOf("Cactus", "Rosa", "Orquídea"),
        captureImage = {})
}
