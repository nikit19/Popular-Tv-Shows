package com.example.nikit.populartv.service

import com.example.nikit.populartv.BuildConfig
import com.example.nikit.populartv.api.TvApi
import com.example.nikit.populartv.model.TvShowDetails
import com.example.nikit.populartv.model.TvShows
import io.reactivex.Observable

class TvService(private val tvApi: TvApi) {

    fun getPopularTvShows(page: Int): Observable<TvShows> {
        return tvApi.getPopularTvShows(BuildConfig.API_KEY, page)
    }

    fun getTvShowDetails(tvId: Int): Observable<TvShowDetails> {
        return tvApi.getTvShowDetails(tvId, BuildConfig.API_KEY)
    }

    fun getSimilarTvShows(tvId: Int): Observable<TvShows> {
        return tvApi.getSimilarTvShows(tvId, BuildConfig.API_KEY)
    }
}