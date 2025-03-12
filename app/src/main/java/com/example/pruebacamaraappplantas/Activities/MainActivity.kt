package com.example.pruebacamaraappplantas.Activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pruebacamaraappplantas.Adapters.PlantAdapter
import com.example.pruebacamaraappplantas.Api.PlantRequest
import com.example.pruebacamaraappplantas.Api.Planta
import com.example.pruebacamaraappplantas.Api.PlantaPrueba
import com.example.pruebacamaraappplantas.Api.RetrofitClient
import com.example.pruebacamaraappplantas.Api.TrefleRetrofitClient
import com.example.pruebacamaraappplantas.R
import com.example.pruebacamaraappplantas.databinding.ActivityMainBinding
import com.example.pruebacamaraappplantas.fragments.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var imageUri: Uri
    private lateinit var progressBar: ProgressBar
    private lateinit var fabCaptureImage: FloatingActionButton
    private val plantListPrueba = mutableListOf<PlantaPrueba>()
    private var backPressedTime: Long = 0

    private val apiKey = "9McR4b7a7Jtfb2FRU46OwhoYMDu6xkfZBFYD4CwnwlwyVsEsCV"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fabCaptureImage = binding.fabCaptureImage
        progressBar = binding.progressBar

        for (int in 1..4) {
            val plant = PlantaPrueba("Planta $int", 0.5, "Planta $int", R.drawable.blanca)
            plantListPrueba.add(plant)
        }
        switchFragment(HomeFragment())
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> switchFragment(HomeFragment())
                R.id.nav_rankings -> switchFragment(RankingFragment())
                R.id.nav_library -> switchFragment(JardinFragment(plantListPrueba))
                R.id.nav_settings -> switchFragment(SettingsFragment())
                else -> false
            }
        }

        fabCaptureImage.setOnClickListener {
            captureImage()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
        }
    }

    private fun switchFragment(fragment: androidx.fragment.app.Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
        return true
    }

    private fun captureImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "plant_${System.currentTimeMillis()}.jpg")
        imageUri = FileProvider.getUriForFile(this, "${packageName}.provider", photoFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            processImage(imageUri)
        }
    }

    private fun processImage(uri: Uri) {
        try {
            val base64Image = convertImageToBase64(uri)
            if (base64Image != null) {
                identifyPlant(base64Image)
            }
        } catch (e: Exception) {
            Log.e("PlantId", "Error al procesar la imagen", e)
        }
    }

    private fun convertImageToBase64(uri: Uri): String? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            Base64.encodeToString(bytes, Base64.NO_WRAP)
        } catch (e: Exception) {
            Log.e("PlantId", "Error al convertir imagen a Base64", e)
            null
        }
    }

    private fun identifyPlant(base64Image: String) {
        val request = PlantRequest(images = listOf(base64Image))

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) { showLoading() }

            try {
                val response = RetrofitClient.instance.identifyPlant(apiKey, request)
                if (response.isSuccessful && response.body() != null) {
                    val plantData = response.body()!!.result.classification.suggestions
                    if (plantData.isNotEmpty()) {
                        val plantaRecibida = plantData[0]
                        val commonName = getCommonName(plantaRecibida.name) ?: plantaRecibida.name
                        val scientificName = plantaRecibida.name

                        withContext(Dispatchers.Main) {
                            val plant = PlantaPrueba(commonName, plantaRecibida.probability, scientificName, R.drawable.imagenprincipal)
                            plantListPrueba.add(plant)

                            Toast.makeText(applicationContext, "Planta: $commonName\nConfianza: ${(plantaRecibida.probability * 100).toInt()}%", Toast.LENGTH_LONG).show()
                            switchFragment(JardinFragment(plantListPrueba))
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
            } finally {
                withContext(Dispatchers.Main) { hideLoading() }
            }
        }
    }

    private suspend fun getCommonName(scientificName: String): String? {
        val response = TrefleRetrofitClient.trefleApi.getPlantsByScientificName(apiKey, scientificName)
        return if (response.isSuccessful) {
            response.body()?.data?.firstOrNull()?.common_name
        } else {
            Log.e("PlantId", "Error en la respuesta: ${response.errorBody()?.string()}")
            null
        }
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
        binding.overlayView.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
        binding.overlayView.visibility = View.GONE
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        // Si el usuario pulsa el botón de "Atrás" por segunda vez en 2 segundos, salimos
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity() // Cierra todas las actividades y sale de la app
        }
        backPressedTime = System.currentTimeMillis()
    }
}
