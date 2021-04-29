package com.example.recyclerviewpool.view.fragment.ranking

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.databinding.FragmentRankingBinding
import com.example.recyclerviewpool.view.MainActivity
import com.sothree.slidinguppanel.SlidingUpPanelLayout


class RankingFragment : Fragment {

    private lateinit var playService: MainActivity
    private lateinit var slidingUpPanelLayout: MainActivity
    var managerRanking: ManagerRanking

    constructor(managerRanking: ManagerRanking) {
        this.managerRanking = managerRanking
    }

    private var listFragment = mutableListOf<Fragment>()

    private lateinit var model: MainActivity
    private lateinit var binding: FragmentRankingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slidingUpPanelLayout = (activity as MainActivity)
        model = (activity as MainActivity)
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        playService = (activity as MainActivity)

        listFragment.add(ManagerMusicVideoFragment(managerRanking, "play-back"))
        listFragment.add(ManagerMusicVideoFragment(managerRanking, "viet-nam"))
        listFragment.add(ManagerMusicVideoFragment(managerRanking, "us-uk"))
        listFragment.add(ManagerMusicVideoFragment(managerRanking, "chinese"))
        listFragment.add(ManagerMusicVideoFragment(managerRanking, "korea"))
        listFragment.add(ManagerMusicVideoFragment(managerRanking, "japan"))
        listFragment.add(ManagerMusicVideoFragment(managerRanking, "france"))
        listFragment.add(ManagerMusicVideoFragment(managerRanking, "other"))


        val viewPager = binding.viewPagerMusicVideo
        val adapterViewPager: FragmentPagerAdapter =
            object : FragmentPagerAdapter(childFragmentManager!!,
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                override fun getItem(position: Int): Fragment {
                    return listFragment[position]
                }

                override fun getCount(): Int {
                    return listFragment.size
                }

                override fun getPageTitle(position: Int): CharSequence? {
                    when (position) {
                        0 -> return getString(R.string.play_back)
                        1 -> return getString(R.string.viet_nam)
                        2 -> return getString(R.string.us_uk)
                        3 -> return getString(R.string.chinese)
                        4 -> return getString(R.string.korea)
                        5 -> return getString(R.string.japan)
                        6 -> return getString(R.string.france)
                        7 -> return getString(R.string.other)
                    }
                    return ""
                }


            }
        viewPager.adapter = adapterViewPager
        var tabLayout = binding.viewpagertab

        tabLayout.setViewPager(viewPager)
        binding.viewPagerMusicVideo.offscreenPageLimit = listFragment.size - 1
        return binding.root
    }


}