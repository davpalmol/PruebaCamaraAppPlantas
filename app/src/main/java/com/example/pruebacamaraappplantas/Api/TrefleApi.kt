package com.example.pruebacamaraappplantas.Api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrefleApi {
    @GET("plants")
    suspend fun getPlantsByScientificName(
        @Query("token") apiKey: String,
        @Query("filter[scientific_name]", encoded = true) scientificName: String
    ): Response<PlantSearchResponse>
}
