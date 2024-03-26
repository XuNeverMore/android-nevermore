package com.nevermore.demo

import android.content.Intent
import android.graphics.pdf.PdfDocument.Page
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import java.net.URLEncoder


data class PageInfo(
    val name: String,
    @IdRes val destId: Int = 0,
    val onClick: (() -> Unit)? = null
)

private const val TAG = "MainFragment"

class MainFragment : PreferenceFragmentCompat() {

    private val pages = mutableListOf(
//        PageInfo("设备信息", destId = R.id.deviceInfoFragment),
        PageInfo("Drawables", destId = R.id.drawablesFragment),
        PageInfo("MultiTypeItem", destId = R.id.multiTypeItemFragment),
        PageInfo("Widgets", destId = R.id.widgetsFragment),
        PageInfo("投屏Setting", destId = 0) {
            val context = requireContext()
            val intent = Intent(Settings.ACTION_CAST_SETTINGS)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            context.startActivity(intent)
        },
        PageInfo("通知", destId = R.id.notificationFragment),
        PageInfo("DevTools", destId = 0) {
            val context = requireContext()
            val intent = Intent("${context.packageName}.nevermore.devtools")
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            context.startActivity(intent)
        },
        PageInfo("权限申请", destId = R.id.permissionFragment),
        PageInfo("每日诗词", destId = R.id.poemFragment),
        PageInfo("系统状态栏", destId = R.id.statusBarFragment),
        PageInfo("WebView加载本地资源", destId = R.id.webViewFragment),
        PageInfo("协调布局", destId = R.id.action_global_scrollingFragment),
        PageInfo("Installed App", destId = R.id.appListFragment),

        PageInfo("支付宝健康码", destId = 0) {
            val path = "client_type=2";
            val link = URLEncoder.encode(path, "UTF-8");
            val url: String =
                "alipays://platformapi/startapp?appId=2021001135679870"
            val uri = Uri.parse(url);
            val intent = Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
//            val context = requireContext()
//            context.packageManager.queryIntentActivities()
//            val intent = Intent("com.tencent.wmpf.action.WMPF_SCAN_CODE")
//            intent.addCategory(Intent.CATEGORY_DEFAULT)
//            context.startActivity(intent)
        },
        PageInfo("支付宝扫一扫", destId = 0) {
            val path = "client_type=2";
            val link = URLEncoder.encode(path, "UTF-8");
            val url: String =
                "alipayqr://platformapi/startapp?saId=10000007"
            val uri = Uri.parse(url);
            val intent = Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        },
        PageInfo("蓝牙鼠标", R.id.bluetoothMouseFragment)
//        PageInfo("Logcat 读取", destId = R.id.logcatFragment),
    )

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)
        pages.forEachIndexed { index, pageInfo ->
            screen.addPreference(Preference(context).apply {
                isIconSpaceReserved = false
                key = "key_$index"
                title = pageInfo.name
                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    pageInfo.onClick?.invoke()
                    val resId = pageInfo.destId
                    if (resId != 0) {
                        findNavController().navigate(resId)
                    }
                    return@OnPreferenceClickListener true
                }
            })

        }
        preferenceScreen = screen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }


}