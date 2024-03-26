package com.nevermore.demo

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.jinrishici.sdk.android.factory.JinrishiciFactory
import com.nevermore.base.ContextHolder


/**
 * <pre>
 *      author: xuchuanting
 *      create on: 2022/4/21 15:32
 *      description:
 *</pre>
 *
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextHolder.ctx = this
        registerLifecycleCallback()

//        initX5WebView()
        JinrishiciFactory.init(this);
    }

    private fun registerLifecycleCallback() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                val title = activity.intent.getStringExtra("title")
                if (activity is FragmentActivity) {
                    activity.actionBar?.title = title
                }
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }
}