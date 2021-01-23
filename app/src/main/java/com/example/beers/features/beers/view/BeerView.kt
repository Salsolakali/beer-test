package com.example.beers.features.beers.view

import java.io.Serializable

data class BeerView(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val image: String
) : Serializable {
    companion object {
        fun empty(): BeerView = BeerView(0, "", "", "", "")
    }
}