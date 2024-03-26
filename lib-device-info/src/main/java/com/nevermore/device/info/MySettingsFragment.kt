package com.nevermore.device.info

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen

/**
 *
 * @author xct
 * create on: 2022/9/23 17:25
 *
 */
class MySettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        val context = requireContext()
        preferenceScreen = preferenceManager.createPreferenceScreen(context).apply {

            val deviceInfo = Preference(context).apply {
                isIconSpaceReserved = false
                title = "设备信息"
                summary = "屏幕、硬件、IP等信息"
                fragment = DeviceInfoFragment::class.java.name

            }
            addPreference(deviceInfo)

            val logcat = Preference(context).apply {
                isIconSpaceReserved = false
                title = "Logcat工具"
                summary = "执行Logcat命令，输出到文件后查看"
                fragment = LogcatFragment::class.java.name
            }

            addPreference(logcat)
        }
    }

    override fun onStart() {
        super.onStart()
        val activity = requireActivity() as? DevToolsActivity
        activity?.displayTitle("DevTools")
    }
}