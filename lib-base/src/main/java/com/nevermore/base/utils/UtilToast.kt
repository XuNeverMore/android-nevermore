package com.nevermore.base.utils

import android.widget.Toast
import androidx.annotation.StringRes
import com.nevermore.base.ContextHolder
import com.nevermore.base.ext.getString

/**
 *
 * @author xuchuanting
 * create on 2021/9/6 11:07
 * description:
 *
 */
object UtilToast {

    @JvmStatic
    fun toast(@StringRes stringResId: Int, duration: Int = Toast.LENGTH_SHORT) {
        toast(stringResId.getString(), duration)
    }

    @JvmStatic
    fun toast(string: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(ContextHolder.ctx, string, duration).show()
    }
}

fun String.toast(duration: Int = Toast.LENGTH_SHORT) {
    UtilToast.toast(this, duration)
}

fun Int.toast(duration: Int = Toast.LENGTH_SHORT) {
    UtilToast.toast(this, duration)
}