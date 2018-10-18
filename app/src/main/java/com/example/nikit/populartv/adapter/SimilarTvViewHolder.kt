package com.example.nikit.populartv.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.nikit.populartv.model.Results
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_similar_tv_show.view.*

class SimilarTvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Results, clickListener: SimilarTvAdapter.RecyclerViewClickListener?) {
        val url = "https://image.tmdb.org/t/p/w500${item.backdrop_path}"
        Picasso.get()
                .load(url)
                .into(itemView.imageTv)

        itemView.setOnClickListener {
            clickListener?.onClick(item.id)
        }
    }
}