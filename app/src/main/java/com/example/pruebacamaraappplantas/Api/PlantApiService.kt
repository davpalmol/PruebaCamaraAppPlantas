package com.example.pruebacamaraappplantas.Api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PlantApiService {
    @POST("api/v3/identification?details=common_names,url,description,taxonomy,rank,gbif_id,inaturalist_id,image,synonyms,edible_parts,watering,propagation_methods&language=es")
    suspend fun identifyPlant(
        @Header("Api-Key") apiKey: String,
        @Body request: PlantRequest
    ): Response<PlantResponse>
}
