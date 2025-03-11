package com.example.pruebacamaraappplantas.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TrefleRetrofitClient {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://trefle.io/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val trefleApi = retrofit.create(TrefleApi::class.java)
}
