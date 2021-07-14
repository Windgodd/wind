package com.universal.wind.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.universal.wind.R
import com.universal.wind.adapter.FavCategoryAdapter.FavCategoryViewHolder

/**
 * 主页类别适配器
 */
class VideoTypeAdapter(var context: Context) :
    RecyclerView.Adapter<VideoTypeAdapter.VideoTypeViewHolder>() {

    var typeList = arrayListOf<String>()
    var itemClickListener:ItemClickListener? = null




    inner class VideoTypeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var content:TextView = itemView.findViewById(R.id.tv_item_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoTypeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_video_type,parent,false)
        return VideoTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoTypeViewHolder, position: Int) {
        holder.content.text = typeList[position]
        holder.content.setBackgroundResource(R.drawable.shape_video_type_bg)
        holder.itemView.setOnClickListener{
            itemClickListener?.itemClickListener(position,typeList[position])
        }
    }

    override fun getItemCount(): Int {
         return typeList.size
    }

    fun setData(data:ArrayList<String>){
        this.typeList = data
        notifyDataSetChanged()
    }

}