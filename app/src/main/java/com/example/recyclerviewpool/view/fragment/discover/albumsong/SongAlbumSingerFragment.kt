package com.example.recyclerviewpool.view.fragment.discover.albumsong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpool.databinding.FragmentAlbumSingerBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.adapter.discover.song.SongAlbumSingerAdapter
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.viewmodel.DiscoverModel
import com.example.recyclerviewpool.viewmodel.LoadDataUtils
import com.example.recyclerviewpool.viewmodel.SetDataSlidingPanel
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import kotlinx.android.synthetic.main.sliding_up_panel.view.*


class SongAlbumSingerFragment : Fragment, SongAlbumSingerAdapter.IAlbum {
    private lateinit var sharedViewModel: DiscoverModel
    private  lateinit var playService: MainActivity
    private lateinit  var slidingUpPanelLayout: MainActivity
    private var managerDiscover: ManagerFragmentDiscover

    constructor( managerDiscover: ManagerFragmentDiscover) {
        this.managerDiscover = managerDiscover

    }


    private lateinit var model: MainActivity
    private lateinit var binding: FragmentAlbumSingerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slidingUpPanelLayout = (activity as MainActivity)
        model = (activity as MainActivity)
        binding = FragmentAlbumSingerBinding.inflate(inflater, container, false)
        playService = (activity as MainActivity)
        ///SlidingPanelDown
        (slidingUpPanelLayout.getSlidingPanelUp()).panelState = PanelState.COLLAPSED


        binding.rcAlbums.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SongAlbumSingerAdapter(this@SongAlbumSingerFragment)
        }
        reg()
        updataDataAlbum()
        return binding.root

    }

    private fun reg() {
        model.getDiscoverModel().songChilSinger.observe(viewLifecycleOwner, Observer {
            binding.rcAlbums.adapter!!.notifyDataSetChanged()

        })
    }
    private fun updataDataAlbum() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(DiscoverModel::class.java)
        sharedViewModel.sharedInfoAlbum.observe(viewLifecycleOwner, Observer {
            LoadDataUtils.loadText(binding.nameSinger, "Album của ${it.nameSong}")
            LoadDataUtils.loadImgBitMapBlur(context, binding.bgAlbums, it.imgSong )

        })
    }

    override fun getAlbumSingerCount(): Int {
        if (model.getDiscoverModel().songChilSinger.value == null) {
            return 0
        } else {
            return model.getDiscoverModel().songChilSinger.value!!.size
        }
    }

    override fun getAlbumSingerData(position: Int): ItemSong {
        return model.getDiscoverModel().songChilSinger.value!![position]
    }

    override fun getOnClickAlbumSinger(position: Int) {

        (activity as MainActivity).songAlbums = (activity as MainActivity).getDiscoverModel().songChilSinger.value!!
        (activity as MainActivity).getPlaySevice()!!.currentPositionSong = position
        SetDataSlidingPanel.setDataSlidingPanel(
            activity as MainActivity,
            (activity as MainActivity).getDiscoverModel().songChilSinger.value!!,
            position
        )
        /////SetLink Music Player
        model.getDiscoverModel().infoAlbum.observe(this, Observer
        {
            SetDataSlidingPanel.setDataMusic(
                activity as MainActivity,
                (activity as MainActivity).getDiscoverModel().songChilSinger.value!!,
                it,
                position
            )
        })
    }



}




