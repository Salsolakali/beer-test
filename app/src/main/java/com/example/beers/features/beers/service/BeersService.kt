package com.example.beers.features.beers.service

import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
class BeersService(retrofit: Retrofit) : BeersApi {
    override fun getBeersFiltered(s: String): Call<List<BeerEntity>> {
        return beersApi.getBeersFiltered(s)
    }

    override fun getBeers(): Call<List<BeerEntity>> {
        return beersApi.getBeers()
    }

    private val beersApi by lazy { retrofit.create(BeersApi::class.java) }
}