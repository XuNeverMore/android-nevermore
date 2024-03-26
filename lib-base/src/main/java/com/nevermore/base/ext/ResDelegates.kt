package com.nevermore.base.ext

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

/**
 *
 * @author xuchuanting
 * create on 2021/9/2 14:08
 * description:
 *
 */

fun Context.delegateStringRes(@StringRes resId: Int): Lazy<String> = lazy { this.getString(resId) }

fun Context.delegateStringArray(@ArrayRes resId: Int): Lazy<Array<String>> =
    lazy { this.resources.getStringArray(resId) }

