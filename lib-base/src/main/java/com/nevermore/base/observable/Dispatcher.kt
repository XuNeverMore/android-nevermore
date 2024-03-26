package com.nevermore.base.observable

/**
 * <pre>
 *     @author: xuchuanting
 *     create on 2021/8/30 14:29
 *     description:
 * </pre>
 */
fun interface Dispatcher<T> {
    fun onDispatchObserver(observer: T)
}