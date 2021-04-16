package com.example.recyclerviewpool.view.fragment.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.databinding.ManagerRankingFragmentBinding
import com.example.recyclerviewpool.view.fragment.discover.albumsong.AddAlbumFragment
import com.example.recyclerviewpool.view.fragment.discover.video.FragmentVideo

class ManagerRanking : Fragment(){
    private lateinit var binding : ManagerRankingFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ManagerRankingFragmentBinding.inflate(inflater, container, false)
        addManagerFragmentRanking()
        return binding.root


    }
    fun openVideo() {
        var fg = fragmentManager!!.beginTransaction()
        fg.replace(R.id.frame_ranking_layout, FragmentVideo(), FragmentVideo::class.java.name)
        fg.addToBackStack(null)
        fg.commit()
    }

    fun addManagerFragmentRanking(){
        var fg = childFragmentManager!!.beginTransaction()
        fg.replace(R.id.frame_ranking_layout, RankingFragment(this))
        fg.commit()
    }



}