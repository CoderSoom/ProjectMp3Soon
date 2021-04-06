package com.example.recyclerviewpool.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerviewpool.model.RetrofitUtils
import com.example.recyclerviewpool.model.SongService
import com.example.recyclerviewpool.model.itemdata.ItemSong
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RankingModel : ViewModel() {
    private var disPoRankMusic: Disposable? = null
    private var disPoRankVideo: Disposable? = null
    private val songService =
        RetrofitUtils.createRetrofit("http://192.168.1.11:5000", SongService::class.java)

    ///Ranking
    var rankingMusicCountry: MutableLiveData<MutableList<ItemSong>> = MutableLiveData()
    var rankingVideoCountry: MutableLiveData<MutableList<ItemSong>> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getMusicRanking(country: String) {
        disPoRankMusic?.dispose()
            songService.getMusicRanking(country).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    rankingMusicCountry.value = it
                }, {
                    it.printStackTrace()
                }, {
                    disPoRankMusic = null
                })

    }


    @SuppressLint("CheckResult")
    fun getVideoRanking(country: String) {
        disPoRankVideo?.dispose()
        songService.getVideoRanking(country).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                rankingVideoCountry.value = it
            }, {
                it.printStackTrace()
            }, {
                disPoRankVideo = null
            })

    }

}