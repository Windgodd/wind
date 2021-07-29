package com.universal.wind.movie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * 标签列表适配器
 */
class VideoFragmentsAdapter(var fragments:ArrayList<Fragment>,var fm:FragmentManager): FragmentStatePagerAdapter(fm) {


    private val titles = arrayListOf("首页", "电影", "电视剧", "动漫")

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }




}