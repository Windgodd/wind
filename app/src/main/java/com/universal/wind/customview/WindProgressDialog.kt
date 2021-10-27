package com.universal.wind.customview

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.universal.wind.R
import java.util.*

/**
 * @author wind
 * 进度弹窗
 */
class WindProgressDialog(context:Context):Dialog(context) {

    private var strOverTimeTip: String? = null

    fun showDialog(tipContent:String,strOverTimeTip:String){
        this.strOverTimeTip = strOverTimeTip
        val view = LayoutInflater.from(this.context).inflate(R.layout.dialog_progress,null)
        val tvTipContent = view.findViewById<TextView>(R.id.tv_tip_content)
        val progressBar: ProgressBar = view.findViewById(R.id.pb_progress)
        tvTipContent.text = tipContent
        setContentView(view)
        /**
         * 开启定时器更新进度条
         * 定时器结束时回调
         */
        var timeCount = 1
        val timer = Timer()
//        timer.schedule (object:TimerTask(){
//            override fun run() {
//                timeCount ++
//                progressBar.post{
//                    progressBar.progress = timeCount
//                    Log.i("progress_dialog","进度-->"+timeCount)
//                }
//                if (timeCount == 10 ){
//                    this.cancel()
//                    ownerActivity?.runOnUiThread { overTime() }
//                }
//            }
//        },1000)
        show()
    }


    /**
     * 超时提示
     */
    fun overTime(){
        dismiss()
        if(!TextUtils.isEmpty(strOverTimeTip)){
            Toast.makeText(context,strOverTimeTip,Toast.LENGTH_SHORT).show()
        }
        ownerActivity?.finish()
    }

}