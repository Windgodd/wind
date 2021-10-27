package com.universal.wind.movie.ui

import android.os.Bundle

class MovieFragment:BaseVideoFragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        videoTid = "/search.php?searchtype=5&tid=1"
        super.onCreate(savedInstanceState)
    }


}