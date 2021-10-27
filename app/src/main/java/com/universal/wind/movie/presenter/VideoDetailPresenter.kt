package com.universal.wind.movie.presenter

import android.text.TextUtils
import android.widget.Toast
import com.universal.wind.base.BasePresenter
import com.universal.wind.bean.VideoEpisodeBean
import com.universal.wind.bean.VideoBean
import com.universal.wind.bean.VideoEpisodeTypeBean
import com.universal.wind.configs.VideoDetailType
import com.universal.wind.movie.contract.VideoDetailContract
import com.universal.wind.movie.ui.VideoDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class VideoDetailPresenter:BasePresenter<VideoDetailActivity>() {



    /**
     * 根据不同的类别对网页进行解析
     */
    fun getVideoInfo(videoBean:VideoBean,type:VideoDetailType,callBack:VideoDetailContract.GetVideoDetailCallBack){
        when (type){
            VideoDetailType.TYPE_MEI_SHI -> analysisUrl1( videoBean,type,callBack)
        }
    }

    /**
     * 获取没事影院的 video 资源，解析 Html 获取 video 详细信息
     * @param type 没事影院
     * @param callBack 回调
     */
    private fun analysisUrl1(videoBean:VideoBean,type:VideoDetailType,callBack:VideoDetailContract.GetVideoDetailCallBack){
        val url = videoBean.detailUrl
        if(TextUtils.isEmpty(url)){
            Toast.makeText(mView!!.getContext(),"无效的地址",Toast.LENGTH_SHORT).show()
            mView?.getVideoDetailInfo(type,null)
            return
        }
        GlobalScope.launch(Dispatchers.IO){
            try {
                val doc = Jsoup.connect(url).get()
                if( doc == null){
                    mView?.getVideoDetailInfo(type,null)
                    return@launch
                }

                val result = doc.getElementsByClass("media-body clearfix")
                val sections = result[0].getElementsByTag("p")
                //主演
                val starSections = sections[0].getElementsByTag("a")
                for(section in starSections){
                    val starValue = section.ownText()
                    videoBean.starName += "${starValue},"
                }
                //导演
                val directorSection = sections[1].getElementsByTag("a")
                val directorValue = directorSection[0].ownText()
                videoBean.videoDirector = directorValue
                //简介
                val descSection = doc.getElementsByClass("content-des")
                val descValue = descSection[0].ownText()
                videoBean.videoDesc = descValue
                videoBean.episodeList = arrayListOf()
                //选集
                val episodeSections = doc.getElementsByClass("layout clearfix")
                for( episodeSection in episodeSections){
                    val tmp = episodeSection.getElementsByClass("playlist clearfix")
                    if(tmp == null || tmp.size <1){
                        continue
                    }
                    val episodeTypeBean = VideoEpisodeTypeBean()
                    val title = episodeSection.getElementsByTag("h4")[0].ownText()
                    episodeTypeBean.typeName = title
                    val episodeList:ArrayList<VideoEpisodeBean> = arrayListOf()
                    val playInfoSections = episodeSection.getElementsByTag("a")
                    for(playInfoSection in playInfoSections){
                        val episodeBean = VideoEpisodeBean()
                        val title2 = playInfoSection.attr("title")
                        val href = "https://www.16co.com" + playInfoSection.attr("href")
                        episodeBean.episodeName = title + "(${title2})"
                        episodeBean.episodeHtml = href
                        episodeList.add(episodeBean)
                    }
                    episodeTypeBean.episodeList= episodeList
                    videoBean.episodeList!!.add(episodeTypeBean)

                }
                withContext(Dispatchers.Main){
                    mView?.getVideoDetailInfo(type,videoBean)
                }

            }catch (e:Exception){
                mView?.getVideoDetailInfo(type,null)
            }



        }


    }

}