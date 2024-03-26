package com.nevermore.device.info.adb

import android.content.Context
import android.os.Process
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.io.File

/**
 *
 * @author xct
 * create on: 2022/9/21 10:39
 *
 */
object Logcat : AdbTools() {

    private const val TAG = "Logcat"
    override val outputFolderName: String = "adb_logcat"
    override val defaultCmd: String = "adb logcat"

    override fun exec(
        context: Context,
        coroutineScope: CoroutineScope,
        cmd: String,
        lineCallback: (CharSequence) -> Unit
    ):Job {
        val file = getOutFolder(context)
        if (!file.exists()) {
            file.mkdirs()
        }
        val fileName = newFileName("log")
        val fileLog = File(file, fileName)
        fileLog.createNewFile()
        Log.i(TAG, "create log file:${fileLog.absolutePath}")
        val command = arrayOf("logcat", cmd)
        return executeCommand(coroutineScope,command, fileLog){
            lineCallback(LogcatFormatter.formatLogText(it))
        }
    }
}