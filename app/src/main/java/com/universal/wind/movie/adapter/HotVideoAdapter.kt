package com.universal.wind.movie.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.universal.wind.R
import com.universal.wind.bean.VideoBean
import com.universal.wind.configs.ConstValue
import com.universal.wind.movie.view.VideoDetailActivity

/**
 * 热门影片列表适配器
 */
class HotVideoAdapter(context: Context):RecyclerView.Adapter<HotVideoAdapter.HotVideoHolder>() {

    private var videoList = arrayListOf<VideoBean>()
    private val mContext = context


    class HotVideoHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val img:ImageView = itemView.findViewById(R.id.iv_video_img)
        val videoName: TextView = itemView.findViewById(R.id.tv_video_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotVideoHolder {
        val itemView:View = LayoutInflater.from(mContext).inflate(
            R.layout.item_hot_video_info,
            null
        )
        return HotVideoHolder(itemView)
    }

    override fun onBindViewHolder(holder: HotVideoHolder, position: Int) {
        val item = videoList[position]
        Glide.with(mContext)
            .load(item.imgUrl)
            .into(holder.img)
        holder.itemView.setOnClickListener{
            val intent = Intent(mContext,VideoDetailActivity::class.java)
            intent.putExtra(ConstValue.VIDEO_BEAN_TAG,videoList[position])
            mContext.startActivity(intent)
        }
        holder.videoName.text = item.videoName

    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    fun updateList(list:ArrayList<VideoBean>){
        this.videoList = list
        notifyDataSetChanged()
    }
}