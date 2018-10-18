package com.example.nikit.populartv.viewModels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.nikit.populartv.model.TvShows
import com.example.nikit.populartv.service.TvService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TvShowViewModel(private val tvService: TvService) : ViewModel() {
    private var disposable: Disposable? = null
    val tvShows = MutableLiveData<TvShows>()
    val progress = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    var page = 1

    fun loadTvShows() {
        disposable = tvService.getPopularTvShows(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    progress.value = true
                }.doFinally {
                    progress.value = true
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