package com.nevermore.device.info.adb

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.io.File

/**
 *
 * @author xct
 * create on: 2022/9/23 16:09
 *
 */
object DumpSys : AdbTools() {

    override val outputFolderName: String = "adb_dumpsys"
    override val defaultCmd: String = "adb shell dumpsys meminfo"

    override fun exec(
        context: Context,
        coroutineScope: CoroutineScope,
        cmd: String,
        lineCallback: (CharSequence) -> Unit
    ): Job {
        val cmds = arrayOf("dumpsys", cmd)
        val file = File(getOutFolder(context), newFileName("dumpsys"))
        return executeCommand(coroutineScope, cmds, file, lineCallback)
    }
}