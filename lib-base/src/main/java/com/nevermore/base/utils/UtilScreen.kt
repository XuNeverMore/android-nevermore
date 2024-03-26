package com.nevermore.base.utils

import android.content.Context
import com.nevermore.base.ContextHolder
import kotlin.properties.ReadOnlyProperty

/**
 * <pre>
 *      author: xuchuanting
 *      create on: 2022/5/31 16:28
 *      description:
 *</pre>
 *
 */

val Context.smallestScreenWidthDp: Int by ReadOnlyProperty<Context, Int> {
        thisRef, _ -> thisRef.resources.configuration.smallestScreenWidthDp
}

val Context.density: Float
    get() = this.resources.displayMetrics.density

val Int.toPx: Float
    get() = (this / ContextHolder.ctx.density) * +0.5f


val Int.toDp: Int
    get() = (this * ContextHolder.ctx.density + 0.5).toInt()

val Context.screenWidthDp: Int
    get() = this.resources.configuration.screenWidthDp

val Context.screenHeightDp: Int
    get() = this.resources.configuration.screenHeightDp

val Context.screenWidthPx: Int
    get() = this.resources.displayMetrics.widthPixels

val Context.screenHeightPx: Int
    get() = this.resources.displayMetrics.heightPixels

val Context.isPortrait: Boolean
    get() = this.resources.configuration.orientation == 1

