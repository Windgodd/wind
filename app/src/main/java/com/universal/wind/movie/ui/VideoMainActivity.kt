package com.universal.wind.movie.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.universal.wind.R
import com.universal.wind.movie.adapter.VideoFragmentsAdapter
import com.universal.wind.base.BaseActivity
import com.universal.wind.movie.presenter.VideoMainPresenter

/**
 * 影片主页
 */
class VideoMainActivity:BaseActivity<VideoMainPresenter>() {

    private var viewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null
    private var etSearch: EditText? = null

    private var videoHomeFragment: VideoHomeFragment? = null
    private var movieFragment: MovieFragment? = null
    private var tvFragment: TVFragment? = null
    private var animFragment: AnimFragment? = null



    private val titles = arrayListOf("首页", "电影", "电视剧", "动漫")
    private val icons = arrayListOf(
        R.mipmap.icon_video_main_default,
        R.mipmap.icon_video_movie_default,
        R.mipmap.icon_video_sitcon_default,
        R.mipmap.icon_video_anim_default
    )

    private val selectedIcons = arrayListOf(
        R.mipmap.icon_video_main_selected,
        R.mipmap.icon_video_movie_selected,
        R.mipmap.icon_video_sitcom_selected,
        R.mipmap.icon_video_anim_selected
    )

    private val fragments = ArrayList<Fragment>()
    private var pagerAdapter: VideoFragmentsAdapter? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_video_main

    }

    override fun initPresenter(): VideoMainPresenter {
        return VideoMainPresenter()
    }

    override fun initView() {
        viewPager = findViewById(R.id.vp_video_main)
        tabLayout = findViewById(R.id.tb_video_main)
        etSearch  = findViewById(R.id.et_video_search)


        etSearch?.isFocusable = false
    }

    override fun initData() {
        videoHomeFragment = VideoHomeFragment()
        movieFragment = MovieFragment()
        animFragment  = AnimFragment()
        tvFragment    = TVFragment()

        fragments.add(videoHomeFragment!!)
        fragments.add(movieFragment!!)
        fragments.add(tvFragment!!)
        fragments.add(animFragment!!)


        for (i in titles.indices){
            tabLayout!!.addTab(tabLayout!!.newTab())
        }
        //关联 tabLayout 与 viewpager
        tabLayout!!.setupWithViewPager(viewPager,true)
        pagerAdapter = VideoFragmentsAdapter(fragments,supportFragmentManager)

        /**
         * 标签切换监听
         * 更改标签样式
         */
        tabLayout!!.addOnTabSelectedListener(object:OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                changeTabStatus(true,tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                changeTabStatus(false,tab)

            }
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }
        })

        viewPager!!.adapter = pagerAdapter
        for (i in titles.indices){
            tabLayout!!.getTabAt(i)!!.customView = getTabView(this,i)
        }
        //默认选中第一个
        changeTabStatus(true,tabLayout?.getTabAt(0))
    }

    fun changeFragment(index:Int){
        viewPager?.setCurrentItem(index,true)

    }

    /**
     * 改变 tab 样式
     * @param isSelect 是否被选中
     * @param tab 被选中的 tab
     */
    private fun changeTabStatus(isSelect:Boolean , tab:TabLayout.Tab?){
        val index = tab!!.position
        val view = tab.customView
        val tabIcon:ImageView? = view?.findViewById(R.id.tab_content_image)
        val tabText:TextView?  = view?.findViewById(R.id.tab_content_text)
        if(isSelect){
            tabIcon?.setImageResource(selectedIcons[index])
            tabText?.setTextColor(resources.getColor(R.color.green,null))
        }else{
            tabIcon?.setImageResource(icons[index])
            tabText?.setTextColor(resources.getColor(R.color.black,null))
        }

    }

    /**
     * 初始化 tab
     */
    @SuppressLint("InflateParams")
    private fun getTabView(context: Context, position : Int ): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_video_tab,null)
        val tabIcon:ImageView = view.findViewById(R.id.tab_content_image)
        val tabText:TextView  = view.findViewById(R.id.tab_content_text)
        tabIcon.setImageResource(icons[position])
        tabText.text = titles[position]
        return view
    }



}