package com.universal.wind.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universal.wind.R
import com.universal.wind.bean.VideoBean
import com.universal.wind.bean.VideoEpisodeTypeBean

/**
 * 资源分类列表
 */
class EpisodeTypeAdapter(context:Context):RecyclerView.Adapter<EpisodeTypeAdapter.EpisodeTypeHolder>() {

    private val mContext = context
    private var episodeTypeList:List<VideoEpisodeTypeBean> = arrayListOf()
    private var videoBean: VideoBean? = null

    inner class EpisodeTypeHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvEpisodeType:TextView = itemView.findViewById(R.id.tv_episode_type)
        val rlEpisodeType:RecyclerView = itemView.findViewById(R.id.rl_episode_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeTypeHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_spisode_type,null)
        return EpisodeTypeHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeTypeHolder, position: Int) {
        holder.tvEpisodeType.text = episodeTypeList[position].typeName
        //选集列表，每行显示三列
        val adapter = EpisodeAdapter(mContext)
        val manager = GridLayoutManager(mContext,3)
     //   val manager = LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false)
        holder.rlEpisodeType.layoutManager = manager
        holder.rlEpisodeType.adapter = adapter
        holder.rlEpisodeType.isNestedScrollingEnabled = false;
        holder.rlEpisodeType.setHasFixedSize(true);
        adapter.setData(videoBean!!,position)

    }

    override fun getItemCount(): Int {
        return episodeTypeList.size
    }

    fun setData(videoBean:VideoBean){
        episodeTypeList = videoBean.episodeList ?:return
        this.videoBean = videoBean
        notifyDataSetChanged()
    }
}