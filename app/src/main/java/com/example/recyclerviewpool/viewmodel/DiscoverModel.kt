package com.example.recyclerviewpool.viewmodel

import android.annotation.SuppressLint
import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.engine.Resource
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.model.RetrofitUtils
import com.example.recyclerviewpool.model.SongService
import com.example.recyclerviewpool.model.itemdata.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlin.coroutines.coroutineContext

class DiscoverModel : ViewModel() {
        private var disPoTopResult: Disposable?=null
    private var disPoAlbumSong: Disposable?=null
    private var disPoAlbumVideo: Disposable?=null
    private var disPoRelateVideo: Disposable?=null
    private var disPoRelateSong: Disposable?=null
    private var disPoInfoAlbums: Disposable?=null
    private var disPoSongAlbums: Disposable?=null
    private var disPoSongChilSinger: Disposable?=null
    private var disPoCategoriesCountry: Disposable?=null
    private var disPoCategoriesStatus: Disposable?=null
    private var disPoNewSong: Disposable?=null
    private var disPoOutstandingSinger: Disposable?=null
    //Share ViewModel
    var sharedInfoAlbum = MutableLiveData<ItemSharedAlbums>()


    //TopResult
    var topResult: MutableLiveData<MutableList<ItemMusicList<ItemSong>>> = MutableLiveData()

    //New Album
    var albumsSong: MutableLiveData<MutableList<ItemMusicList<ItemSong>>> = MutableLiveData()
    var albumsVideo: MutableLiveData<MutableList<ItemMusicList<ItemSong>>> = MutableLiveData()

    ///Sug
    var sugAlbums: MutableLiveData<MutableList<ItemMusicList<ItemSong>>> = MutableLiveData()

    //Info And Relate Song, Video
    var infoAlbum: MutableLiveData<ItemMusicInfo> = MutableLiveData()
    var relateSong: MutableLiveData<MutableList<ItemMusicList<ItemSong>>> = MutableLiveData()
    var mvSong: MutableLiveData<MutableList<ItemSong>> = MutableLiveData()
    var relateVideo: MutableLiveData<MutableList<ItemSong>> = MutableLiveData()

    //Song in Album
    var songAlbums: MutableLiveData<MutableList<ItemSong>> = MutableLiveData()
    var songChilSinger: MutableLiveData<MutableList<ItemSong>> = MutableLiveData()

    //Categories
    var categoriesCountry: MutableLiveData<MutableList<ItemMusicList<ItemCategories>>> =
        MutableLiveData()
    var categoriesStatus: MutableLiveData<MutableList<ItemMusicList<ItemCategories>>> =
        MutableLiveData()

    var childCategoriesStatus: MutableLiveData<MutableList<ItemMusicList<ItemSong>>> = MutableLiveData()

    //New Song

    var newSong: MutableLiveData<MutableList<ItemMusicList<ItemSong>>> = MutableLiveData()

    //outstandingSinger
    var outstandingSinger: MutableLiveData<MutableList<ItemMusicList<ItemCategories>>> =
        MutableLiveData()


    private val songService =
        RetrofitUtils.createRetrofit("http://192.168.1.5:5000", SongService::class.java)


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
    fun albumsSong() {
        disPoAlbumSong?.dispose()
        songService.albumsSong().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                albumsSong.value = it
            }, {
                it.printStackTrace()
            }, {
                disPoAlbumSong=null
            })
    }

    @SuppressLint("CheckResult")
    fun albumsVideo() {
        disPoAlbumVideo?.dispose()
        songService.albumsVideo().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                albumsVideo.value = it
            }, {
                it.printStackTrace()
            }, {
                disPoAlbumVideo=null
            })
    }


    @SuppressLint("CheckResult")
    fun albumsChil(linkAlbums: String) {
        disPoSongAlbums?.dispose()
        songService.albumsChil(linkAlbums).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                songAlbums.value = it
            }, {

                it.printStackTrace()
            }, {
                disPoSongAlbums=null
            })
    }

    @SuppressLint("CheckResult")
    fun albumsChilSinger(linkSinger: String) {
        disPoSongChilSinger?.dispose()
        songService.albumsChilSinger(linkSinger).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                songChilSinger.value = it
            }, {
                it.printStackTrace()
            }, {
                disPoSongChilSinger=null
            })
    }


    @SuppressLint("CheckResult")
    fun getInfo(link: String) {
        disPoInfoAlbums?.dispose()
        songService.getInfo(link).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                infoAlbum.value = it
            }, {

                it.printStackTrace()
            }, {
                disPoInfoAlbums=null
            })
    }

    @SuppressLint("CheckResult")
    fun getRelateSong(linkSong: String) {
        disPoRelateSong?.dispose()
        songService.getRelateSong(linkSong).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                relateSong.value = it
            }, {
                it.printStackTrace()
            }, {
                disPoRelateSong=null
            })
    }

    @SuppressLint("CheckResult")
    fun getMVSong(linkSong: String) {
//        disPoRelateSong?.dispose()
        Log.d("TAGGGGGGG", "$linkSong"
        )
        songService.getMVSong(linkSong).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
               mvSong.value = it
            }, {
                it.printStackTrace()
            }, {
//                disPoRelateSong=null
            })
    }

    @SuppressLint("CheckResult")
    fun getRelateVideo(linkRelate: String) {
        disPoRelateVideo?.dispose()
        songService.getRelateVideo(linkRelate).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                relateVideo.value = it
            }, {

                it.printStackTrace()
            }, {
                disPoRelateVideo=null
            })
    }

    @SuppressLint("CheckResult")
    fun getTopResult() {
        disPoTopResult?.dispose()
        songService.getTopResult().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                topResult.value = it
            }, {
                it.printStackTrace()
            }, {
                disPoTopResult=null
            })
    }

    @SuppressLint("CheckResult")
    fun categoriesCountry() {
        disPoCategoriesCountry?.dispose()
        songService.categoriesContry().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                categoriesCountry.value = it
            }, {

                it.printStackTrace()
            }, {
                disPoCategoriesCountry=null
            })
    }

    @SuppressLint("CheckResult")
    fun categoriesStatus() {
        disPoCategoriesStatus?.dispose()
        songService.categoriesStatus().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                categoriesStatus.value = it
            }, {

                it.printStackTrace()
            }, {
                disPoCategoriesStatus=null
            })
    }

    @SuppressLint("CheckResult")
    fun newSong() {
        disPoNewSong?.dispose()
        songService.newSong().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                newSong.value = it
            }, {

                it.printStackTrace()
            },{
                disPoNewSong=null
            })
    }

    @SuppressLint("CheckResult")
    fun outstandingSinger() {
        disPoOutstandingSinger?.dispose()
        songService.outstandingSinger().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                outstandingSinger.value = it
            }, {
                it.printStackTrace()
            }, {
                disPoOutstandingSinger = null
            })
    }

    @SuppressLint("CheckResult")
    fun sugVideoMusic(linkSug: String) {
        songService.getSug(linkSug).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                 sugAlbums.value = it
            }, {
                it.printStackTrace()
            }, {

            })
    }



    @SuppressLint("CheckResult")
    fun childCategoriesStatus(linkCategories: String) {
        songService.chilCategoriesStatus(linkCategories).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                childCategoriesStatus.value = it
            }, {
                it.printStackTrace()
            }, {
            })
    }



}