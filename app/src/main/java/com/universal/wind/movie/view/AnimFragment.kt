package com.universal.wind.movie.view

import android.view.View
import com.universal.wind.R
import com.universal.wind.base.BaseFragment
import com.universal.wind.base.IBaseView
import com.universal.wind.movie.presenter.AnimPresenter

class AnimFragment: BaseFragment<AnimPresenter>(),IBaseView {


    override fun getLayoutId(): Int {
        return R.layout.fragment_anim
    }

    override fun initPresenter(): AnimPresenter {
        return AnimPresenter()
    }

    override fun initView(view: View) {

    }

    override fun initData() {

    }
}