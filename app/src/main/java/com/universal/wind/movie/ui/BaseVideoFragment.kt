package com.universal.wind.movie.ui

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universal.wind.R
import com.universal.wind.adapter.ItemClickListener
import com.universal.wind.movie.adapter.VideoTypeAdapter
import com.universal.wind.base.BaseFragment
import com.universal.wind.bean.VideoBean
import com.universal.wind.configs.BaseConfigs
import com.universal.wind.customview.WindProgressDialog
import com.universal.wind.movie.adapter.VideoListAdapter
import com.universal.wind.movie.presenter.BaseVideoPresenter

/**
 * 显示影片信息基类
 */
open class BaseVideoFragment:BaseFragment<BaseVideoPresenter>() {

    protected val TAG:String = this.javaClass.name
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

    /**
     * 类型
     */
    protected var videoTid:String = ""
    //年份
    private var videoYear:String = ""
    //地区
    private var videoArea:String = ""
    //排序
    private var videoOrder:String = ""


    private var typeList = arrayListOf<String>()
    //地区列表数据：大陆，欧美
    private var areaList = arrayListOf<String>()
    //年份数据列表
    private var yearList = arrayListOf<String>()
    //地址列表数据
    private var urlList  = arrayListOf<String>()
    //排序列表数据
    private val sortList = arrayListOf<String>()
    //影片数据
    private var videoList = arrayListOf<VideoBean>()

    protected  var progressDialog:WindProgressDialog? = null

    /**
     * 关联布局
     */
    override fun getLayoutId(): Int {
        return R.layout.fragment_video_base
    }

    /**
     * 初始化 Presenter
     */
    override fun initPresenter(): BaseVideoPresenter {
        return BaseVideoPresenter()
    }

    /**
     * 初始化视图
     */
    override fun initView(view: View) {
        rlVideoSort = view.findViewById(R.id.rl_video_sort)
        rlVideoType = view.findViewById(R.id.rl_video_type)
        rlVideoArea = view.findViewById(R.id.rl_video_area)
        rlVideoYear = view.findViewById(R.id.rl_video_year)
        rlVideoList = view.findViewById(R.id.rl_videos)
    }

    /**
     * 初始化数据
     */
    override fun initData() {

        if(sortList.size < 1){
            sortList.add("按时间")
            sortList.add("按人气")
            sortList.add("按推荐")
            sortList.add("按评分")
            showList(sortList,rlVideoSort!!,ListType.Sort)
        }

        if(!isHidden){
            if(videoList.size > 0){
                showTypeList(this.typeList,this.urlList)
                showYearList(this.yearList)
                showAreaList(this.areaList)
                showVideoList(videoList)
                showList(sortList,rlVideoSort!!,ListType.Sort)
                Log.i(TAG,"显示原有数据")
            }else{
                Log.i(TAG,"请求数据")
                getData()
            }
        }

    }

    /**
     * 根据 url 获取 数据
     */
    fun getData(){
        showProgressDialog()
        val url = BaseConfigs.videoBaseUrl + "${videoTid}&order=${videoOrder}&area=${videoArea}&year=${videoYear}&jq="
        mPresenter?.getVideoData(url)
    }

    /**
     * 显示类型
     * @param
     */
    fun showTypeList(typeList:ArrayList<String>,urlList:ArrayList<String>){
        if(this.typeList.size > 0){
            return
        }
        this.typeList = typeList
        this.urlList  = urlList
        showList(typeList,rlVideoType!!,ListType.Category)
    }

    /**
     * 显示年份
     * @param yearList 类型数据
     */
    fun showYearList(yearList: ArrayList<String>){
        if(this.yearList.size > 0){
            return
        }
        this.yearList = yearList
        showList(this.yearList,rlVideoYear!!,ListType.Year)
    }

    /**
     * 显示地区
     * @param areaList 地区数据
     */
    fun showAreaList(areaList: ArrayList<String>){
        if(this.areaList.size > 0){
            return
        }
        this.areaList = areaList
        showList(this.areaList,rlVideoArea!!,ListType.Area)
    }

    /**
     * 显示影片信息
     * @param  videoList 影片数据
     */
    fun showVideoList(videoList:ArrayList<VideoBean>){
        this.videoList = videoList
        val adapter = VideoListAdapter(context!!)
        val layoutManager = GridLayoutManager(context!!,3)
        rlVideoList!!.layoutManager = layoutManager
        rlVideoList!!.adapter = adapter
        adapter.updateList(videoList)

    }

    fun showList(list:ArrayList<String>,rl:RecyclerView,listType:ListType){
        hideProgressDialog()
        val adapter = VideoTypeAdapter(context!!)
        val manager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        rl.layoutManager = manager
        rl.adapter = adapter
        adapter.setData(list)
        adapter.setOnItemClickListener(object : ItemClickListener {
            override fun itemClickListener(position: Int, obj: Any?) {
                val content = obj as String
                when (listType){
                    ListType.Year -> videoYear = content
                    ListType.Sort ->{
                        when(content){
                            "按人气" -> videoOrder = "hit"
                            "按时间" -> videoOrder = "time"
                            "按推荐" -> videoOrder = "commend"
                            "按评分" -> videoOrder = "score"
                        }
                    }
                    ListType.Area -> videoArea = content
                    ListType.Category -> videoTid = "/"+urlList[position]
                }
                getData()
            }
        })

    }


    /**
     * 类型枚举:排序，地区，类型，年份
     */
     enum class ListType{
         Sort,Area,Category,Year
    }

    protected fun showProgressDialog(){
        progressDialog = WindProgressDialog(activity!!)
        progressDialog!!.showDialog("加载中..","加载超时")
    }

    protected fun hideProgressDialog(){
        progressDialog?.dismiss()
    }
}