package com.example.beers.features.beers.view

import android.os.Bundle
import android.view.View
import com.example.beers.R
import com.example.beers.core.extensions.loadFromUrl
import com.example.beers.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_beer_detail.*


class BeerDetailFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_beer_detail

    private var beer = BeerView.empty()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = arguments
        if (arguments != null) {
            beer = arguments.getSerializable("beer") as BeerView
            initLayout()
        }
    }

    private fun initLayout() {
        imageDetail.loadFromUrl(beer?.image ?: "")
        titleDetail.text = beer?.name
        authorDetail.text = beer?.description
        contentDetail.text = beer?.tagline
    }
}
