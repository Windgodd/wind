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
class FavCategoryAdapter(var context: Context) :
    RecyclerView.Adapter<FavCategoryViewHolder>() {

    var nameList = context.resources.getStringArray(R.array.str_categories)
    var imgList = intArrayOf(R.mipmap.icon_fav_ct_movie,R.mipmap.icon_fav_ct_music)
    var itemClickListener:ItemClickListener? = null




    inner class FavCategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.iv_fav_category_img)
        var content:TextView = itemView.findViewById(R.id.tv_fav_category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavCategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_fav_category,parent,false)
        return FavCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavCategoryViewHolder, position: Int) {
        holder.content.text = nameList[position]
        holder.img.setBackgroundResource(imgList[position])
        holder.itemView.setOnClickListener{
            itemClickListener?.itemClickListener(position,nameList[position])
        }
    }

    override fun getItemCount(): Int {
         return imgList.size
    }




}