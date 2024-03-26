package com.nevermore.base.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build


/**
 *
 * @author xuchuanting
 * create on 2021/9/6 11:54
 * description:
 *
 */
object UtilStarter {


    /**
     * 打开app设置页面
     * [packageName] 为空则 打开自身
     */
    fun toAppSettingsPage(context: Context, packageName: String? = null) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 9) {
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.fromParts("package", packageName ?: context.packageName, null)
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.action = Intent.ACTION_VIEW
            intent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails")
            intent.putExtra(
                "com.android.settings.ApplicationPkgName",
                packageName ?: context.packageName
            )
        }
        context.startActivity(intent)
    }
}

inline fun <reified A : Activity> Activity.startPage(
    finishNow: Boolean = false,
    block: Intent.() -> Unit = {},
) {
    val intent = Intent(this, A::class.java)
    block(intent)
    startActivity(intent)
    if (finishNow) {
        finish()
    }
}

inline fun <reified A : Activity> Context.startPage(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, A::class.java).also(block)
    if (this !is Activity) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}