package com.universal.wind.movie.ui

import android.os.Bundle

class TVFragment:BaseVideoFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        videoTid = "/search.php?searchtype=5&tid=2"
        super.onCreate(savedInstanceState)
    }
}