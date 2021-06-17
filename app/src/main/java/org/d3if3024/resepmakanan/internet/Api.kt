package org.d3if3024.resepmakanan.internet

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.spoonacular.com/"

object Api {

    val instance: ResepApi by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.create(ResepApi::class.java)
    }
}

interface ResepApi {
    @GET("recipes/complexSearch?")
    fun get(
            @Query("query") query: String,
            @Query("apiKey") key: String = "3334839dff7b47e7a9afb51614f571b9"
    ): Call<ApiResponse>

    @GET("recipes/{id}/information?")
    fun getDetails(
            @Path("id") id: Int,
            @Query("apiKey") key: String = "3334839dff7b47e7a9afb51614f571b9"
    ): Call<DetailResponse>
}

enum class ApiStatus { LOADING, SUCCESS, FAILED, NOTFOUND }

