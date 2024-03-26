package com.qyqy.ucoo.utils.statistic

interface OnReportCallback {
    /**
     * 进入统计对象
     */
    fun onEnterReportObj(obj: ReportObj)

    /**
     * 离开统计对象,若不统计时长，[durationStay] 为 0
     */
    fun onExitReportObj(obj: ReportObj, durationStay: Long)
}