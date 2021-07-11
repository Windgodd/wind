package com.universal.wind.movie.view

import android.content.res.Configuration
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.universal.wind.R
import com.universal.wind.base.BaseActivity
import com.universal.wind.bean.VideoBean
import com.universal.wind.configs.ConstValue.Companion.VIDEO_BEAN_TAG
import com.universal.wind.movie.presenter.VideoPlayPresenter

/**
 * 视频播放页面
 * @author wind
 *
 */
class VideoPlayActivity:BaseActivity<VideoPlayPresenter>() {

    private var videoPlay: StandardGSYVideoPlayer? = null
    private var isPlay = false
    private var isPause = false
    private val cache = false
    private var url: String? = ""
    //控制屏幕
    private var orientationUtils: OrientationUtils? = null
    //视频播放控件
    private var gsyVideoOptionBuilder: GSYVideoOptionBuilder? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_video_play
    }

    override fun initPresenter(): VideoPlayPresenter {
        return VideoPlayPresenter()
    }

    /**
     * 初始化视图
     */
    override fun initView() {
        videoPlay = findViewById(R.id.video_play)

    }

    /**
     * 初始化数据
     */
    override fun initData() {
        val videoBean:VideoBean = intent.getSerializableExtra(VIDEO_BEAN_TAG) as VideoBean
        if(TextUtils.isEmpty(videoBean.videoPlayHtml)){
            Toast.makeText(this, "无效参数", Toast.LENGTH_SHORT).show()
            return
        }
        initPlayer(videoBean)
        mPresenter?.getVideoPlayUrl(videoBean)

    }

    /**
     * 初始化播放器
     * @param  videoBean 影片数据
     */
    private fun initPlayer(videoBean:VideoBean){
        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(R.mipmap.icon_video_default)
        //增加title

        videoPlay?.titleTextView?.visibility = View.GONE
        videoPlay?.backButton?.visibility = View.GONE

        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, videoPlay)
        //初始化不打开外部的旋转
        orientationUtils!!.isEnable = true
        gsyVideoOptionBuilder = GSYVideoOptionBuilder()
            .setThumbImageView(imageView)
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setSeekRatio(1f)
            .setUrl(url)
            .setCacheWithPlay(cache)
            .setVideoTitle(videoBean.videoName)
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    //开始播放了才能旋转和全屏
                    orientationUtils!!.isEnable = videoPlay!!.isRotateWithSystem
                    isPlay = true
                }

                override fun onQuitFullscreen(url: String, vararg objects: Any) {
                    super.onQuitFullscreen(url, *objects)
                    if (orientationUtils != null) {
                        orientationUtils?.backToProtVideo()
                    }
                }
            })
        gsyVideoOptionBuilder?.build(videoPlay)
        videoPlay?.fullscreenButton?.setOnClickListener { //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            videoPlay?.startWindowFullscreen(this@VideoPlayActivity, true, true)
        }
        //锁屏
        videoPlay?.setLockClickListener { _, lock ->
            if (orientationUtils != null) {
                //配合下方的onConfigurationChanged
                orientationUtils?.isEnable = !lock
            }
        }
    }

    /**
     * 获取当前播放器
     */
    private fun getCurPlay(): GSYVideoPlayer? {
        return if (videoPlay?.fullWindowPlayer != null) {
            videoPlay?.fullWindowPlayer
        } else videoPlay
    }


    override fun onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils?.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    /**
     * 暂停
     */
    override fun onPause() {
        getCurPlay()?.onVideoPause()
        super.onPause()
        isPause = true
    }

    /**
     * 页面恢复
     */
    override fun onResume() {
        getCurPlay()?.onVideoResume()
        super.onResume()
        isPause = false
    }

    /**
     * 页面销毁
     */
    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            getCurPlay()?.release()
        }
        if (orientationUtils != null) orientationUtils?.releaseListener()
    }

    /**
     * 播放视频
     * @param url 视频地址
     * @param title 影片名称
     */
     fun playVideo(url: String, title: String) {
         this.url = url
        videoPlay?.release()
        gsyVideoOptionBuilder!!.setUrl(url)
            .setCacheWithPlay(cache)
            .setVideoTitle(title)
            .build(videoPlay)
        gsyVideoOptionBuilder!!.build(videoPlay)
        videoPlay?.postDelayed({ videoPlay?.startPlayLogic() }, 1000)
    }

    /**
     * 屏幕变化
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            videoPlay?.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }
}