package com.universal.wind.movie.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.universal.wind.R
import com.universal.wind.adapter.ItemClickListener

/**
 * @author wind
 * 类别数据适配器
 */
class VideoTypeAdapter(var context: Context) :
    RecyclerView.Adapter<VideoTypeAdapter.VideoTypeViewHolder>() {

    //被选中项的下标
    var selectedIndex = 0
    //数据
    var typeList = arrayListOf<String>()
    //点击回调
    var itemClickListener: ItemClickListener? = null


    inner class VideoTypeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var content:TextView = itemView.findViewById(R.id.tv_item_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoTypeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_video_type,parent,false)
        return VideoTypeViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: VideoTypeViewHolder, position: Int) {
        holder.content.text = typeList[position]
        if(position == selectedIndex){
            holder.content.setBackgroundResource(R.drawable.shape_video_type_bg)
            holder.content.setTextColor(context.resources.getColor(R.color.green))
        }else{
            holder.content.background = holder.itemView.background
            holder.content.setTextColor(android.graphics.Color.DKGRAY)
        }

        holder.itemView.setOnClickListener{
            if(position == selectedIndex){
                return@setOnClickListener
            }
            selectedIndex = position
            itemClickListener?.itemClickListener(position,typeList[position])
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
         return typeList.size
    }

    /**
     * 设置数据
     */
    fun setData(data:ArrayList<String>){
        this.typeList = data
        Log.i("adapter","数据长度-->"+typeList.size)
        notifyDataSetChanged()
    }

    /**
     * 设置回调
     */
    fun setOnItemClickListener(clickListener: ItemClickListener){
        this.itemClickListener = clickListener
    }

}