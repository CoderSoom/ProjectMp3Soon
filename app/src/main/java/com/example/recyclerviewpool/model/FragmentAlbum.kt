package com.example.recyclerviewpool.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FragmentAlbum {
    @SerializedName("linkMusic")
    @Expose
    var linkMusic: Any? = null

    @SerializedName("imgSong")
    @Expose
    var imgSong: String? = null

    @SerializedName("nameSong")
    @Expose
    var nameSong: String? = null

    @SerializedName("singerSong")
    @Expose
    var singerSong: String? = null

    @SerializedName("linkSong")
    @Expose
    var linkSong: String? = null

    @SerializedName("quality")
    @Expose
    var quality: Any? = null

    @SerializedName("hoursAgo")
    @Expose
    var hoursAgo: Any? = null

    @SerializedName("views")
    @Expose
    var views: Any? = null
}