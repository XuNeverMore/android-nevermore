package com.nevermore.device.info.utils

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.content.ContextCompat.getSystemService

/**
 *
 * @author xct
 * create on: 2022/9/19 17:43
 *
 */
object DeviceUtil {

    /**
     * 获取IP
     */
    fun getIP(context: Context): String {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        return (ipAddress and 0xff).toString() + "." + (ipAddress shr 8 and 0xff) + "." + (ipAddress shr 16 and 0xff) + "." + (ipAddress shr 24 and 0xff)
    }


    fun getMetrics(context: Context): DisplayMetrics {
        val metrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.display?.getRealMetrics(metrics)
        } else {
            val windowManager = getSystemService(context, WindowManager::class.java)
            windowManager?.defaultDisplay?.getRealMetrics(metrics)
        }
        return metrics
    }

}