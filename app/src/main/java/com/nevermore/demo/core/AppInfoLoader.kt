package com.nevermore.demo.core

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.graphics.Color
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.palette.graphics.Palette
import com.nevermore.demo.bean.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author: xct
 * create on: 2022/8/26 10:55
 *
 */
object AppInfoLoader {


    suspend fun loadInstalledApp(context: Context, ignoreSystem: Boolean): List<AppInfo> =
        withContext(Dispatchers.IO) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val appList = mutableListOf<AppInfo>()
            val packageManager = context.packageManager

            val installedPackages: MutableList<PackageInfo> = packageManager.getInstalledPackages(0)
            installedPackages.forEach iter@{ pkgInfo ->

                val isSystemApp =
                    (pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0

                if (ignoreSystem && isSystemApp) {
                    return@iter
                }
                val icon: Drawable = pkgInfo.applicationInfo.loadIcon(packageManager)
                Log.i("xct", "" + icon)
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    when (icon) {
                        is BitmapDrawable -> {
                            icon.bitmap
                        }
                        is AdaptiveIconDrawable -> {
                            val background = icon.background
                            if (background is BitmapDrawable) {
                                background.bitmap
                            } else {
                                null
                            }
                        }
                        else -> null
                    }
                } else {
                    null
                }
                val color = if (bitmap != null) {
                    val palette = Palette.from(bitmap).generate()
                    palette.getLightVibrantColor(Color.TRANSPARENT)
                } else Color.TRANSPARENT

                appList.add(
                    AppInfo(
                        pkgInfo.packageName,
                        pkgInfo.versionName ?: "0.0.0",
                        packageManager.getApplicationLabel(pkgInfo.applicationInfo).toString(),
                        icon,
                        isSystemApp,
                        dateFormat.format(Date(pkgInfo.firstInstallTime)),
                        color
                    )
                )
            }
            appList.sortedBy { it.colorPrimary }
        }

}