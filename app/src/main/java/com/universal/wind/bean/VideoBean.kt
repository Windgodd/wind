package com.universal.wind.bean

import com.universal.wind.configs.VideoDetailType
import java.io.Serializable

/**
 * 影片实体类
 */
class VideoBean:Serializable {

    //影片名称
    var videoName:String? = null
    //图片
    var imgUrl:String? = null
    //主演
    var starName:String = ""
    //种类：电影，电视剧，动漫
    var category:String? = null
    //地区
    var videoArea:String? = null
    //上映时间
    var releaseTime:String? = null
    //详情地址
    var detailUrl:String? = null
    //导演
    var videoDirector:String? = null
    //简介
    var videoDesc:String? = null
    //选集
    var episodeList:ArrayList<VideoEpisodeBean>?= null
    //影片资源类别
    var videoDetailType:VideoDetailType? = null
    //视频播放页面的 url
    var videoPlayHtml:String? = null

}