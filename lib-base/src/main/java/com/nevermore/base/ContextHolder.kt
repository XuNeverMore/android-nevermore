package com.nevermore.base

import android.annotation.SuppressLint
import android.content.Context

/**
 *
 * @author xuchuanting
 * create on 2021/9/13 18:29
 * description:
 *
 */
@SuppressLint("StaticFieldLeak")
object ContextHolder {
    lateinit var ctx: Context
}