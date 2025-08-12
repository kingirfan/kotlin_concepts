package com.example.first_app.json_retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

val retrofitService: ApiService = Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)


interface ApiService {

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse
}