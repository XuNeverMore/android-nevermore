package com.nevermore.demo.statistic

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

abstract class EmptyActivityLifecycleCallbacks:ActivityLifecycleCallbacks {
    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPreCreated(activity, savedInstanceState)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityPostCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPostCreated(activity, savedInstanceState)
    }

    override fun onActivityPreStarted(activity: Activity) {
        super.onActivityPreStarted(activity)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityPostStarted(activity: Activity) {
        super.onActivityPostStarted(activity)
    }

    override fun onActivityPreResumed(activity: Activity) {
        super.onActivityPreResumed(activity)
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPostResumed(activity: Activity) {
        super.onActivityPostResumed(activity)
    }

    override fun onActivityPrePaused(activity: Activity) {
        super.onActivityPrePaused(activity)
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityPostPaused(activity: Activity) {
        super.onActivityPostPaused(activity)
    }

    override fun onActivityPreStopped(activity: Activity) {
        super.onActivityPreStopped(activity)
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityPostStopped(activity: Activity) {
        super.onActivityPostStopped(activity)
    }

    override fun onActivityPreSaveInstanceState(activity: Activity, outState: Bundle) {
        super.onActivityPreSaveInstanceState(activity, outState)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityPostSaveInstanceState(activity: Activity, outState: Bundle) {
        super.onActivityPostSaveInstanceState(activity, outState)
    }

    override fun onActivityPreDestroyed(activity: Activity) {
        super.onActivityPreDestroyed(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivityPostDestroyed(activity: Activity) {
        super.onActivityPostDestroyed(activity)
    }
}