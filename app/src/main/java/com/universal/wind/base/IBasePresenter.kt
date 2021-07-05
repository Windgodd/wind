package com.universal.wind.base

interface IBasePresenter {
    fun attachView(view: IBaseView)
    fun detachView()
}