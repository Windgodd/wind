package com.universal.wind.movie.view

import android.os.Bundle
import android.view.View
import com.universal.wind.R
import com.universal.wind.base.BaseFragment
import com.universal.wind.base.IBaseView
import com.universal.wind.movie.presenter.AnimPresenter

class AnimFragment: BaseVideoFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        videoTid = "/search.php?searchtype=5&tid=4"
        super.onCreate(savedInstanceState)
    }
}