package com.example.pruebacamaraappplantas

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
    val probability: Double
)
