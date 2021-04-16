package com.example.recyclerviewpool.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.recyclerviewpool.model.RetrofitUtils
import com.example.recyclerviewpool.model.SongService
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.model.itemdata.ItemSharedAlbums
import com.example.recyclerviewpool.model.itemdata.ItemSong
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchModel :ViewModel(){

    private var disPoMost:Disposable?=null
    private var disPolistSongs:Disposable?=null
    private var disPolistAlbums:Disposable?=null
    private var disPolistVideos:Disposable?=null
    private val songService =
        RetrofitUtils.createRetrofit("http://192.168.1.4:5000", SongService::class.java)

    var sharedInfoAlbum = MutableLiveData<ItemSharedAlbums>()

    //SearchSong
    var mostSearched: MutableLiveData<MutableList<ItemMusicList<ItemSong>>> = MutableLiveData()
    var listSongs: MutableLiveData<MutableList<ItemMusicList<ItemSong>>> = MutableLiveData()
    var listAlbums: MutableLiveData<MutableList<ItemMusicList<ItemSong>>> = MutableLiveData()
    var listVideos: MutableLiveData<MutableList<ItemMusicList<ItemSong>>> = MutableLiveData()

    fun setData(
        imgSong: String,
        nameSong: String,
        singerSong: String
    ) {
        sharedInfoAlbum.value = ItemSharedAlbums(
            imgSong,
            nameSong,
            singerSong)
    }

    @SuppressLint("CheckResult")
    fun searchSong(nameSearch: String, context: Context) {
        disPolistSongs?.dispose()
        disPolistSongs = songService.searchSong(nameSearch).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                listSongs.value = it

            }, {

                it.printStackTrace()
            },{
                disPolistSongs = null
            })

    }

    @SuppressLint("CheckResult")
    fun searchAlbum(nameSearch: String) {
        disPolistAlbums?.dispose()
        disPolistAlbums = songService.searchAlbums(nameSearch).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                listAlbums.value = it
            }, {
                it.printStackTrace()
            },{
                disPolistAlbums = null
            })

    }

    @SuppressLint("CheckResult")
    fun searchVideo(nameSearch: String) {
        disPolistVideos?.dispose()
        disPolistVideos = songService.searchVideos(nameSearch).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                listVideos.value = it
            }, {
                it.printStackTrace()
            },{
                disPolistVideos = null
            })

    }


    @SuppressLint("CheckResult")
    fun mostSearched() {
        disPoMost?.dispose()
        disPoMost = songService.mostSearched().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                mostSearched.value = it
            }, {
                it.printStackTrace()
            },{
                disPoMost = null
            })

    }

    fun onDestroy(){
        disPoMost?.dispose()
        disPolistAlbums?.dispose()
        disPolistSongs?.dispose()
        disPolistVideos?.dispose()
    }
}