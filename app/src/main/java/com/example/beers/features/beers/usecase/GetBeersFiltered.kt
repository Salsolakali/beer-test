package com.example.beers.features.beers.usecase

import com.example.beers.core.interactor.UseCase
import com.example.beers.features.beers.repository.Beer
import com.example.beers.features.beers.repository.BeersRepository


class GetBeersFiltered(private val beersRepository: BeersRepository) :
    UseCase<List<Beer>, GetBeersFiltered.Params>() {

    override suspend fun run(params: Params) = beersRepository.getBeersFiltered(params.s)
    data class Params(val s: String)

}