package com.universal.wind.bean

import java.io.Serializable

/**
 * 视频可能有多个数据源
 *
 */
class VideoEpisodeTypeBean:Serializable {
    //类别名称如：资源1，高清1，高清2
    var typeName:String? = null
    //对应的选集列表
    var episodeList:List<VideoEpisodeBean>? = null
}