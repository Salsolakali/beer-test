package com.example.beers.features.beers.usecase


import androidx.lifecycle.MutableLiveData
import com.example.beers.core.platform.BaseViewModel
import com.example.beers.features.beers.repository.Beer
import com.example.beers.features.beers.view.BeerView


class GetBeersViewModel(
    private val getBeers: GetBeers,
    private val getBeersFiltered: GetBeersFiltered
) : BaseViewModel() {

    var beers: MutableLiveData<List<BeerView>> = MutableLiveData()
    var beersViews: List<BeerView> = listOf()

    fun getBeers() = getBeers.invoke(
        GetBeers.Params()
    ) {
        it.either(::handleFailure, ::handleBeersList)
    }

    fun getBeersFiltered(s: String) = getBeersFiltered.invoke(
        GetBeersFiltered.Params(s)
    ) {
        it.either(::handleFailure, ::handleBeersList)
    }


    private fun handleBeersList(beers: List<Beer>) {
        beersViews = beers.map {
            it.toBeerView()
        }
        this.beers.value = beersViews
    }
}
