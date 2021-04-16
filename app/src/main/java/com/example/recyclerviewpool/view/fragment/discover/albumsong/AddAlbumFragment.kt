package com.example.recyclerviewpool.view.fragment.discover.albumsong

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.databinding.FragmentAlbumsBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.adapter.discover.song.SongAlbumsAdapter
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.view.fragment.ranking.ManagerRanking
import com.example.recyclerviewpool.view.fragment.search.ManagerFragmentSearch
import com.example.recyclerviewpool.viewmodel.DiscoverModel
import com.example.recyclerviewpool.viewmodel.LoadDataUtils
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import kotlinx.android.synthetic.main.sliding_up_panel.view.*
import kotlin.math.log


class AddAlbumFragment : Fragment, SongAlbumsAdapter.IAlbum, View.OnClickListener {
    private lateinit var sharedViewModel: DiscoverModel

    private lateinit var playService: MainActivity
    private lateinit var slidingUpPanelLayout: MainActivity
    lateinit var managerDiscover: ManagerFragmentDiscover
    lateinit var managerRanking: ManagerRanking

    constructor(managerDiscover: ManagerFragmentDiscover?, managerRanking: ManagerRanking?) {
        if (managerDiscover != null) {
            this.managerDiscover = managerDiscover
        }
        if (managerRanking != null) {
            this.managerRanking = managerRanking
        }
    }


    private var listFragment = mutableListOf<Fragment>()

    private lateinit var model: MainActivity
    private lateinit var binding: FragmentAlbumsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        playService = (activity as MainActivity)
        slidingUpPanelLayout = (activity as MainActivity)
        model = (activity as MainActivity)
        binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener(this)
        reg()
        setUpViewPager()

        updataDataAlbum()
        return binding.root

    }

    private fun setUpViewPager() {
        listFragment.add(DetailsAlbumFragment())
        listFragment.add(ViewPagerInfoAlbum())
        val viewPager = binding.viewPagerSong
        val adapterViewPager: FragmentPagerAdapter =
            object : FragmentPagerAdapter(fragmentManager!!) {
                override fun getItem(position: Int): Fragment {
                    return listFragment[position]
                }

                override fun getCount(): Int {
                    return listFragment.size
                }
            }
        viewPager.adapter = adapterViewPager
        binding.rcAlbums.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SongAlbumsAdapter(this@AddAlbumFragment)
        }
    }

    private fun updataDataAlbum() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(DiscoverModel::class.java)
        sharedViewModel.sharedInfoAlbum.observe(viewLifecycleOwner, Observer {
            LoadDataUtils.loadImgBitMapBlur(context, binding.bgAlbums, it.imgSong)

        })
    }


    private fun reg() {
        model.getDiscoverModel().songAlbums.observe(viewLifecycleOwner, Observer {
            binding.rcAlbums.adapter!!.notifyDataSetChanged()
        })
    }

    override fun getCount(): Int {
        if (model.getDiscoverModel().songAlbums.value == null) {
            return 0
        } else {
            return model.getDiscoverModel().songAlbums.value!!.size
        }
    }

    override fun getData(position: Int): ItemSong {
        return model.getDiscoverModel().songAlbums.value!![position]
    }

    override fun getOnClickSong(position: Int) {


        (activity as MainActivity).getPlaySevice()!!.currentPositionSong = position

        (slidingUpPanelLayout.getSlidingPanelUp()).panelState = PanelState.EXPANDED
        (slidingUpPanelLayout.getSlidingPanelUp()).addPanelSlideListener(object :
            SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                panel!!.slide_play_song_mini.alpha = 1 - slideOffset
            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: PanelState?,
                newState: PanelState
            ) {
                if (newState == PanelState.EXPANDED) {
                    (slidingUpPanelLayout.getSlidingPanelUp()).slide_play_song_big.visibility =
                        View.VISIBLE
                } else {
                    (slidingUpPanelLayout.getSlidingPanelUp()).slide_play_song_big.visibility =
                        View.INVISIBLE
                }
            }
        })
        if (model.getDiscoverModel().songAlbums.value!=null) {
            model.getDiscoverModel()
                .getInfo(model.getDiscoverModel().songAlbums.value!![position].linkSong)
            model.getDiscoverModel()
                .getRelateSong(model.getDiscoverModel().songAlbums.value!![position].linkSong)
            model.getDiscoverModel()
                .getMVSong(model.getDiscoverModel().songAlbums.value!![position].linkSong)
        }

//        if (model.getRankingModel().rankingMusicCountry.value != null) {
//            model.getDiscoverModel()
//                .getInfo(model.getRankingModel().rankingMusicCountry.value!![position].linkSong)
//        Toast.makeText(context, "AAAA"+model.getRankingModel().rankingMusicCountry.value!![position].linkSong, Toast.LENGTH_SHORT).show()
//            model.getDiscoverModel()
//                .getRelateSong(model.getRankingModel().rankingMusicCountry.value!![position].linkSong)
//            model.getDiscoverModel()
//                .getMVSong(model.getRankingModel().rankingMusicCountry.value!![position].linkSong)
//        }

    /////SetLink Music Player
    model.getDiscoverModel().infoAlbum.observe(this, Observer
    {
        LoadDataUtils.loadImgBitMapBlur(context,
            (slidingUpPanelLayout.getSlidingPanelUp()).bg_song,
            it.imgSong)
        model.getDiscoverModel().songAlbums.value!![playService.getPlaySevice()!!.currentPositionSong].linkMusic =
            it.linkMusic
        model.getDiscoverModel().songAlbums.value!![playService.getPlaySevice()!!.currentPositionSong].nameSong =
            it.nameSong


        playService.getPlaySevice()!!.releaseMusic()
        playService.getPlaySevice()!!
            .setDataMusicOnline(model.getDiscoverModel().songAlbums.value!![playService.getPlaySevice()!!.currentPositionSong],
                position,
                model.getDiscoverModel().songAlbums.value!!)

    })



    model.getDiscoverModel().songAlbums.observe(viewLifecycleOwner, Observer
            {
                (slidingUpPanelLayout.getSlidingPanelUp()).slide_play_song_big.play_nameSong.text =
                    it[position].nameSong
                (slidingUpPanelLayout.getSlidingPanelUp()).slide_play_song_big.play_singerSong.text =
                    it[position].singerSong


        //setName Slide Panel Up
        (slidingUpPanelLayout.getSlidingPanelUp()).slide_play_song_mini.nameSong.text =
            it[position].nameSong
        (slidingUpPanelLayout.getSlidingPanelUp()).slide_play_song_mini.singerSong.text =
            it[position].singerSong
    })


}

override fun onClick(v: View?) {
    when (v?.id) {
        R.id.btn_back -> {
            if (managerDiscover != null) {
                managerDiscover.childFragmentManager.popBackStack()
            }
            if (managerRanking != null) {
                managerRanking.childFragmentManager.popBackStack()
            }
        }
    }
}
}
