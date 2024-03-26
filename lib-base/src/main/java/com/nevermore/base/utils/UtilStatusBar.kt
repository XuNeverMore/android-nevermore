package com.nevermore.base.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * https://developer.android.google.cn/develop/ui/views/layout/edge-to-edge
 * <pre>
 *      author: xuchuanting
 *      create on: 2022/3/28 13:48
 *      description:
 *</pre>
 *
 */
object UtilStatusBar {

    private var statusBarColor = Color.BLACK

    fun setTranslucentStatus(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = window.statusBarColor
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    fun clearTranslucentStatus(activity: Activity) {
        val window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility -= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = statusBarColor
    }


    //enable 浅色状态栏，黑色字体
    fun setLightStatusBar(activity: Activity, enable: Boolean) {

        val window = activity.window
        val controller: WindowInsetsControllerCompat =
            WindowCompat.getInsetsController(window, window.decorView)
        controller.isAppearanceLightStatusBars = enable

//        val decorView = activity.window.decorView
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val systemUiVisibility = decorView.systemUiVisibility
//
//
//
//            val hasLightFlag = systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR != 0
//            if (enable) {
//                if (!hasLightFlag) {
//                    decorView.systemUiVisibility =
//                        systemUiVisibility + View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//                }
//            } else {
//                if (hasLightFlag) {
//                    decorView.systemUiVisibility =
//                        systemUiVisibility - View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//                }
//            }
//        }
    }


    /**
     * 设置底部导航栏颜色
     * @param window
     * @param color
     */
    fun setNavigationBarColor(window: Window, @ColorInt color: Int) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.navigationBarColor = color
    }

    /**
     * 全屏
     */
    fun setFullScreenMode(window: Window, enable: Boolean) {
        val controller: WindowInsetsControllerCompat =
            WindowCompat.getInsetsController(window, window.decorView)
        when (enable) {
            true -> {
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
            false -> {
                controller.show(WindowInsetsCompat.Type.systemBars())
            }
        }

    }

    /**
     * 沉浸模式
     * view填充状态栏，可以显示状态栏信息
     */
    fun setImmersiveMode(window: Window, enable: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, enable.not())
    }
}