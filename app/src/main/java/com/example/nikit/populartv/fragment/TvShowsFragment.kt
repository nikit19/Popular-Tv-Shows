package com.example.nikit.populartv.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikit.populartv.R
import com.example.nikit.populartv.adapter.PopularTvAdapter
import kotlinx.android.synthetic.main.fragment_tv_shows.view.*
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.nikit.populartv.viewModels.TvShowViewModel
import org.koin.android.architecture.ext.viewModel

const val TV_ID: String = "TV_ID"

class TvShowsFragment : Fragment() {
    private lateinit var rootView: View

    private val tvRecyclerAdapter: PopularTvAdapter = PopularTvAdapter()
    private val tvShowViewModel by viewModel<TvShowViewModel>()
    var lastPage: Int? = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_tv_shows, container, false)
        rootView.recyclerView.adapter = tvRecyclerAdapter

        tvShowViewModel.loadTvShows()

        val activity = activity as? AppCompatActivity
        activity?.supportActionBar?.title = "TV Shows"

        rootView.recyclerView.layoutManager = GridLayoutManager(context, 2)

        val recyclerViewClickListener = object : PopularTvAdapter.RecyclerViewClickListener {
            override fun onClick(tvId: Int?) {
                val fragment = TvShowDetailsFragment()
                val bundle = Bundle()
                tvId?.let { bundle.putInt(TV_ID, it) }
                fragment.arguments = bundle
                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.frameContainer, fragment)
                        ?.addToBackStack(null)
                        ?.commit()
            }
        }

        rootView.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->

            if (view.getChildAt(view.childCount - 1) != null) {
                if (scrollY >= view.getChildAt(view.childCount - 1).measuredHeight - view.measuredHeight && scrollY > oldScrollY) {
                    if (tvShowViewModel.page <= lastPage!!) {
                        tvShowViewModel.page++
                        tvShowViewModel.loadTvShows()
                    }
                }
            }
        })

        tvShowViewModel.progress.observe(this, Observer {
            it?.let { showProgressBar(it) }
        })

        tvShowViewModel.tvShows.observe(this, Observer {
            it?.let {
                lastPage = it.total_pages

                if (tvShowViewModel.page == 1) {
                    it.results?.let { it1 -> tvRecyclerAdapter.addAll(it1) }
                    tvRecyclerAdapter.notifyDataSetChanged()

                } else
                    it.results?.let { it1 -> tvRecyclerAdapter.addData(it1) }
            }
        })

        tvShowViewModel.message.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        tvRecyclerAdapter.setListener(recyclerViewClickListener)
        return rootView
    }

    fun showProgressBar(show: Boolean) {
        rootView.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

}