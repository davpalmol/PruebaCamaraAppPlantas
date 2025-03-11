package com.example.pruebacamaraappplantas.Api

data class PlantSearchResponse(
    val data: List<PlantData>,
    val links: Links,
    val meta: Meta
)

data class PlantData(
    val id: Int,
    val slug: String,
    val scientificName: String,
    val common_name: String?,
    val family: String?,
    val genus: String?,
    val imageUrl: String?,
    val rank: String,
    val status: String,
    val synonyms: List<String>?,
    val year: Int,
    val links: PlantLinks
)

data class Links(
    val first: String,
    val last: String,
    val self: String
)

data class Meta(
    val total: Int
)

data class PlantLinks(
    val genus: String,
    val plant: String,
    val self: String
)
