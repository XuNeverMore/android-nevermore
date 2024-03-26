package com.nevermore.demo.utils

import android.util.Log

object LogUtil {

    private val enable = true

    fun log(
        level: Int,
        msg: String,
        tag: String = "LogUtil",
        dividerTop: Boolean = false,
        dividerBottom: Boolean = false
    ) {
        if (!enable) {
            return
        }
        if (dividerTop) {
            Log.e(tag, "-------------------------------------")
        }
        val outPutMsg = "[${Thread.currentThread()}] $msg"
        when (level) {
            Log.DEBUG -> {
                Log.d(tag, outPutMsg)
            }

            Log.INFO -> {
                Log.i(tag, outPutMsg)
            }

            Log.WARN -> {
                Log.w(tag, outPutMsg)
            }

            Log.ERROR -> {
                Log.e(tag, outPutMsg)
            }

            else -> {}
        }
        if (dividerBottom) {
            Log.e(tag, "-------------------------------------")
        }
    }

}

fun LogUtil.d(
    msg: String,
    tag: String = "LogUtil",
    dividerTop: Boolean = false,
    dividerBottom: Boolean = false
) {
    log(Log.DEBUG, msg, tag, dividerTop, dividerBottom)
}

fun LogUtil.i(
    msg: String,
    tag: String = "LogUtil",
    dividerTop: Boolean = false,
    dividerBottom: Boolean = false
) {
    log(Log.INFO, msg, tag, dividerTop, dividerBottom)
}

fun LogUtil.w(
    msg: String,
    tag: String = "LogUtil",
    dividerTop: Boolean = false,
    dividerBottom: Boolean = false
) {
    log(Log.WARN, msg, tag, dividerTop, dividerBottom)
}

fun LogUtil.e(
    msg: String,
    tag: String = "LogUtil",
    dividerTop: Boolean = false,
    dividerBottom: Boolean = false
) {
    log(Log.ERROR, msg, tag, dividerTop, dividerBottom)
}

interface LogTagOwner {
    val logTag: String
}

fun LogTagOwner.logd(
    msg: String,
    dividerTop: Boolean = false,
    dividerBottom: Boolean = false
) = LogUtil.d(msg, logTag, dividerTop, dividerBottom)

fun LogTagOwner.logi(
    msg: String,
    dividerTop: Boolean = false,
    dividerBottom: Boolean = false
) = LogUtil.i(msg, logTag, dividerTop, dividerBottom)

fun LogTagOwner.logw(
    msg: String,
    dividerTop: Boolean = false,
    dividerBottom: Boolean = false
) = LogUtil.w(msg, logTag, dividerTop, dividerBottom)

fun LogTagOwner.loge(
    msg: String,
    dividerTop: Boolean = false,
    dividerBottom: Boolean = false
) = LogUtil.e(msg, logTag, dividerTop, dividerBottom)