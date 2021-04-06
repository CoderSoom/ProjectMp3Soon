package com.example.recyclerviewpool.lyricsong

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Message
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.example.recyclerviewpool.model.itemlyric.LineInfo
import com.example.recyclerviewpool.model.itemlyric.LyricInfo
import java.util.*

class LyricManager(context: Context) {
    private var progressChangedListener: OnProgressChangedListener? = null
    private var selectedColor = Color.parseColor("#07FA81")
    private var normalColor = Color.parseColor("#FFFFFF")
    private var lyricInfo: LyricInfo? = null
    private var flag_refresh = false
    private var flag_position = 0
    var contentTest = ""
    var contentPOs = ""




    fun setLyricString(inputLyric: String?){
        detext(inputLyric!!)
    }


    fun detext(input: String){
        val scanner = Scanner(input)
        lyricInfo = LyricInfo()
        while (scanner.hasNextLine()) {
            val line: String = scanner.nextLine()
            analyzeLyric(line)        }
        scanner.close()
        setCurrentTimeMillis(0)
    }


    private fun analyzeLyric(line: String?) {
        val lineInfo = LineInfo()
        val index = line!!.lastIndexOf("]")
        if (line != null && index == 9 && line.trim { it <= ' ' }.length > 10) {
            lineInfo.content = line.substring(10, line.length)
            lineInfo.start = analyzeStartTimeMillis(line.substring(0, 10))
            lyricInfo!!.lines!!.add(lineInfo)
        }
    }


    private fun analyzeStartTimeMillis(str: String): Long {
        val minute = str.substring(1, 3).toLong()
        val second = str.substring(4, 6).toLong()
        val millisecond = str.substring(7, 9).toLong()
        return millisecond + second * 1000 + minute * 60 * 1000
    }


    fun setCurrentTimeMillis(timeMillis: Long) {
        try {
            val lines = if (lyricInfo != null) lyricInfo!!.lines else null
            if (lines != null) {
                Thread(object : Runnable {
                    override fun run() {
                        var position = 0
                        val stringBuilder = SpannableStringBuilder()
                        run {
                            var i = 0
                            val size = lines.size
                            while (i < size) {
                                position = if (lines[i].start < timeMillis) {
                                    i
                                } else {
                                    break
                                }
                                i++
                            }
                        }
                        if (position == flag_position && !flag_refresh) {
                            return
                        }
                        flag_position = position
                        var i = 0
                        val size = lines.size
                        while (i < size) {
                            if (i != position) {
                                val span = ForegroundColorSpan(normalColor)
                                val line = lines[i].content
                                val spannableString = SpannableString("""
    $line

    """.trimIndent())
                                spannableString.setSpan(span, 0, spannableString.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                                stringBuilder.append(spannableString)
                            } else {
                                val span = ForegroundColorSpan(selectedColor)
                                val line = lines[i].content
                                val spannableString = SpannableString("""
    $line

    """.trimIndent())
                                spannableString.setSpan(span, 0, spannableString.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                                stringBuilder.append(spannableString)
                            }
                            i++
                        }
                        val message = Message()
                        message.what = 0x159
                        val dataHolder = DataHolder()
                        dataHolder.builder = stringBuilder
                        dataHolder.position = position
                        dataHolder.refresh = flag_refresh
                        dataHolder.lines = lines
                        message.obj = dataHolder
                        handler.sendMessage(message)
                        if (flag_refresh) {
                            flag_refresh = false
                        }
                    }
                }).start()
            } else {
                val message = Message()
                message.what = 0x159
                val dataHolder = DataHolder()
                dataHolder.builder = null
                dataHolder.position = -1
                message.obj = dataHolder
                handler.sendMessage(message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("HandlerLeak")
    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val `object` = msg.obj
            if (`object` != null && `object` is DataHolder) {
                val dataHolder = `object`
                when (msg.what) {
                    0x159 -> if (null != progressChangedListener) {
                        progressChangedListener!!.onProgressChanged(dataHolder.builder, dataHolder.position, dataHolder.refresh)
                        if (dataHolder.lines != null) {
                            progressChangedListener!!.onProgressChanged(dataHolder.lines!![dataHolder.position].content, dataHolder.refresh)
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }


    internal inner class DataHolder {
        var builder: SpannableStringBuilder? = null
        var lines: List<LineInfo>? = null
        var refresh = false
        var position = 0
    }


    fun setOnProgressChangedListener(progressChangedListener: OnProgressChangedListener?) {
        this.progressChangedListener = progressChangedListener
    }


    interface OnProgressChangedListener {
        fun onProgressChanged(singleLine: String?, refresh: Boolean)
        fun onProgressChanged(stringBuilder: SpannableStringBuilder?, lineNumber: Int, refresh: Boolean)
    }


    fun setSelectedTextColor(color: Int) {
        if (color != selectedColor) {
            selectedColor = color
            flag_refresh = true
        }
    }

    fun setNormalTextColor(color: Int) {
        if (color != normalColor) {
            normalColor = color
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(context: Context): LyricManager {
            return LyricManager(context)
        }
    }
}