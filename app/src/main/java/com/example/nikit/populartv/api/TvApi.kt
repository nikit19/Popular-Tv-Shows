package com.example.nikit.populartv.api

import com.example.nikit.populartv.model.TvShowDetails
import com.example.nikit.populartv.model.TvShows
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvApi {

    @GET("tv/popular")
    fun getPopularTvShows(@Query("api_key") apiKey: String, @Query("page") page: Int):
            Observable<TvShows>

    @GET("tv/{tvId}")
    fun getTvShowDetails(@Path("tvId") tvId: Int, @Query("api_key") apiKey: String):
            Observable<TvShowDetails>

    @GET("tv/{tvId}/similar")
    fun getSimilarTvShows(@Path("tvId") tvId: Int, @Query("api_key") apiKey: String):
            Observable<TvShows>
}
