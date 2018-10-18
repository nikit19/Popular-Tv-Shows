package com.example.nikit.populartv

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.nikit.populartv.fragment.TvShowsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = TvShowsFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .commit()
    }
}
