package com.universal.wind.main

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universal.wind.R
import com.universal.wind.adapter.FavCategoryAdapter
import com.universal.wind.adapter.ItemClickListener
import com.universal.wind.base.BaseActivity
import com.universal.wind.movie.view.VideoMainActivity

class MainActivity : BaseActivity<MainPresenter>() {

    private var listCategory: RecyclerView? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun initView() {
        listCategory = findViewById(R.id.list_category)
        val layoutManger = GridLayoutManager(this,2)
        val favCtAdapter = FavCategoryAdapter(this)
        listCategory!!.layoutManager = layoutManger
        listCategory!!.adapter = favCtAdapter
        favCtAdapter.notifyDataSetChanged()
        favCtAdapter.itemClickListener = object : ItemClickListener{
            override fun itemClickListener(position: Int, obj: Any?) {
                when (obj as String) {
                    "影视" -> {
                        val intent = Intent(this@MainActivity, VideoMainActivity::class.java)
                        startActivity(intent)
                    }
                }

            }
        }

    }

    override fun initData() {

    }




}
