package com.example.nikit.populartv.di

import com.example.nikit.populartv.api.TvApi
import com.example.nikit.populartv.service.TvService
import com.example.nikit.populartv.viewModels.TvShowDetailsViewModel
import com.example.nikit.populartv.viewModels.TvShowViewModel
import com.google.gson.GsonBuilder
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = applicationContext {
    bean {
        val retrofit: Retrofit = get()
        retrofit.create(TvApi::class.java)
    }
    factory { TvService(get()) }
}

val viewModelModule = applicationContext {
    viewModel { TvShowViewModel(get()) }
    viewModel { TvShowDetailsViewModel(get()) }
}

val networkModule = applicationContext {

    bean {
        GsonBuilder()
                .setLenient()
                .create()
    }

    bean {
        val baseUrl = "https://api.themoviedb.org/3/"

        Retrofit.Builder()
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create(get()))
                .baseUrl(baseUrl)
                .build()
    }
}