package com.qyqy.ucoo.utils.statistic

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner

interface ReportObj : LifecycleOwner, ViewModelStoreOwner {
    val reportKey: String

    //是否统计停留时长
    fun isReportStayDuration(): Boolean = false

    fun getReportExtras(): List<Pair<String, String>> {
        return emptyList()
    }
}