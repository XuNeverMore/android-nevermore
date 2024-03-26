package com.nevermore.demo.fragments.app_list

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 *
 * 安装 卸载 app 广播监听
 *
 * @author: xct
 * create on: 2022/8/26 15:09
 *
 */
class PackageChangeReceiver(val context:Context,val onPackageChanged:()->Unit) : BroadcastReceiver() ,DefaultLifecycleObserver{

    companion object{
        private const val TAG = "PackageChangeReceiver"
    }

    val actionsPackageChanged =
        mutableListOf(Intent.ACTION_PACKAGE_REMOVED, Intent.ACTION_PACKAGE_ADDED)


    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.i(TAG, "onCreate: ")
        context.registerReceiver(this, IntentFilter().apply {
            actionsPackageChanged.forEach {
                addAction(it)
            }
            addDataScheme("package")
        })
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.i(TAG, "onDestroy: ")
        context.unregisterReceiver(this)
        owner.lifecycle.removeObserver(this)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        onPackageChanged()
        Log.i(TAG, "onReceive: ")
    }
}