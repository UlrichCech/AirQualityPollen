package com.example.android.airqualitypollen.business.airquality.boundary

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://api.ambeedata.com"

//private const val AMBEE_API_KEY = "eRGsr21sQP8r1xOLvEkXW6sk16XYCCFNM3Y7dgec"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()


interface AmbeeApiService {

    @Headers(
        "Content-type: application/json",
        "Accept: application/json"
    )
    @GET("/latest/by-lat-lng")
    suspend fun getAirQualityForCurrentLocation(@Query("lat") lat : String,
                                                @Query("lng") lng : String,
                                                @Header("x-api-key") apiKey: String) : AirQualityResult

    @Headers(
        "Content-type: application/json",
        "Accept: application/json"
    )
    @GET("/latest/pollen/by-lat-lng")
    suspend fun getPollenForCurrentLocation(@Query("lat") lat : String,
                                            @Query("lng") lng : String,
                                            @Header("x-api-key") apiKey: String) : PollenResult
}


object AmbeeApi {
    val RETROFIT_SERVICE : AmbeeApiService by lazy {
        retrofit.create(AmbeeApiService::class.java)
    }
}