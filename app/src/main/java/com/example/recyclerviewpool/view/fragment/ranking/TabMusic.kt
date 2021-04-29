package com.example.recyclerviewpool.view.fragment.ranking

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpool.databinding.TabMusicVideoBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.adapter.ranking.CountryMusicAdapter
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.viewmodel.DiscoverModel
import com.example.recyclerviewpool.viewmodel.LoadDataUtils
import com.example.recyclerviewpool.viewmodel.RankingModel
import com.example.recyclerviewpool.viewmodel.SetDataSlidingPanel
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.sliding_up_panel.view.*

class TabMusic(val title: String) : Fragment(), CountryMusicAdapter.ICountry {
    private lateinit var binding: TabMusicVideoBinding
    private lateinit var model: MainActivity
    private lateinit var rankingModel: RankingModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TabMusicVideoBinding.inflate(inflater, container, false)
        model = (activity as MainActivity)
        rankingModel = RankingModel()
        reg()
        rankingModel.getMusicRanking(title)
        model.getRankingModel().getMusicRanking(title)
        binding.rcCountry.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CountryMusicAdapter(this@TabMusic)
        }
        return binding.root

    }

    private fun reg() {
        rankingModel.rankingMusicCountry.observe(viewLifecycleOwner, Observer {
            binding.rcCountry.adapter!!.notifyDataSetChanged()
        })
    }

    override fun getCountryMusicCount(): Int {
        if ( rankingModel.rankingMusicCountry.value == null) {
            return 0

        } else {
            return  rankingModel.rankingMusicCountry.value!!.size
        }
    }

    override fun getCountryMusicData(position: Int): ItemSong {
        return  rankingModel.rankingMusicCountry.value!![position]
    }

    override fun getCountryMusicOnClick(position: Int) {
        (activity as MainActivity).songAlbums = rankingModel.rankingMusicCountry.value!!
        model.getPlaySevice()!!.currentPositionSong = position
        SetDataSlidingPanel.setDataSlidingPanel(model,rankingModel.rankingMusicCountry.value!!, position)
        model.getDiscoverModel().infoAlbum.observe(viewLifecycleOwner, Observer {
            SetDataSlidingPanel.setDataMusic(model, rankingModel.rankingMusicCountry.value!!, it, position)
        })
    }

}