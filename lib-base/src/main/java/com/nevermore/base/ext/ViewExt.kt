package com.nevermore.base.ext

import android.graphics.Outline
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import com.nevermore.base.utils.dip

/**
 * <pre>
 *     @author xuchuanting
 *     create on 2021/8/31 16:28
 *     description:
 * </pre>
 */

fun View.setClipCorner(cornerDp: Float) {
    post {
        clipToOutline = true
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(
                    0,
                    0,
                    measuredWidth,
                    measuredHeight,
                    view.dip(cornerDp).toFloat()
                )
            }
        }
    }
}


fun View.withMatchLayoutParams() {
    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
}

fun View.withWrapContentLayoutParams() {
    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
}