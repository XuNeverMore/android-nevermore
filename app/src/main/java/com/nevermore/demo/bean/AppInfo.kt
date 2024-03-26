package com.nevermore.demo.bean

import android.graphics.drawable.Drawable

/**
 *
 * App 信息
 * @author: xct
 * create on: 2022/8/26 10:53
 *
 */
data class AppInfo(
    val packageName: String,
    val versionName: String,
    val label: String,
    val icon: Drawable,
    val systemApp:Boolean,
    val installTime:String,
    val colorPrimary:Int,
)