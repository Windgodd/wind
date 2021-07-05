package com.universal.wind.movie.view

import android.view.View
import com.universal.wind.R
import com.universal.wind.base.BaseFragment
import com.universal.wind.base.IBaseView
import com.universal.wind.movie.presenter.TVPresenter

class TVFragment: BaseFragment<TVPresenter>(), IBaseView {

    override fun getLayoutId(): Int {
        return R.layout.fragment_sitcon
    }

    override fun initPresenter(): TVPresenter {
        return TVPresenter()
    }

    override fun initView(view: View) {

    }

    override fun initData() {

    }
}