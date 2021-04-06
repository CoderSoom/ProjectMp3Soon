package com.example.recyclerviewpool.model

import com.example.recyclerviewpool.model.itemdata.ItemCategories
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.model.itemdata.ItemMusicInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SongService {




    /****************************************
     *    GET NEW ALBUMS SONG, VIDEO
     ****************************************/

    @GET("/api/newAlbumsSong")
    fun albumsSong(): Observable<MutableList<ItemMusicList<ItemSong>>>

    @GET("/api/newAlbumsVideo")
    fun albumsVideo(): Observable<MutableList<ItemMusicList<ItemSong>>>

    /****************************************
     *    GET SONG IN ALBUMS
     ****************************************/

    @GET("/api/AlbumsChil")
    fun albumsChil(@Query("linkAlbums") linkAlbums: String): Observable<MutableList<ItemSong>>

    @GET("/api/AlbumsChilSinger")
    fun albumsChilSinger(@Query("linkSinger") linkAlbums: String): Observable<MutableList<ItemSong>>

    /****************************************
     *    GET INFO SONG AND VIDEO
     ****************************************/

    @GET("/api/getInfo")
    fun getInfo(@Query("link") link: String): Observable<ItemMusicInfo>

    /****************************************
     *    GET RELALE SONG AND VDIDEO
     ****************************************/

    @GET("/api/getMVSongs")
    fun getMVSong(@Query("linkSong") link: String):Observable<MutableList<ItemSong>>

    @GET("/api/getRelateSongs")
    fun getRelateSong(@Query("linkSong") link: String):Observable<MutableList<ItemMusicList<ItemSong>>>

    @GET("/api/getRelateVideos")
    fun getRelateVideo(@Query("linkRelate") link: String):Observable<MutableList<ItemSong>>


    /****************************************
     *    GET TOPRESULT
     ****************************************/

    @GET("/api/topResult")
    fun getTopResult():Observable<MutableList<ItemMusicList<ItemSong>>>



    /****************************************
     *    GET CATEGORIES
     ****************************************/

    @GET("/api/categoriesCountry")
    fun categoriesContry():Observable<MutableList<ItemMusicList<ItemCategories>>>

    @GET("/api/categoriesStatus")
    fun categoriesStatus():Observable<MutableList<ItemMusicList<ItemCategories>>>


    @GET("/api/themesStatus")
    fun chilCategoriesStatus(@Query("status")linkCategories: String):Observable<MutableList<ItemMusicList<ItemSong>>>


    /****************************************
     *    GET NEW SONG
     ****************************************/
    @GET("/api/newSongs")
    fun newSong():Observable<MutableList<ItemMusicList<ItemSong>>>


    /****************************************
     *    GET OUTSTANDING SINGER
     ****************************************/
    @GET("/api/outstandingSinger")
    fun outstandingSinger():Observable<MutableList<ItemMusicList<ItemCategories>>>


    /****************************************
     *    GET SONG SEARCH
     ****************************************/
    @GET("/api/searchSong")
    fun searchSong(@Query("nameSearch") nameSearch: String):Observable<MutableList<ItemMusicList<ItemSong>>>

    @GET("/api/searchVideos")
    fun searchVideos(@Query("nameSearch") nameSearch: String):Observable<MutableList<ItemMusicList<ItemSong>>>

    @GET("/api/searchAlbums")
    fun searchAlbums(@Query("nameSearch") nameSearch: String):Observable<MutableList<ItemMusicList<ItemSong>>>

    @GET("/api/mostSearched")
    fun mostSearched():Observable<MutableList<ItemMusicList<ItemSong>>>

    /****************************************
     *    GET RANKING
     ****************************************/

    @GET("/api/rankMusicCountry")
    fun getMusicRanking(@Query("country") country: String):Observable<MutableList<ItemSong>>

    @GET("/api/rankVideoCountry")
    fun getVideoRanking(@Query("country") country: String):Observable<MutableList<ItemSong>>

    @GET("/api/getSuggestions")
    fun getSug(@Query("linkAlbumsSong") link: String):Observable<MutableList<ItemMusicList<ItemSong>>>


}