package com.universal.wind.base

import android.app.Application
import com.shuyu.gsyvideoplayer.cache.CacheFactory
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager
import tv.danmaku.ijk.media.exo2.ExoPlayerCacheManager


class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initVideoConfig()

    }

    /**
     * 配置播放器全局变量
     */
    private fun initVideoConfig(){
        PlayerFactory.setPlayManager(Exo2PlayerManager::class.java)
        //exo缓存模式，支持m3u8，只支持exo
        CacheFactory.setCacheManager(ExoPlayerCacheManager::class.java)
    }
}