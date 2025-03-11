package com.example.pruebacamaraappplantas.Api

import android.net.Uri

data class PlantResponse(
    val result: ResultData
)

data class ResultData(
    val classification: Classification
)

data class Classification(
    val suggestions: List<Suggestion>
)

data class Suggestion(
    val name: String,
    val probability: Double,
    val details: Detail
)

data class Detail(
    val common_names: List<String>?
)

data class Planta(
    var nombreCientifico: String,
    val probabilidad: Double,
    var nombre: String,
    var imageUri: Uri? = null
)

data class PlantaPrueba(
    var nombreCientifico: String,
    val probabilidad: Double,
    var nombre: String,
    var imageUri: Int
)