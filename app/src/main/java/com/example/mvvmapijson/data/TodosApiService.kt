package com.example.mvvmapijson.data

import com.example.mvvmapijson.model.Todo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://jsonplaceholder.typicode.com"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface TodosApiService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>
}

object TodosApi {
    val retrofitService: TodosApiService by lazy {
        retrofit.create(TodosApiService::class.java)
    }
}