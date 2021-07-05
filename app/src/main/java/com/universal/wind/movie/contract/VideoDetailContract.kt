package com.universal.wind.movie.contract

import com.universal.wind.bean.VideoBean
import com.universal.wind.configs.VideoDetailType

interface VideoDetailContract {

    interface GetVideoDetailCallBack{
        fun getVideoDetailInfo(type: VideoDetailType, videoBean: VideoBean?)
    }

}