package com.example.recyclerviewpool.viewmodel

import android.os.Environment
import android.view.View
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.model.download.Downloader
import com.example.recyclerviewpool.model.itemdata.ItemMusicInfo
import com.example.recyclerviewpool.model.itemdata.ItemMusicOffline
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.view.MainActivity
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.sliding_up_panel.view.*
import java.io.File


object SetDataSlidingPanel {
    fun setDataSlidingPanel(
        activity: MainActivity,
        dataSong: MutableList<ItemSong>,
        position: Int
    ) {
        (activity.getSlidingPanelUp()).panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        (activity.getSlidingPanelUp()).addPanelSlideListener(object :
            SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                panel!!.slide_play_song_mini.alpha = 1 - slideOffset
            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState
            ) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    activity.getNavigation().visibility = View.GONE
                    activity.slidingUpPanelLayoutView.slidePlaySongMini.visibility = View.INVISIBLE

                    (activity.getSlidingPanelUp()).slide_play_song_big.visibility =
                        View.VISIBLE
                } else {
                    activity.slidingUpPanelLayoutView.slidePlaySongMini.visibility = View.VISIBLE
                    activity.getNavigation().visibility = View.VISIBLE
                    activity.getBottomSheet().bottomSheet.visibility = View.GONE
                    (activity.getSlidingPanelUp()).slide_play_song_big.visibility =
                        View.INVISIBLE
                }
            }
        })
        if (dataSong != null) {
            activity.getDiscoverModel()
                .getInfo(dataSong[position].linkSong)
            activity.getDiscoverModel()
                .getRelateSong(dataSong[position].linkSong)
            activity.getDiscoverModel()
                .getMVSong(dataSong[position].linkSong)
        }

    }

    fun setDataMusic(
        activity: MainActivity, dataSong: MutableList<ItemSong>, it: ItemMusicInfo,
        position: Int
    ) {
        LoadDataUtils.loadImgBitMapBlur(activity,
            (activity.getSlidingPanelUp()).bg_song,
            it.imgSong)
        dataSong[activity.getPlaySevice()!!
            .currentPositionSong].linkMusic =
            it.linkMusic
        dataSong[activity.getPlaySevice()!!
            .currentPositionSong].nameSong =
            it.nameSong


        activity.getPlaySevice()!!.releaseMusic()
        activity.getPlaySevice()!!
            .setDataMusicOnline(dataSong
                    [activity.getPlaySevice()!!.currentPositionSong],
                position,
                dataSong)
        downloadFileMp3(activity!!, dataSong[activity.getPlaySevice()!!.currentPositionSong].linkMusic!!, dataSong[activity.getPlaySevice()!!.currentPositionSong].nameSong!! )

    }

    fun setDataSlidingOffline(
        activity: MainActivity,
        dataSong: MutableList<ItemMusicOffline>,
        position: Int
    ) {
        activity.getPlaySevice()!!.releaseMusic()
        activity.getPlaySevice()!!.setDataMusicOffline(dataSong[position].pathSong!!)
        activity.getPlaySevice()!!.playMusic()

        activity.getSlidingPanelUp().slide_play_song_mini.nameSong.text =
            dataSong[position].nameSong
        activity.getSlidingPanelUp().slide_play_song_mini.singerSong.text =
            dataSong[position].nameSinger
        activity.getSlidingPanelUp().slide_play_song_big.play_name_song.text =
            dataSong[position].nameSong
        activity.getSlidingPanelUp().slide_play_song_big.play_singerSong.text =
            dataSong[position].nameSinger

        activity.getSlidingPanelUp().slide.tv_time_end.text =
            dataSong[position].durationSong


        if (dataSong[position].imageUri == null) {
            activity.imgPlaySong.setImageResource(R.drawable.logoapp)
        } else {
            LoadDataUtils.loadImageUri(activity.imgPlaySong, dataSong[position].imageUri)
        }

        (activity.getSlidingPanelUp()).panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        (activity.getSlidingPanelUp()).addPanelSlideListener(object :
            SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                panel!!.slide_play_song_mini.alpha = 1 - slideOffset
            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState
            ) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    activity.getNavigation().visibility = View.GONE
                    activity.slidingUpPanelLayoutView.slidePlaySongMini.visibility = View.INVISIBLE
                    (activity.getSlidingPanelUp()).slide_play_song_big.visibility =
                        View.VISIBLE
                } else {
                    activity.slidingUpPanelLayoutView.slidePlaySongMini.visibility = View.VISIBLE
                    activity.getNavigation().visibility = View.VISIBLE
                    activity.getBottomSheet().bottomSheet.visibility = View.GONE
                    (activity.getSlidingPanelUp()).slide_play_song_big.visibility =
                        View.INVISIBLE
                }
            }
        })

    }
    fun downloadFileMp3(activity: MainActivity, url: String, nameFile: String) {
        Thread(Runnable {
            val download = Downloader(activity)
            val out = File(Environment.getExternalStorageDirectory().toString() + nameFile)
            download.execute(url)
        }).start()
    }
}