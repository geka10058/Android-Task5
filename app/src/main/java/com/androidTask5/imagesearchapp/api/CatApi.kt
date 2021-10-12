package com.androidTask5.imagesearchapp.api

import com.androidTask5.imagesearchapp.BuildConfig
import com.androidTask5.imagesearchapp.data.CatPhoto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatApi {

    companion object {
        const val BASE_URL = "https://api.thecatapi.com/v1/"
        const val ACCESS_KEY = BuildConfig.ACCESS_KEY//"15a8dfdb-a893-4015-a8d3-3bd20d511812"
    }

    @Headers("Content-Type: application/json", "x-api-key: $ACCESS_KEY")
    @GET("images/search")
    suspend fun searchPhotos(
        //@Query("query") query: String,
        @Query("order") order: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<CatPhoto>//CatResponse
}