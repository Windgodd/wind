package com.universal.wind.movie.presenter

import com.universal.wind.base.BasePresenter
import com.universal.wind.bean.VideoBean
import com.universal.wind.movie.view.VideoPlayActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.lang.Exception

class VideoPlayPresenter:BasePresenter<VideoPlayActivity>() {

    fun getVideoPlayUrl(videoBean:VideoBean){
        GlobalScope.launch(Dispatchers.IO){
            try {
                val doc = Jsoup.connect(videoBean.videoPlayHtml).get() ?:return@launch
                val result = doc.getElementsByClass("play clearfix")
                val sections = result[0].getElementsByTag("script")
                val content  = sections[0].data()
                val url = content.substring(content.indexOf("https"),content.indexOf("m3u8")+4)
                withContext(Dispatchers.Main){
                    mView?.playVideo(url,videoBean.videoName!!)
                }
            }catch (e:Exception){

            }

        }
    }

}