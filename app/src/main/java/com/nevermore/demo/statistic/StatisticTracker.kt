package com.qyqy.ucoo.utils.statistic

interface StatisticTracker {

    fun track(eventName: String) {
        track(eventName, emptyList())
    }

    fun track(eventName: String, extras: List<Pair<String, Any>>)
}