package com.universal.wind.base

import android.R
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity

/**
 * 基础 activity
 */

abstract class BaseActivity<T : IBasePresenter?>: AppCompatActivity(), IBaseView {

    protected var mPresenter : T? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

     //   setStatusBar()
        bindPresenter()
        initView()
        initData()
    }

    /**
     * 界面结束时
     */
    override fun finish() {
        //对 presenter 解绑
        mPresenter?.detachView()
        mPresenter = null
        super.finish()
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
    protected abstract fun initView()

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    fun getContext(): BaseActivity<T> {
        return this
    }


    /**
     * 设置状态栏沉浸式
     */
    private fun setStatusBar(){
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        //状态栏覆盖在contentView上面，设置透明使contentView的背景透出来
        window.statusBarColor = Color.TRANSPARENT;

    //    val controller = window.insetsController
    //    controller?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)

        setAndroidNativeLightStatusBar(this,true)
    }

    private fun setAndroidNativeLightStatusBar(activity: Activity, dark: Boolean) {

        val decor: View = activity.window.decorView
        if (dark) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        } else {
            decor.systemUiVisibility =  Window.FEATURE_ACTION_BAR_OVERLAY
        }
        val content = (findViewById<View>(R.id.content) as ViewGroup).getChildAt(0)
        if (content != null ) {
            content.fitsSystemWindows = true
        }
    }

}