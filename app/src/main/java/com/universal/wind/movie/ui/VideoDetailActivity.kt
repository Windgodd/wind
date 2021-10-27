package com.universal.wind.movie.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.universal.wind.R
import com.universal.wind.movie.adapter.EpisodeTypeAdapter
import com.universal.wind.base.BaseActivity
import com.universal.wind.bean.VideoBean
import com.universal.wind.configs.ConstValue.Companion.VIDEO_BEAN_TAG
import com.universal.wind.configs.VideoDetailType
import com.universal.wind.movie.contract.VideoDetailContract
import com.universal.wind.movie.presenter.VideoDetailPresenter

class VideoDetailActivity:BaseActivity<VideoDetailPresenter>(),
    VideoDetailContract.GetVideoDetailCallBack {


    private var tvVideoName: TextView? = null
    private var ivVideoImg: ImageView? = null
    private var tvVideoStar:TextView?  = null
    private var tvVideoDirector:TextView? = null
    private var tvVideoCategory:TextView? = null
    private var tvVideoDesc:TextView? = null
    private var rlEpisode:RecyclerView? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_video_info
    }

    override fun initPresenter(): VideoDetailPresenter {
        return VideoDetailPresenter()
    }

    override fun initView() {
        tvVideoName = findViewById(R.id.tv_video_name)
        ivVideoImg  = findViewById(R.id.iv_video_img)
        tvVideoStar = findViewById(R.id.tv_video_star)
        tvVideoDirector = findViewById(R.id.tv_video_director)
        tvVideoCategory = findViewById(R.id.tv_video_category)
        tvVideoDesc = findViewById(R.id.tv_video_desc)
        rlEpisode = findViewById(R.id.rl_episode)
    }


    override fun initData() {
        val videoBean:VideoBean = intent.getSerializableExtra(VIDEO_BEAN_TAG) as VideoBean
        //获取影片详细信息
        mPresenter?.getVideoInfo(videoBean, videoBean.videoDetailType!!, this)

    }

    /**
     * 获取影片详细信息回调
     * @param type 类型
     * @param videoBean 影片信息
     */
    override fun getVideoDetailInfo(type: VideoDetailType, videoBean: VideoBean?) {
        if(videoBean == null) return
        tvVideoName!!.text = videoBean.videoName
        tvVideoStar!!.text = videoBean.starName
        tvVideoCategory!!.text = videoBean.category
        tvVideoDirector!!.text = videoBean.videoDirector
        tvVideoDesc!!.text = videoBean.videoDesc
        Glide.with(this@VideoDetailActivity)
            .load(videoBean.imgUrl)
            .into(ivVideoImg)

        val adapter = EpisodeTypeAdapter(this)
        val manager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        rlEpisode!!.layoutManager = manager
        rlEpisode!!.adapter = adapter
        adapter.setData(videoBean)

    }


}