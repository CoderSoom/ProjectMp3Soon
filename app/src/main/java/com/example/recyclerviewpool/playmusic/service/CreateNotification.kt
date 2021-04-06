package com.example.recyclerviewpool.playmusic.service

import android.app.Notification

object CreateNotification {
    var notification: Notification? = null
    val CHANNEL_ID = "channelsoom"
    const val ACTION_PREVIUOS = "actionprevious"
    const val ACTION_PLAY = "actionplay"
    const val ACTION_NEXT = "actionnext"
    const val ACTION_CLOSE = "actionclose"
}