package com.universal.wind.movie.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universal.wind.R
import com.universal.wind.adapter.VideoTypeAdapter
import com.universal.wind.base.BaseFragment
import com.universal.wind.configs.BaseConfigs
import com.universal.wind.movie.presenter.BaseVideoPresenter

open class BaseVideoFragment:BaseFragment<BaseVideoPresenter>() {

    //按人气，评分
    var rlVideoSort: RecyclerView? = null
    //按类别
    var rlVideoType: RecyclerView? = null
    //按地区
    var rlVideoArea: RecyclerView? = null
    //按年份
    var rlVideoYear: RecyclerView? = null
    //video 列表
    var rlVideoList:RecyclerView?  = null

    protected var videoTid:String = "1"
    private var videoYear:String = ""
    private var videoArea:String = ""
    private var videoType:String = ""
    private var videoOrder:String = ""

    private var url = "/search.php?searchtype=5&order=hit&tid=1&area=&year=&jq="

    private var typeList = arrayListOf<String>()
    private var areaList = arrayListOf<String>()
    private var yearList = arrayListOf<String>()
    private var urlList  = arrayListOf<String>()


    override fun getLayoutId(): Int {
        return R.layout.fragment_video_base
    }

    override fun initPresenter(): BaseVideoPresenter {
        return BaseVideoPresenter()
    }

    override fun initView(view: View) {
        rlVideoSort = view.findViewById(R.id.rl_video_sort)
        rlVideoType = view.findViewById(R.id.rl_video_type)
        rlVideoArea = view.findViewById(R.id.rl_video_area)
        rlVideoYear = view.findViewById(R.id.rl_video_year)
        rlVideoList = view.findViewById(R.id.rl_videos)
    }

    override fun initData() {
        val url = BaseConfigs.videoBaseUrl + "/search.php?${videoTid}&order=${videoOrder}&area=${videoArea}&year=${videoYear}&jq="
        mPresenter?.getVideoData(url)
    }

    fun showTypeList(typeList:ArrayList<String>,urlList:ArrayList<String>){
     //   this.typeList.clear()
        this.urlList.clear()
     //   this.typeList.addAll(typeList)
        this.urlList.addAll(urlList)
        showList(this.yearList,typeList,rlVideoType!!)
    }

    fun showYearList(yearList: ArrayList<String>){
//        this.yearList.clear()
 //       this.yearList.addAll(yearList)
        showList(this.typeList,yearList,rlVideoYear!!)
    }

    fun showAreaList(areaList: ArrayList<String>){
//        this.areaList.clear()
//        this.areaList.addAll(areaList)
        showList(this.areaList,areaList,rlVideoArea!!)
    }

    fun showList(list:ArrayList<String>, data:ArrayList<String>,rl:RecyclerView){
        list.clear()
        list.addAll(data)
        val adapter = VideoTypeAdapter(context!!)
        val manager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        rl.layoutManager = manager
        rl.adapter = adapter
        adapter.setData(list)

    }
}