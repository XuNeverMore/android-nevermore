package com.nevermore.base.utils

import android.content.Context
import android.content.pm.PackageManager

/**
 *
 * @author xuchuanting
 * create on 2021/9/6 10:55
 * description:
 *
 */
object UtilPermission {


    /**
     * 判断权限是否通过
     */
    @JvmStatic
    fun isPermissionGranted(context: Context, permission: String): Boolean {
        return context.packageManager.checkPermission(
            permission,
            context.packageName
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 返回未通过的权限
     */
    @JvmStatic
    fun getLackedPermissions(context: Context, permissions: List<String>): List<String> {
        return permissions.filter { permission ->
            return@filter isPermissionGranted(context, permission).not()
        }
    }
}