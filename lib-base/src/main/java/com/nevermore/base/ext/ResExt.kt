package com.nevermore.base.ext
import androidx.core.content.ContextCompat
import com.nevermore.base.ContextHolder

/**
 * <pre>
 *     @author: xuchuanting
 *     create on 2021/8/25 14:28
 *     description: res工具类
 * </pre>
 */




/**
 * 获取string
 */
fun Int.getString(): String = ContextHolder.ctx.getString(this)

/**
 * 获取color
 */
fun Int.getColor(): Int = ContextCompat.getColor(ContextHolder.ctx, this)

/**
 * 获取string array
 */
fun Int.getStringArray(): Array<String> = ContextHolder.ctx.resources.getStringArray(this)

//fun Float.toPx():Int = DisplayUtils.dp2px(ContextHolder.context, this)