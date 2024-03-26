package com.nevermore.device.info.adb

import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import java.io.Closeable
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author xct
 * create on: 2022/9/23 16:13
 *
 */
abstract class AdbTools {

    private val TAG: String = this::class.java.simpleName

    abstract val outputFolderName: String

    abstract val defaultCmd: String

    internal fun getOutFolder(context: Context): File {
        val file = File(context.filesDir, outputFolderName)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
    }


    fun getFiles(context: Context): Array<out File>? {
        val outFolder = getOutFolder(context)
        return outFolder.listFiles()
    }

    fun exec(context: Context, coroutineScope: CoroutineScope, cmd: String = defaultCmd): Job {
        return exec(context, coroutineScope, cmd, {})
    }

    abstract fun exec(
        context: Context,
        coroutineScope: CoroutineScope,
        cmd: String = defaultCmd,
        lineCallback: (CharSequence) -> Unit
    ): Job

    fun executeCommand(
        coroutineScope: CoroutineScope,
        commandArray: Array<String>,
        outputFile: File,
        callback: (CharSequence) -> Unit = {}
    ): Job {
        return coroutineScope.launch(Dispatchers.IO) {
            Log.i(TAG, "executeCommand: ${commandArray.contentToString()}")
            val exec: Process = Runtime.getRuntime().exec(commandArray)
            var outputStream: Closeable? = null
            try {
                val writer = FileOutputStream(outputFile).writer()
                outputStream = writer
                val reader = exec.inputStream.reader()
                reader.forEachLine {
                    writer.write(it, 0, it.length)
                    writer.write("\n")
                    callback(it)
                }
                val errorReader = exec.errorStream.reader()
                if (exec.waitFor() == 0) {
                    Log.i(TAG, "exec success!")
                } else {
                    val readText = errorReader.readText()
                    Log.i(TAG, "exec failed!\n$readText")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    outputStream?.close()
                } catch (e: Exception) {
                }
            }
        }

    }

    internal fun newFileName(prefix: String, extension: String = "txt"): String {
        val format = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        return "${prefix}_${format.format(Date())}.$extension"
    }

}