@file:Suppress("UNCHECKED_CAST")

package com.universal.wind.base

open class BaseFragmentPresenter<T: IBaseView>: IBasePresenter {

    protected var mView:T? = null

    /**
     * 跟视图绑定
     */
    override fun attachView(view: IBaseView) {
        this.mView = view as T
    }

    /**
     * 解绑
     */
    override fun detachView() {
        mView = null
    }


}