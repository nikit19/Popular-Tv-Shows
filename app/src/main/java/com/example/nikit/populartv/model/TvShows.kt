package com.example.nikit.populartv.model

data class TvShows(val page: Int? = null,
                   val total_results: Int? = null,
                   val total_pages: Int? = null,
                   val results:List<Results>? =null)