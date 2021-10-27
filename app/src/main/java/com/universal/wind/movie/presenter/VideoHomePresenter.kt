package com.universal.wind.movie.presenter

import com.universal.wind.base.BaseFragmentPresenter
import com.universal.wind.bean.VideoBean
import com.universal.wind.configs.BaseConfigs
import com.universal.wind.configs.VideoDetailType
import com.universal.wind.movie.ui.VideoHomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class VideoHomePresenter:BaseFragmentPresenter<VideoHomeFragment>() {

    /**
     * 开启协程直接用爬数据
     */
    fun getHotVideoData(){
        GlobalScope.launch(Dispatchers.IO) {
            val doc = Jsoup.connect(BaseConfigs.videoBaseUrl).get()
            //分类
            val elsCategory =
                doc?.getElementsByClass("video-list1 clearfix")
            val hotMovieList = arrayListOf<VideoBean>()
            val hotTVList = arrayListOf<VideoBean>()
            val hotAnimList = arrayListOf<VideoBean>()

            if( elsCategory == null || elsCategory.size < 1){
                return@launch
            }

            for( category in elsCategory){
                val items = category.getElementsByClass("item")
                for (item in items) {
                    val videoBean = VideoBean()
                    val otherInfo = item.getElementsByClass("subtitle")
                    val labels = otherInfo[0].getElementsByTag("span")
                    val kind = labels[0].ownText()
                    val detailUrl = item.attr("href")
                    val videoName = item.attr("title")
                    val cover = item.getElementsByClass("cover")
                    val bgTmp = cover[0].attr("style")
                    val imgUrl = bgTmp.substring(bgTmp.indexOf("(")+1,bgTmp.indexOf(")"))

                    videoBean.imgUrl = imgUrl
                    videoBean.videoName = videoName
                    videoBean.detailUrl = BaseConfigs.videoBaseUrl+detailUrl
                    videoBean.category = kind
                    videoBean.videoArea = labels[1].ownText()
                    videoBean.releaseTime = labels[2].ownText()
                    videoBean.videoDetailType = VideoDetailType.TYPE_MEI_SHI

                    if( kind.contains("电影") || kind.contains("片")){
                        hotMovieList.add(videoBean)
                    }else if( kind.contains("剧")){
                        hotTVList.add(videoBean)
                    }else if(kind.contains("动漫")){
                        hotAnimList.add(videoBean)
                    }

                }
            }

            /**
             * 切换线程
             * 更新视图
             */
            withContext(Dispatchers.Main){
                mView?.updateList(hotMovieList,hotTVList,hotAnimList)
            }
        }
    }
}