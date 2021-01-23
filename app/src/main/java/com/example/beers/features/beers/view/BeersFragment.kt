package com.example.beers.features.beers.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beers.R
import com.example.beers.core.exception.Failure
import com.example.beers.core.extensions.failure
import com.example.beers.core.extensions.observe
import com.example.beers.core.functional.DialogCallback
import com.example.beers.core.platform.BaseFragment
import com.example.beers.features.beers.usecase.GetBeersViewModel
import kotlinx.android.synthetic.main.fragment_beers.*
import org.koin.android.scope.currentScope


class BeersFragment : BaseFragment() {
    override fun layoutId() = R.layout.fragment_beers

    private val beerAdapter: BeerAdapter by currentScope.inject()
    private val getBeersViewModel: GetBeersViewModel by currentScope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(getBeersViewModel) {
            observe(beers, ::renderBeersList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        initListeners()
        loadBeers()
    }

    private fun initializeView() {
        beerList.layoutManager = LinearLayoutManager(activity)
        beerList.adapter = beerAdapter

        searchBarProfiles.onActionViewExpanded()
        searchBarProfiles.isFocusable = false
        searchBarProfiles.clearFocus()
        searchBarProfiles.queryHint = "Buscar"
    }

    private fun initListeners() {
        beerAdapter.clickListener = { beerView ->
            val bundle = Bundle()
            bundle.putSerializable("beer", beerView)
            view?.findNavController()
                ?.navigate(R.id.action_beersFragment_to_beerDetailFragment, bundle)
        }

        searchBarProfiles.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                showProgress()
                getBeersViewModel.getBeersFiltered(s)
                return false
            }
        })
    }

    private fun loadBeers() {
        showProgress()
        getBeersViewModel.getBeers()
    }


    private fun renderBeersList(beers: List<BeerView>?) {
        beerAdapter.collection = beers.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.CustomError -> renderFailure(failure.errorCode, failure.errorMessage)
            else -> renderFailure(0, "")
        }
    }

    private fun renderFailure(errorCode: Int, errorMessage: String?) {
        hideProgress()
        showError(errorCode, errorMessage, object : DialogCallback {
            override fun onAccept() {
                loadBeers()
            }

            override fun onDecline() {
                onBackPressed()
            }
        })
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
    }

}
