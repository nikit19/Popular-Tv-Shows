package com.example.nikit.populartv.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.nikit.populartv.model.Results
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_tv_show.view.*

class PopularTvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val name = itemView.name

    fun bind(item: Results, clickListener: PopularTvAdapter.RecyclerViewClickListener?) {
        name.text = item.name
        val url = "https://image.tmdb.org/t/p/w500${item.backdrop_path}"
        Picasso.get()
                .load(url)
                // .placeholder(R.drawable.placeholder)
                .into(itemView.imageTv)

        itemView.setOnClickListener {
            clickListener?.onClick(item.id)
        }
    }
}