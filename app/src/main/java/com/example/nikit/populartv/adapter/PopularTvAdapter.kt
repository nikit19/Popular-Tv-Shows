package com.example.nikit.populartv.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.nikit.populartv.R
import com.example.nikit.populartv.model.Results
import java.util.ArrayList


class PopularTvAdapter : RecyclerView.Adapter<PopularTvViewHolder>() {
    private var clickListener: RecyclerViewClickListener? = null
    private val tvShows = ArrayList<Results>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularTvViewHolder {
        return PopularTvViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false))
    }

    fun addData(data: List<Results>) {
        val positionStart = tvShows.size + 1
        this.tvShows.addAll(data)
        notifyItemRangeInserted(positionStart, tvShows.size)
    }

    fun setListener(listener: RecyclerViewClickListener) {
        clickListener = listener
    }

    fun addAll(tagsList: List<Results>) {
        if (tvShows.isNotEmpty())
            this.tvShows.clear()
        this.tvShows.addAll(tagsList)
    }

    override fun onBindViewHolder(holder: PopularTvViewHolder, position: Int) {
        holder.bind(tvShows[position], clickListener)

    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    interface RecyclerViewClickListener {
        fun onClick(tvId: Int?)
    }
}



