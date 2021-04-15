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
import com.example.recyclerviewpool.adapter.ranking.CountryMusicAdapter
import com.example.recyclerviewpool.viewmodel.RankingModel

class TabMusic(val title:String) : Fragment(), CountryMusicAdapter.ICountry {
    private lateinit var binding: TabMusicVideoBinding

    private lateinit var model:RankingModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TabMusicVideoBinding.inflate(inflater, container, false)
        model = RankingModel()
        binding.rcCountry.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CountryMusicAdapter(this@TabMusic)
        }
        model.getMusicRanking(title)
        reg()
        //setToggle


        return binding.root

    }

    private fun reg() {
        model.rankingMusicCountry.observe(viewLifecycleOwner, Observer {
            binding.rcCountry.adapter!!.notifyDataSetChanged()
        })
    }

    override fun getCountryMusicCount(): Int {
        if (model.rankingMusicCountry.value == null) {
            return 0

        } else {
            return model.rankingMusicCountry.value!!.size
        }
    }

    override fun getCountryMusicData(position: Int): ItemSong {
        return model.rankingMusicCountry.value!![position]
    }

    override fun getCountryMusicOnClick(position: Int) {
    }

}