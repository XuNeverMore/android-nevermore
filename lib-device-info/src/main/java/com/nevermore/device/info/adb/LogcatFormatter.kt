package com.nevermore.device.info.adb

import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.annotation.ColorInt
import java.util.regex.Pattern

/**
 *
 * @author xct
 * create on: 2022/9/21 15:38
 *
 */
object LogcatFormatter {
    private val colorConfig: MutableMap<Int, Int> = mutableMapOf<Int, Int>(
        Log.VERBOSE to Color.BLUE,
        Log.ERROR to Color.RED,
        Log.WARN to 0XFFFFB300.toInt(),
        Log.INFO to Color.GRAY,
        Log.DEBUG to 0XFF81D4FA.toInt(),
        Log.ASSERT to Color.GRAY
    )


    /**
     * set log color
     * logLeve :[Log.VERBOSE] [Log.ERROR] [Log.WARN] [Log.INFO] [Log.DEBUG]
     */
    fun setLogColor(logLeve: Int, @ColorInt color: Int) {
        colorConfig[logLeve] = color
    }


    /**
     * 获取日志级别D V W E I A
     */
    fun getLogLevel(log: CharSequence): Int {
        val reg =
            "[0-9]{2}-[0-9]{2}\\s[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}\\s*[0-9]+\\s*[0-9]+\\s*[ADIVWE]"
        val compile = Pattern.compile(reg)
        val matcher = compile.matcher(log)
        if (matcher.find()) {
            val end = matcher.end()
            val substring = log.substring(0, end)
            val levelStr = substring[substring.lastIndex].toString()
            return when (levelStr) {
                "V" -> Log.VERBOSE
                "W" -> Log.WARN
                "E" -> Log.ERROR
                "I" -> Log.INFO
                "D" -> Log.DEBUG
                else -> Log.ASSERT
            }
        }

        return Log.VERBOSE
    }


    /**
     * 格式化日志输出颜色
     */
    fun formatLogText(logLines: List<String>): CharSequence {
        val ssb = SpannableStringBuilder()
        logLines.forEach {
            val logLevel = getLogLevel(it)
            val color = colorConfig.getOrElse(logLevel) { Color.GRAY }
            val ss = SpannableString(it).apply {
                setSpan(
                    ForegroundColorSpan(color),
                    0,
                    it.length,
                    SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
            ssb.append(ss).append("\n")
        }
        return ssb
    }

    fun formatLogText(log: CharSequence): CharSequence {
        val ssb = SpannableStringBuilder()
        val logLevel = getLogLevel(log)
        val color = colorConfig.getOrElse(logLevel) { Color.GRAY }
        val ss = SpannableString(log).apply {
            setSpan(
                ForegroundColorSpan(color),
                0,
                log.length,
                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
        ssb.append(ss).append("\n")
        return ssb
    }
}