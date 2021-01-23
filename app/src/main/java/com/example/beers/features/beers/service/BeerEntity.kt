package com.example.beers.features.beers.service

import com.example.beers.features.beers.repository.Beer
import com.google.gson.annotations.SerializedName


data class BeerEntity(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("first_brewed") val first_brewed: String,
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("abv") val abv: String,
    @SerializedName("ibu") val ibu: String,
    @SerializedName("target_fg") val target_fg: String,
    @SerializedName("target_og") val target_og: String,
    @SerializedName("ebc") val ebc: String,
    @SerializedName("srm") val srm: String,
    @SerializedName("ph") val ph: String,
    @SerializedName("attenuation_level") val attenuation_level: String
) {
    fun toBeer(): Beer {
        return Beer(id, name, tagline, description, image_url)
    }
}