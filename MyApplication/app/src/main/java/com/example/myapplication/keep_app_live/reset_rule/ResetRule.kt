package com.example.myapplication.keep_app_live.reset_rule

import android.content.Context
import android.util.Log
import com.example.myapplication.keep_app_live.KeepAppLive.keepAppLive
import java.util.Calendar


object ResetRule {

    fun handleLogic(context : Context){
        if(getLastExecuteTime(context) <= 0){
            //设置上一个执行的时间
            setLastExecuteTime(context, Calendar.getInstance().timeInMillis)
            return
        }
        val timeDiff = Calendar.getInstance().timeInMillis - getLastExecuteTime(context)
        if (timeDiff <= 30*1000 || timeDiff >= 3*60*1000) {
            Log.e("ATU", "Worker 杀死所有定时Work重新来")
            keepAppLive(context)
            return
        }
        //设置上一个执行的时间
        setLastExecuteTime(context, Calendar.getInstance().timeInMillis)
    }

    fun handleInit(context : Context){
        setLastExecuteTime(context, 0)
    }

    const val KEY_LAST_EXECUTE_TIME = "KEY_LAST_EXECUTE_TIME"
    private fun getLastExecuteTime(context : Context) : Long{
        return  SPUtils.getInstance(
            context
        ).getLong(KEY_LAST_EXECUTE_TIME, Calendar.getInstance().timeInMillis)
    }

    private fun  setLastExecuteTime(context: Context, time:Long){
        SPUtils.getInstance(
            context
        ).put(KEY_LAST_EXECUTE_TIME, time)
    }
}