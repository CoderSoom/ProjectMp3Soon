package com.example.recyclerviewpool.model.itemdata

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "item_song")
data class ItemSong(

    @PrimaryKey
    var id: String,
    var linkMusic: String?,
    var imgSong: String,
    var nameSong: String,
    var singerSong: String,
    var linkSong: String,
    var quality: String?,
    var hoursAgo: String?,
    var views: String?,
    var time: String?,
    var keySearch: String
)

