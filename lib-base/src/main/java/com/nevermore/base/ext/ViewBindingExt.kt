package com.nevermore.base.ext

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

/**
 *
 * @author xuchuanting
 * create on 2021/9/2 13:48
 * description:
 *
 */

inline fun <reified VB : ViewBinding> createViewBinding(layoutInflater: LayoutInflater): VB {
    val method = VB::class.java.getMethod("inflate", LayoutInflater::class.java)
    return method.invoke(null, layoutInflater) as VB
}