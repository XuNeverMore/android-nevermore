package com.qyqy.ucoo.utils.statistic

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nevermore.demo.statistic.EmptyActivityLifecycleCallbacks

object StatisticReporter : StatisticTracker {

    private var mCallback: OnReportCallback? = null
    private var mTracker: StatisticTracker? = null


    override fun track(eventName: String, extras: List<Pair<String, Any>>) {
        mTracker?.track(eventName, extras)
    }

    fun init(app: Application, tracker: StatisticTracker, callback: OnReportCallback) {
        mCallback = callback
        mTracker = tracker
        app.registerActivityLifecycleCallbacks(object : EmptyActivityLifecycleCallbacks() {

            override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityPreCreated(activity, savedInstanceState)
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityCreated(activity, savedInstanceState)

            }

            override fun onActivityResumed(activity: Activity) {
                super.onActivityResumed(activity)

            }

            override fun onActivityStopped(activity: Activity) {
                super.onActivityStopped(activity)
            }

            override fun onActivityPaused(activity: Activity) {
                super.onActivityPaused(activity)

            }

            override fun onActivityDestroyed(activity: Activity) {
                super.onActivityDestroyed(activity)
            }
        })
    }


    private fun onReportObjResumed(reportObj: ReportObj) {
        mCallback?.onEnterReportObj(reportObj)
        if (reportObj.isReportStayDuration()) {
            ViewModelProvider(reportObj)[ReportViewModel::class.java].onResumed()
        }
    }


    private fun onReportObjPaused(reportObj: ReportObj) {
        if (reportObj.isReportStayDuration()) {
            val reportViewModel = ViewModelProvider(reportObj)[ReportViewModel::class.java]
            reportViewModel.onPaused()
            //时长上报
            val duration = reportViewModel.duration
            mCallback?.onExitReportObj(reportObj, duration)
        } else {
            mCallback?.onExitReportObj(reportObj, 0L)
        }
    }


}

class ReportViewModel : ViewModel() {

    var duration: Long = 0L //nano time
        private set

    private var lastResumeTime = 0L
    fun onResumed() {
        lastResumeTime = System.nanoTime()
    }

    fun onPaused() {
        duration = (System.nanoTime() - lastResumeTime)
    }
}