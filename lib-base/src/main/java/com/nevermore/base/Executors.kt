package com.nevermore.base

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * <pre>
 *     @author: xuchuanting
 *     create on 2021/8/24 19:22
 *     description:
 * </pre>
 */
object Executors {

    @JvmStatic
    val MAIN = MainThreadExecutor(Handler(Looper.getMainLooper()))

    @JvmStatic
    val IO: ExecutorService = Executors.newCachedThreadPool()


    class MainThreadExecutor(private val handler: Handler) : Executor {
        override fun execute(command: Runnable) {
            handler.post(command)
        }

        fun postDelay(time: Long, command: Runnable) {

            handler.postDelayed(command, time)
        }
    }
}

