package com.example.nikit.populartv.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nikit.populartv.R
import com.example.nikit.populartv.adapter.SimilarTvAdapter
import com.example.nikit.populartv.viewModels.TvShowDetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_tv_show_details.view.*
import org.koin.android.architecture.ext.viewModel

class TvShowDetailsFragment : Fragment() {
    private lateinit var rootView: View

    private var tvId: Int = -1
    private val tagsRecyclerAdapter: SimilarTvAdapter = SimilarTvAdapter()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val tvShowDetailsViewModel by viewModel<TvShowDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            tvId = bundle.getInt(TV_ID, -1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_tv_show_details, container, false)

        rootView.recyclerView.adapter = tagsRecyclerAdapter

        val activity = activity as? AppCompatActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity?.supportActionBar?.title = "Details"
        setHasOptionsMenu(true)

        tvShowDetailsViewModel.loadTvShowDetails(tvId)

        // Creates a horizontal Layout Manager
        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rootView.recyclerView.layoutManager = linearLayoutManager
        val recyclerViewClickListener = object : SimilarTvAdapter.RecyclerViewClickListener {
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

        tvShowDetailsViewModel.tvShowDetails.observe(this, Observer {
            it?.let {
                rootView.title.text = it.original_name
                rootView.overview.text = it.overview
                val url = "https://image.tmdb.org/t/p/w500${it.poster_path}"
                Picasso.get()
                        .load(url)
                        .into(rootView.poster)
            }
        })

        tvShowDetailsViewModel.tvShows.observe(this, Observer {
            it?.let {
                it.results?.let { it1 -> tagsRecyclerAdapter.addAll(it1) }
                tagsRecyclerAdapter.notifyDataSetChanged()
            }
        })

        tvShowDetailsViewModel.progress.observe(this, Observer {
            it?.let { showProgressBar(it) }
        })

        tvShowDetailsViewModel.message.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        tagsRecyclerAdapter.setListener(recyclerViewClickListener)

        return rootView

    }


    fun showProgressBar(show: Boolean) {
        rootView.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        val activity = activity as? AppCompatActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)
        super.onDestroyView()
    }
}
