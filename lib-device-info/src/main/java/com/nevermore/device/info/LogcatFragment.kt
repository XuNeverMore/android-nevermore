package com.nevermore.device.info

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.nevermore.device.info.adb.AdbTools
import com.nevermore.device.info.adb.Logcat
import com.nevermore.device.info.adb.LogcatFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream

/**
 *  https://developer.android.google.cn/studio/command-line/logcat#filteringOutput
 * 读取logcat日志
 * @author xct
 * create on: 2022/9/21 11:00
 *
 */
class LogcatFragment : Fragment() {

    companion object{
        private const val KEY_NAME_LOGCAT = "name_logcat"
        private const val KEY_STORE_COMMAND = "store_command"
    }

    private val files = mutableListOf<File>()

    private var tool: AdbTools = Logcat
    private var currentCommand: String? = null

    private lateinit var tvLog: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var scrollView: ScrollView
    private var logcatJob: Job? = null
    private var sharedPreferences:SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logcat, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initViews(view)
        val context = view.context
        val sharedPreferences = context.getSharedPreferences(KEY_NAME_LOGCAT, Context.MODE_PRIVATE)
        currentCommand = sharedPreferences.getString(KEY_STORE_COMMAND,null)
        this.sharedPreferences = sharedPreferences
        currentCommand?.let {
            (requireActivity() as? DevToolsActivity)?.displayTitle(it)
            fetchLog(context)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_logcat_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        val context = requireContext()
        when (itemId) {
            R.id.action_select ->
                showSelectLogDialog(context)
            R.id.action_fetch -> fetchLog(context)
            R.id.action_clear -> tvLog.text = ""
            R.id.action_command -> changeCommand(context)
            R.id.action_delete -> clearAllLog(context)
            else -> {}
        }
        return true
    }

    private fun changeCommand(context: Context) {

        val editText = EditText(context)
        currentCommand?.let {
            editText.setText(it)
        }
        AlertDialog.Builder(context)
            .setTitle("请输入logcat命令")
            .setView(editText)
            .setPositiveButton(R.string.text_confirm) { d, w ->
                val activity = requireActivity() as? DevToolsActivity
                val command = editText.text.toString().trim()
                sharedPreferences?.run {
                    edit().putString(KEY_STORE_COMMAND,command).apply()
                }
                activity?.displayTitle(command.also { currentCommand = it })
                fetchLog(context)
            }
            .setNegativeButton(R.string.text_cancel, null)
            .show()
    }

    private fun initViews(view: View) {
        tvLog = view.findViewById(R.id.tv_log)
        tvLog.setTextIsSelectable(true)
        scrollView = view.findViewById(R.id.scroll_view)
        progressBar = view.findViewById(R.id.progress_bar)
    }

    private fun clearAllLog(context: Context) {
        tool.getFiles(context)?.forEach {
            it.delete()
        }
        tvLog.text = ""
    }

    private fun fetchLog(context: Context) {
        view ?: return
        tvLog.text = ""
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                logcatJob?.cancel()
            } catch (e: Exception) {
            }
            val lineCallback = { text: CharSequence ->
                tvLog.post {
                    tvLog.append(text)
                    scrollView.fullScroll(View.FOCUS_DOWN)
                }
                Unit
            }
            logcatJob = currentCommand?.let {
                tool.exec(context, this, it, lineCallback)
            } ?: tool.exec(context, this, lineCallback = lineCallback)

        }
    }

    private fun showSelectLogDialog(context: Context) {
        val logFiles = tool.getFiles(context)
        if (logFiles.isNullOrEmpty()) {
            Toast.makeText(context, "暂无日志，请先获取", Toast.LENGTH_SHORT).show()
            return
        }
        logFiles.sortByDescending { it.lastModified() }
        AlertDialog.Builder(context)
            .setTitle("选择日志")
            .setItems(
                logFiles.map { it.name }.toTypedArray()
            ) { _: DialogInterface?, which: Int ->
                showFileContent(logFiles[which])
            }
            .show()
    }


    private fun showFileContent(item: File) {
        try {
            logcatJob?.cancel()
        } catch (e: Exception) {
        }
        progressBar.isVisible = true
        viewLifecycleOwner.lifecycleScope.launch {
            tvLog.apply {
                val fileContent = getFileContent(item)
                text = ""
                text = fileContent
            }
            progressBar.isVisible = false
        }

    }

    private suspend fun getFileContent(file: File): CharSequence? = withContext(Dispatchers.IO) {
        var text: CharSequence? = null
        var inputStream: FileInputStream? = null
        try {
            inputStream = FileInputStream(file)
            text =
                inputStream.reader().readLines().let { LogcatFormatter.formatLogText(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }
        text
    }

}