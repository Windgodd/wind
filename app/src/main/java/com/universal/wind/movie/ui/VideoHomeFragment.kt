package com.universal.wind.movie.ui

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universal.wind.R
import com.universal.wind.movie.adapter.HotVideoAdapter
import com.universal.wind.base.BaseFragment
import com.universal.wind.base.IBaseView
import com.universal.wind.bean.VideoBean
import com.universal.wind.movie.presenter.VideoHomePresenter

class VideoHomeFragment:BaseFragment<VideoHomePresenter>(),IBaseView, View.OnClickListener {

    //更多按钮
    private var tvHotMovieMore: TextView? = null
    private var tvHotTvMore: TextView? = null
    private var tvHotAnimMore:      TextView? = null

    private var rlHotMovie: RecyclerView? = null
    private var rlHotAnim:  RecyclerView? = null
    private var rlHotTv:    RecyclerView? = null
    private var mContext:Context? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_video_home
    }

    override fun initPresenter(): VideoHomePresenter {
        return VideoHomePresenter()
    }

    /**
     * 初始化视图
     */
    override fun initView(view: View) {
        tvHotAnimMore = view.findViewById(R.id.tv_hot_anim)
        tvHotTvMore = view.findViewById(R.id.tv_hot_tv)
        tvHotMovieMore = view.findViewById(R.id.tv_hot_movie)

        rlHotAnim = view.findViewById(R.id.rl_anim)
        rlHotMovie = view.findViewById(R.id.rl_movie)
        rlHotTv = view.findViewById(R.id.rl_tv)

        tvHotAnimMore?.setOnClickListener(this)
        tvHotTvMore?.setOnClickListener(this)
        tvHotMovieMore?.setOnClickListener(this)
    }


    /**
     * 绑定上下文
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    /**
     * 爬取数据
     */
    override fun initData() {
        mPresenter?.getHotVideoData()
    }


    /**
     * 更新列表数据
     * @param listMovie 电影数据
     * @param listTv 电视数据
     * @param listAnim 动漫数据
     */
     fun updateList(listMovie:ArrayList<VideoBean>,listTv:ArrayList<VideoBean>,listAnim:ArrayList<VideoBean>){
        val hotMovieAdapter = HotVideoAdapter(mContext!!)
        val hotTvAdapter = HotVideoAdapter(mContext!!)
        val hotAnimAdapter = HotVideoAdapter(mContext!!)

        val movieManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        val tvManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        val animManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        rlHotMovie!!.layoutManager = movieManager
        rlHotTv!!.layoutManager = tvManager
        rlHotAnim!!.layoutManager = animManager

        rlHotTv!!.adapter = hotTvAdapter
        rlHotMovie!!.adapter = hotMovieAdapter
        rlHotAnim!!.adapter = hotAnimAdapter

        hotTvAdapter.updateList(listTv)
        hotMovieAdapter.updateList(listMovie)
        hotAnimAdapter.updateList(listAnim)


    }

    override fun onClick(v: View?) {
        when (v){
            tvHotAnimMore -> {
                (activity as VideoMainActivity).changeFragment(3)
            }
            tvHotMovieMore -> {
                (activity as VideoMainActivity).changeFragment(1)
            }
            tvHotTvMore -> {
                (activity as VideoMainActivity).changeFragment(2)
            }
        }

    }


}