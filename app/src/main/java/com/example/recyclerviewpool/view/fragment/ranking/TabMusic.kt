package com.example.recyclerviewpool.view.fragment.ranking

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpool.databinding.TabMusicVideoBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.adapter.ranking.CountryMusicAdapter
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.viewmodel.DiscoverModel
import com.example.recyclerviewpool.viewmodel.LoadDataUtils
import com.example.recyclerviewpool.viewmodel.RankingModel
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.sliding_up_panel.view.*

class TabMusic(var managerRanking: ManagerRanking, val title: String) : Fragment(),
    CountryMusicAdapter.ICountry {
    private lateinit var binding: TabMusicVideoBinding

    private lateinit var model: MainActivity
    private lateinit var modelRanking: RankingModel
    private lateinit var modelDiscoverModel: DiscoverModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TabMusicVideoBinding.inflate(inflater, container, false)
        modelRanking = RankingModel()
        model = (activity as MainActivity)
        modelDiscoverModel = DiscoverModel()
        binding.rcCountry.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CountryMusicAdapter(this@TabMusic)
        }
        modelRanking.getMusicRanking(title)
        reg()
        //setToggle


        return binding.root

    }

    private fun reg() {
        modelRanking.rankingMusicCountry.observe(viewLifecycleOwner, Observer {
            binding.rcCountry.adapter!!.notifyDataSetChanged()
        })
    }

    override fun getCountryMusicCount(): Int {
        if (modelRanking.rankingMusicCountry.value == null) {
            return 0

        } else {
            return modelRanking.rankingMusicCountry.value!!.size
        }
    }

    override fun getCountryMusicData(position: Int): ItemSong {
        return modelRanking.rankingMusicCountry.value!![position]
    }

    override fun getCountryMusicOnClick(position: Int) {

        (activity as MainActivity).getDiscoverModel()
            .getInfo(modelRanking.rankingMusicCountry.value!![position].linkSong)

        (activity as MainActivity).getDiscoverModel()
            .getRelateSong(modelRanking.rankingMusicCountry.value!![position].linkSong)

        (activity as MainActivity).getDiscoverModel()
            .getMVSong(modelRanking.rankingMusicCountry.value!![position].linkSong)

        ((activity as MainActivity).getSlidingPanelUp()).panelState =
            SlidingUpPanelLayout.PanelState.EXPANDED
        ((activity as MainActivity).getSlidingPanelUp()).addPanelSlideListener(object :
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
                    ((activity as MainActivity).getSlidingPanelUp()).slide_play_song_big.visibility =
                        View.VISIBLE
                } else {
                    ((activity as MainActivity).getSlidingPanelUp()).slide_play_song_big.visibility =
                        View.INVISIBLE
                }
            }
        })

       modelRanking.rankingMusicCountry.observe(viewLifecycleOwner, Observer
        {
            (activity as MainActivity).getSlidingPanelUp().slide_play_song_big.play_nameSong.text =
                it[position].nameSong

            ((activity as MainActivity).getSlidingPanelUp()).slide_play_song_big.play_singerSong.text =
                it[position].singerSong


            //setName Slide Panel Up
            ((activity as MainActivity).getSlidingPanelUp()).slide_play_song_mini.nameSong.text =
                it[position].nameSong
            ((activity as MainActivity).getSlidingPanelUp()).slide_play_song_mini.singerSong.text =
                it[position].singerSong
        })

        model.getDiscoverModel().infoAlbum.observe(this, Observer
        {
            LoadDataUtils.loadImgBitMapBlur(context,
                (model.getSlidingPanelUp()).bg_song,
                it.imgSong)

            modelRanking.rankingMusicCountry.value!![(activity as MainActivity).getPlaySevice()!!.currentPositionSong].linkMusic =
                it.linkMusic
            modelRanking.rankingMusicCountry.value!![(activity as MainActivity).getPlaySevice()!!.currentPositionSong].nameSong =
                it.nameSong




            (activity as MainActivity).getPlaySevice()!!.releaseMusic()
            (activity as MainActivity).getPlaySevice()!!
                .setDataMusicOnline(modelRanking.rankingMusicCountry.value!![ (activity as MainActivity).getPlaySevice()!!.currentPositionSong],
                    position,
                    modelRanking.rankingMusicCountry.value!!)

        })


    }

}