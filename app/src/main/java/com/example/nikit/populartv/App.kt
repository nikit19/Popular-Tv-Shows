package com.example.nikit.populartv

import android.support.multidex.MultiDexApplication
import com.example.nikit.populartv.di.apiModule
import com.example.nikit.populartv.di.networkModule
import com.example.nikit.populartv.di.viewModelModule
import org.koin.android.ext.android.startKoin

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(apiModule, networkModule, viewModelModule))
    }
}
