package com.example.recyclerviewpool.view.fragment.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.databinding.ManagerTabMusicVideoBinding
import com.example.recyclerviewpool.view.MainActivity
import com.sothree.slidinguppanel.SlidingUpPanelLayout


class ManagerMusicVideoFragment : Fragment {
    private lateinit var model: MainActivity
    private lateinit var playService: MainActivity
    private lateinit var slidingUpPanelLayout: MainActivity

    private lateinit var binding: ManagerTabMusicVideoBinding
    var managerRanking: ManagerRanking
    private val country: String

    constructor(
        managerRanking: ManagerRanking,
        country: String
    ) {
        this.managerRanking = managerRanking
        this.country = country

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slidingUpPanelLayout = (activity as MainActivity)
        model = (activity as MainActivity)
        binding = ManagerTabMusicVideoBinding.inflate(inflater, container, false)
        playService = (activity as MainActivity)
        ///SlidingPanelDown
        (slidingUpPanelLayout.getSlidingPanelUp()).panelState =
            SlidingUpPanelLayout.PanelState.COLLAPSED

        openFragmentMusic()
        binding.tabMusic.setOnClickListener {
            openFragmentMusic()
        }
        if (country == "france"|| country == "play-back") {
            binding.tabVideo.visibility = View.GONE
        } else {
            binding.tabVideo.visibility = View.VISIBLE
            binding.tabVideo.setOnClickListener {
                openFragmentVideo()
            }
        }



        return binding.root

    }

    private fun openFragmentMusic() {
        var fg = childFragmentManager.beginTransaction()
        fg.replace(R.id.frame_layout, TabMusic(managerRanking, country))
        fg.commit()
    }

    private fun openFragmentVideo() {
        var fg = childFragmentManager.beginTransaction()
        fg.replace(R.id.frame_layout, TabVideo(country))
        fg.commit()
    }


}