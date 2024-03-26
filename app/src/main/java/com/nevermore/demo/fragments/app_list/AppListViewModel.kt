package com.nevermore.demo.fragments.app_list

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevermore.base.ContextHolder
import com.nevermore.base.utils.UtilStarter
import com.nevermore.demo.base.Loading
import com.nevermore.demo.base.PageState
import com.nevermore.demo.base.Success
import com.nevermore.demo.core.AppInfoLoader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 *
 * @author: xct
 * create on: 2022/8/26 11:11
 *
 */

data class AppItem(
    val appName: String,
    val appIcon: Drawable,
    val onItemClick: (context: Context) -> Unit
)

class AppListViewModel : ViewModel() {



    private val _appList: MutableStateFlow<PageState<List<AppItem>>> = MutableStateFlow(Loading)

    val appList = _appList.asStateFlow()


    init {
        refreshAppList()
    }


    fun onPackageChanged() {
        refreshAppList()
    }


    private fun refreshAppList() {
        viewModelScope.launch {
            val loadInstalledApp = AppInfoLoader.loadInstalledApp(ContextHolder.ctx, true)
            _appList.update {
                Success(loadInstalledApp.map { appInfo ->
                    AppItem(appInfo.label, appInfo.icon) { ctx ->
                        AlertDialog.Builder(ctx)
                            .setTitle("其他信息")
                            .setItems(
                                arrayOf(
                                    appInfo.label,
                                    appInfo.packageName,
                                    "version:${appInfo.versionName}",
                                    "install time:${appInfo.installTime}"
                                ), null
                            )
                            .setNegativeButton("打开App") { d, w ->
                                ctx.packageManager.getLaunchIntentForPackage(appInfo.packageName)
                                    ?.let {
                                        ctx.startActivity(it)
                                    }
                            }
                            .setPositiveButton("打开设置") { dialog, witch ->
                                UtilStarter.toAppSettingsPage(ctx, appInfo.packageName)
                            }.show()
                    }
                })
            }
        }
    }



}