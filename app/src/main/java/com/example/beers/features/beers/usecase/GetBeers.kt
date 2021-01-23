package com.example.beers.features.beers.usecase

import com.example.beers.core.interactor.UseCase
import com.example.beers.features.beers.repository.Beer
import com.example.beers.features.beers.repository.BeersRepository

class GetBeers(private val beersRepository: BeersRepository) :
    UseCase<List<Beer>, GetBeers.Params>() {
    override suspend fun run(params: Params) = beersRepository.getBeers()
    class Params
}