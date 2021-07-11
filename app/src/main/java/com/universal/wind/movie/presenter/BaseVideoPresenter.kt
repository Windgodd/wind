package com.universal.wind.movie.presenter

import com.universal.wind.base.BaseFragmentPresenter
import com.universal.wind.movie.view.BaseVideoFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class BaseVideoPresenter:BaseFragmentPresenter<BaseVideoFragment>() {

    fun getVideoData(url:String){
        GlobalScope.launch(Dispatchers.IO){
            val doc = Jsoup.connect(url).get() ?: return@launch

            val results = doc.getElementsByClass("scroller");
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
            val areaSections = results[2].getElementsByTag("a")
            val areaList = arrayListOf<String>()
            for(areaSection in areaSections){
                val typeValue = areaSection.attr("title")
                areaList.add(typeValue)

            }

            //年份
            val yearSections = results[2].getElementsByTag("a")
            val yearList = arrayListOf<String>()
            for(yearSection in yearSections){
                val typeValue = yearSection.attr("title")
                yearList.add(typeValue)
            }


            withContext(Dispatchers.Main){
                mView?.showAreaList(areaList)
                mView?.showYearList(yearList)
                mView?.showTypeList(typeList,typeUrlList)
            }

        }
    }
}