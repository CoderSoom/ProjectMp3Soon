package com.example.recyclerviewpool.view.fragment.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpool.databinding.TabMusicVideoBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.adapter.ranking.CountryVideoAdapter
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.viewmodel.RankingModel

class TabVideo(val title: String, var managerRanking: ManagerRanking) : Fragment(),
    CountryVideoAdapter.ICountry {
    private lateinit var binding: TabMusicVideoBinding
    private lateinit var model: RankingModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TabMusicVideoBinding.inflate(inflater, container, false)
        model = RankingModel()
        binding.rcCountry.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CountryVideoAdapter(this@TabVideo)
        }
        model.getVideoRanking(title)
        reg()

        return binding.root
    }

    private fun reg() {
        model.rankingVideoCountry.observe(viewLifecycleOwner, Observer {
            binding.rcCountry.adapter!!.notifyDataSetChanged()
        })
    }


    override fun getVideoCountryCout(): Int {
        if (model.rankingVideoCountry.value == null) {
            return 0

        } else {
            return model.rankingVideoCountry.value!!.size
        }
    }

    override fun getVideoCountryData(position: Int): ItemSong {
        return model.rankingVideoCountry.value!![position]
    }

    override fun getVideoCountryOnClick(position: Int) {
        (activity as MainActivity).getDiscoverModel().getInfo(model.rankingVideoCountry.value!![position].linkSong)
        (activity as MainActivity).getDiscoverModel().getRelateVideo(model.rankingVideoCountry.value!![position].linkSong)
        managerRanking.openVideo()

    }

}