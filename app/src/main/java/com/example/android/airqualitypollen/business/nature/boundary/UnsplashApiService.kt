package com.example.android.airqualitypollen.business.nature.boundary

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://api.unsplash.com"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()


interface UnsplashApiService {

    @Headers(
        "Content-type: application/json",
        "Accept: application/json"
    )
    @GET("/search/photos/")
//    client_id=j90AGyvGRe5nRUt-wxAQIQGClF7SVBQxc2sevFdTQwA&query=%22nature%20images%22&per_page=1&orientation=landscape&order_by=latest
    suspend fun searchLatestPicture(@Query("client_id") clientId : String,
                                    @Query("query") query : String,
                                    @Query("per_page") page: Int,
                                    @Query("orientation") orientation : String,
                                    @Query("order_by") latest : String) : SearchResult

}


object UnsplashApi {
    val RETROFIT_SERVICE : UnsplashApiService by lazy {
        retrofit.create(UnsplashApiService::class.java)
    }
}