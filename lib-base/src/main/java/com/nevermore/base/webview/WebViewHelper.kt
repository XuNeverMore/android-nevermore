package com.nevermore.base.webview

import android.webkit.WebView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 *
 * @author: xct
 * create on: 2022/8/17 14:28
 *
 */

fun bindWebViewToLifecycle(webView: WebView, lifecycle: Lifecycle) {

    lifecycle.addObserver(object : DefaultLifecycleObserver {

        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
        }

        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
        }

        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            webView.onResume()
        }

        override fun onPause(owner: LifecycleOwner) {
            super.onPause(owner)
            webView.onPause()
        }

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            webView.destroy()
            lifecycle.removeObserver(this)
        }
    })
}

