package com.example.dog

import retrofit2.http.GET


interface DogService {
    @GET("api/breeds/image/random")
    suspend fun getDog(): Dog
}