package dev.darshn.androidtesting.data.remoteResponses

import dev.darshn.androidtesting.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun searchImage(
     @Query("q") searchQuery:String,
     @Query("key") apiKey:String = BuildConfig.API_KEY
    ) :Response<ImageResponse>
}