package com.nevermore.device.info

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

/**
 *
 * @author xct
 * create on: 2022/9/23 17:23
 *
 */
class DevToolsActivity : AppCompatActivity(),PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            title = "DevTools"
            setBackgroundDrawable(ColorDrawable(Color.BLACK))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility -= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window?.statusBarColor = Color.BLACK
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, MySettingsFragment())
            .commitAllowingStateLoss()
    }

    fun displayTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {
        // Instantiate the new Fragment
        val args = pref.extras
        val fragmentClassName = pref.fragment?:return false
        val fragment = supportFragmentManager.fragmentFactory.instantiate(
            classLoader,
            fragmentClassName
        )
        fragment.arguments = args
        fragment.setTargetFragment(caller, 0)
        // Replace the existing Fragment with the new Fragment
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .addToBackStack(null)
            .commit()
        return true
    }
}