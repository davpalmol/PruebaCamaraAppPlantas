package com.example.pruebacamaraappplantas

data class PlantRequest(
    val images: List<String>,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val similar_images: Boolean = true
)
