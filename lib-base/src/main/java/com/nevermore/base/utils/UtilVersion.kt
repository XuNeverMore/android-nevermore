package com.nevermore.base.utils

import android.os.Build

/**
 *
 * @author xuchuanting
 * create on 2021/9/6 11:22
 * description:
 *
 */
object UtilVersion {

    @JvmStatic
    fun getSDKInt(): Int {
        return Build.VERSION.SDK_INT
    }

}