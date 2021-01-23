package com.example.beers.features.beers.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


internal interface BeersApi {

    @GET("beers")
    fun getBeers(): Call<List<BeerEntity>>

    @GET("beers")
    fun getBeersFiltered(@Query("beer_name") s: String): Call<List<BeerEntity>>
}
