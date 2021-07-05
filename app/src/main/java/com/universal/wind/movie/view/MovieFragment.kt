package com.universal.wind.movie.view

import android.view.View
import com.universal.wind.R
import com.universal.wind.base.BaseFragment
import com.universal.wind.base.IBaseView
import com.universal.wind.movie.presenter.MoviePresenter

class MovieFragment: BaseFragment<MoviePresenter>(), IBaseView {

    override fun getLayoutId(): Int {
        return R.layout.fragment_movie
    }

    override fun initPresenter(): MoviePresenter {
        return MoviePresenter()
    }

    override fun initView(view: View) {

    }

    override fun initData() {

    }
}