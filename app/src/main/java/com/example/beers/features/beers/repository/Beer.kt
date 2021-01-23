package com.example.beers.features.beers.repository

import com.example.beers.features.beers.view.BeerView

data class Beer(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val image: String
) {

    fun toBeerView(): BeerView {
        return BeerView(id, name, tagline, description, image)
    }
}