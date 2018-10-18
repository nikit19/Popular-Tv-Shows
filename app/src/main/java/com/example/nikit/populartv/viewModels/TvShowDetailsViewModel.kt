package com.example.nikit.populartv.viewModels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nikit.populartv.model.TvShowDetails
import com.example.nikit.populartv.model.TvShows
import com.example.nikit.populartv.service.TvService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TvShowDetailsViewModel(private val tvService: TvService) : ViewModel() {

    private var disposable: Disposable? = null
    val tvShowDetails = MutableLiveData<TvShowDetails>()
    val tvShows = MutableLiveData<TvShows>()
    val progress = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun loadTvShowDetails(tvId: Int) {
        disposable = tvService.getTvShowDetails(tvId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    progress.value = true
                }.doFinally {
                }.subscribe(
                        {
                            tvShowDetails.value = it
                            loadSimilarTvShows(tvId)
                        },
                        { error ->
                            message.value = error.message
                        }
                )
    }

    fun loadSimilarTvShows(tvId: Int) {
        disposable = tvService.getSimilarTvShows(tvId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                }.doFinally {
                    progress.value = false
                }.subscribe(
                        {
                            tvShows.value = it
                        },
                        { error ->
                            message.value = error.message
                        }
                )
    }
}