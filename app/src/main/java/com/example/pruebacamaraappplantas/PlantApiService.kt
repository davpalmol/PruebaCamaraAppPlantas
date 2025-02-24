package com.example.pruebacamaraappplantas

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PlantApiService {
    @POST("api/v3/identification")
    suspend fun identifyPlant(
        @Header("Api-Key") apiKey: String,
        @Body request: PlantRequest
    ): Response<PlantResponse>
}
