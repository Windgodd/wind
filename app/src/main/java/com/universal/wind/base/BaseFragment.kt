package com.universal.wind.base

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

/**
 * 基础 fragment
 */

abstract class BaseFragment<T: IBasePresenter?>:Fragment(),IBaseView {

    protected var mPresenter : T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindPresenter()
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(),null)
        initView(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        initData()
    }


    override fun onDestroy() {
        //对 presenter 解绑
        mPresenter?.detachView()
        mPresenter = null
        super.onDestroy()
    }

    /**
     * 初始化视图 id
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 绑定 presenter
     */
    private fun bindPresenter(){
        mPresenter = initPresenter()
        mPresenter!!.attachView(this)
    }

    /**
     * 初初始化 presenter
     */
    protected abstract fun initPresenter(): T

    /**
     * 初始化视图
     */
    protected abstract fun initView(view:View)

    /**
     * 初始化数据
     */
    protected abstract fun initData()





}