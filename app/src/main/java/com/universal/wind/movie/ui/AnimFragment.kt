package com.universal.wind.movie.ui

import android.os.Bundle

class AnimFragment: BaseVideoFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        videoTid = "/search.php?searchtype=5&tid=4"
        super.onCreate(savedInstanceState)
    }
}