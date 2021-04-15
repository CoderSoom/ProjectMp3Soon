package com.example.recyclerviewpool.playmusic

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.PowerManager
import androidx.annotation.RequiresApi

class PlayMusic : MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener,
    MediaPlayer.OnBufferingUpdateListener {
    var player: MediaPlayer? = null
    var inter: IPlayMusic? = null
    var total = 0
    var volume = 1f
    var speed = 0.5f

    fun FadeOut(detailTime: Float) {
        player!!.setVolume(volume, volume)
        volume -= speed * detailTime
    }

    fun FadeIn(detailTime: Float) {
        player!!.setVolume(volume, volume)
        volume += speed * detailTime
    }

    fun setDataOffline(path: String) {
        player = MediaPlayer()
        player?.setOnErrorListener(this)
        player?.setDataSource(path)
        player?.prepare()

    }

    fun setDataSource(context: Context, link: String) {

        player = MediaPlayer()
        player!!.setOnErrorListener(this)
        player!!.setOnBufferingUpdateListener(this)
        player!!.setDataSource(context, Uri.parse(link))

        player!!.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK)
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiLock: WifiManager.WifiLock =
            wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock")

        wifiLock.acquire()
        player!!.setOnPreparedListener(this)
        player!!.prepareAsync()


    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        return true
    }


    override fun onPrepared(mp: MediaPlayer) {
        total = mp.duration
        inter?.onPrepared()

        mp.start()
    }

    fun setOnCompletionListener() {
        player!!.setOnCompletionListener {
            inter?.setOnCompletionListener()
        }
    }


    fun setLooping(enable: Boolean) {
        player!!.isLooping = enable
    }

    fun getTotalTime(): Int {
        return player!!.duration
    }


    fun getCurrentPosition(): Int {
        return player!!.currentPosition
    }

    fun play() {
        if (player == null) {
            return
        }
        if (player!!.isPlaying) {
            return
        }
        player!!.start()
    }

    fun stop() {
        if (player == null) {
            return
        }
        player!!.stop()

    }

    fun pause() {
        if (player == null) {
            return
        }
        player!!.pause()
    }

    fun release() {
        player?.release()
        player = null
    }

    override fun onBufferingUpdate(mp: MediaPlayer, percent: Int) {
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getSeekTo(progress: Int, seekNextSync: Int) {
        if (player != null || player!!.isPlaying)
            player!!.seekTo(progress.toLong(), seekNextSync)

    }

    interface IPlayMusic {
        fun onPrepared()
        fun setOnCompletionListener()
    }


}