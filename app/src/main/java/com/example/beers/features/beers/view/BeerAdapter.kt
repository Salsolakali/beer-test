package com.example.beers.features.beers.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beers.R
import com.example.beers.core.extensions.inflate
import com.example.beers.core.extensions.loadFromUrl
import kotlinx.android.synthetic.main.item_beer_row.view.*
import kotlin.properties.Delegates

class BeerAdapter
    : RecyclerView.Adapter<BeerAdapter.ViewHolder>() {
    internal var collection: List<BeerView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (BeerView) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_beer_row))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collection[position], clickListener)
    }

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(beerView: BeerView, clickListener: (BeerView) -> Unit) {
            itemView.tvTitleBeer.text = beerView.name
            itemView.tvDescriptionBeer.text = beerView.description
            itemView.imgBeer.loadFromUrl(beerView.image)
            itemView.setOnClickListener { clickListener(beerView) }
        }
    }
}