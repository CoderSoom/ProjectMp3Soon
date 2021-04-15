package com.example.recyclerviewpool.playmusic.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.playmusic.PlayMusic


class PlayService : Service(), PlayMusic.IPlayMusic {

    var imgSong: Bitmap? = null

    private lateinit var playMusic: PlayMusic
    var inter: PlayMusic.IPlayMusic? = null

    var currentPositionSong = 0

    @SuppressLint("InvalidWakeLockTag")

    override fun onCreate() {
        super.onCreate()
        playMusic = PlayMusic()
        playMusic.inter = this

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {

        return MyBinder(this)
    }

    class MyBinder : Binder {
        val service: PlayService

        constructor(service: PlayService) {
            this.service = service
        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    fun setDataMusicOnline(item: ItemSong, position: Int, itemSize: MutableList<ItemSong>) {
        playMusic.setDataSource(this, item.linkMusic!!)
        createNotification(this, item,
            R.drawable.ic_pause_black_24dp, position, itemSize.size - 1)
        playMusic.setOnCompletionListener()

    }
    fun setDataMusicOffline(path: String){
        playMusic.setDataOffline(path)
    }




    fun createNotification(
        context: Context,
        itemSong: ItemSong,
        playbutton: Int,
        pos: Int,
        size: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mediaSessionCompat = MediaSessionCompat(context, "tag")

            Glide.with(context)
                .asBitmap()
                .load(itemSong.imgSong)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imgSong = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }


                })

            //Previous
            val pendingIntentPrevious: PendingIntent?
            val action_previus: Int
            if (pos == 0) {
                pendingIntentPrevious = null
                action_previus = 0
            } else {
                val intentPrevious = Intent(context, NotificationActionService::class.java)
                    .setAction(CreateNotification.ACTION_PREVIUOS)
                pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                    intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT)
                action_previus = R.drawable.ic_skip_previous_black_24dp
            }

            //Play
            val intentPlay = Intent(context, NotificationActionService::class.java)
                .setAction(CreateNotification.ACTION_PLAY)
            val pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                intentPlay, PendingIntent.FLAG_UPDATE_CURRENT)


            ///Next
            val pendingIntentNext: PendingIntent?
            val action_next: Int
            if (pos == size) {
                pendingIntentNext = null
                action_next = 0
            } else {
                val intentNext = Intent(context, NotificationActionService::class.java)
                    .setAction(CreateNotification.ACTION_NEXT)
                pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                    intentNext, PendingIntent.FLAG_UPDATE_CURRENT)
                action_next = R.drawable.ic_skip_next_black_24dp

            }

            //Close
            val pendingIntentClose: PendingIntent?
            val action_close: Int

            val intentClose = Intent(context, NotificationActionService::class.java)
                .setAction(CreateNotification.ACTION_CLOSE)
            pendingIntentClose = PendingIntent.getBroadcast(context, 0,
                intentClose, PendingIntent.FLAG_UPDATE_CURRENT)
            action_close = R.drawable.baseline_close_black_24dp



            //create notification

            CreateNotification.notification = NotificationCompat.Builder(context,
                CreateNotification.CHANNEL_ID)

                .setSmallIcon(R.drawable.icon_music)
                .setContentTitle(itemSong.nameSong)
                .setLargeIcon(imgSong)
                .setContentText(itemSong.singerSong)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .setPriority(Notification.PRIORITY_MAX)
                .addAction(action_previus, "Previous", pendingIntentPrevious)
                .addAction(playbutton, "Play", pendingIntentPlay)
                .addAction(action_next, "Next", pendingIntentNext)
                .addAction(action_close, "Close", pendingIntentClose)
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2, 3)
                    .setMediaSession(mediaSessionCompat.sessionToken))
                .setOngoing(true)
                .build()
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, CreateNotification.notification)
            startForeground(1, CreateNotification.notification!!)


        }


    }


    fun getTotalTime(): Int {
        return playMusic.total
    }

    fun getCurrentPosition(): Int {
        return playMusic.getCurrentPosition()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setSeekTo(progress: Int, seekNextSync: Int) {
        playMusic.getSeekTo(progress, seekNextSync)
    }

    override fun onPrepared() {
        inter?.onPrepared()
    }

    override fun setOnCompletionListener() {
        inter?.setOnCompletionListener()
    }
    fun setLooping(enable: Boolean){
        playMusic.setLooping(enable)
    }


    fun releaseMusic() {
        playMusic.release()
    }

    fun pauseMusic() {
        playMusic.pause()
    }

    fun playMusic() {
        playMusic.play()
    }

    override fun onDestroy() {
        super.onDestroy()


    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        stopSelf()
    }


}