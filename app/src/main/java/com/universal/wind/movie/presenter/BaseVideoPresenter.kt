package com.universal.wind.movie.presenter

import android.util.Log
import com.universal.wind.base.BaseFragmentPresenter
import com.universal.wind.bean.VideoBean
import com.universal.wind.configs.BaseConfigs
import com.universal.wind.configs.VideoDetailType
import com.universal.wind.movie.ui.BaseVideoFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class BaseVideoPresenter:BaseFragmentPresenter<BaseVideoFragment>() {

    /**
     * 解析 html
     * 提前页面信息
     */
    fun getVideoData(url:String){
        Log.i("url-->",url)
        GlobalScope.launch(Dispatchers.IO){
            val doc = Jsoup.connect(url).get() ?: return@launch

            val results = doc.getElementsByClass("scroller")
            //类型
            val typeSections = results[2].getElementsByTag("a")
            val typeUrlList = arrayListOf<String>()
            val typeList = arrayListOf<String>()
            for(typeSection in typeSections){
                val typeValue = typeSection.attr("title")
                val typeUrl   = typeSection.attr("href")
                typeList.add(typeValue)
                typeUrlList.add(typeUrl)
            }
            //地区
            val areaSections = results[3].getElementsByTag("a")
            val areaList = arrayListOf<String>()
            for(areaSection in areaSections){
                val typeValue = areaSection.attr("title")
                areaList.add(typeValue)

            }

            //年份
            val yearSections = results[4].getElementsByTag("a")
            val yearList = arrayListOf<String>()
            for(yearSection in yearSections){
                val typeValue = yearSection.attr("title")
                yearList.add(typeValue)
            }

            //影片信息
            val videoList = arrayListOf<VideoBean>()
            val videoSections = doc.getElementsByClass("item")
            videoSections.removeAt(0)
            for(videoSection in videoSections){
                val videoBean = VideoBean()

                val videoDetailUrl = videoSection.attr("href")
                val videoName = videoSection.attr("title")

                videoBean.videoName = videoName
                videoBean.detailUrl = BaseConfigs.videoBaseUrl + videoDetailUrl

                val videoImgSection = videoSection.getElementsByClass("cover")
                val videoImgTmp = videoImgSection.attr("style")
                val imgUrl = videoImgTmp.substring(videoImgTmp.indexOf("(")+1,videoImgTmp.indexOf(")"))

                videoBean.imgUrl = imgUrl
                videoBean.videoDetailType = VideoDetailType.TYPE_MEI_SHI

                val detailSections = videoSection.getElementsByClass("subtitle")
                val detailList = detailSections[0].getElementsByTag("span")
                for(detail in detailList){
                    val content = detail.ownText()
                    if(content.contains("类型")){
                        videoBean.category = content
                    }
                    if(content.contains("地区")){
                        videoBean.videoArea = content
                    }
                    if(content.contains("年份")){
                    //    videoBean.
                    }

                }
                videoList.add(videoBean)

            }

            //切换线程，更新界面
            withContext(Dispatchers.Main){
                mView?.showAreaList(areaList)
                mView?.showYearList(yearList)
                mView?.showTypeList(typeList,typeUrlList)
                mView?.showVideoList(videoList)
            }

        }
    }
}