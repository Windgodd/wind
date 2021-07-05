package com.universal.wind.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.universal.wind.R
import com.universal.wind.bean.VideoEpisodeBean
import com.universal.wind.bean.VideoBean
import com.universal.wind.configs.ConstValue.Companion.VIDEO_BEAN_TAG
import com.universal.wind.movie.view.VideoPlayActivity

/**
 * 选集列表适配器
 */
class EpisodeAdapter(context: Context):RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    private var episodeList:ArrayList<VideoEpisodeBean>? = null
    private var mContext:Context = context
    private var videoBean:VideoBean? = null

    inner class EpisodeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvTitle:TextView = itemView.findViewById(R.id.tv_item_episode_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_episode,null)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.tvTitle.text = episodeList?.get(position)?.episodeName
        //点选集跳转播放页面
        holder.itemView.setOnClickListener{
            videoBean!!.videoPlayHtml = episodeList?.get(position)?.episodeHtml
            val intent = Intent(mContext,VideoPlayActivity::class.java)
            intent.putExtra(VIDEO_BEAN_TAG,videoBean!!)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return episodeList?.size ?:0
    }

    fun setData(videoBean:VideoBean){
        episodeList = videoBean.episodeList ?:return
        this.videoBean = videoBean
        notifyDataSetChanged()
    }

}