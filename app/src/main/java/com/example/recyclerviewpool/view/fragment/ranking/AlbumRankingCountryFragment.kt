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
import com.example.recyclerviewpool.adapter.discover.song.SongAlbumsAdapter
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.viewmodel.DiscoverModel
import com.example.recyclerviewpool.viewmodel.LoadDataUtils
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState


class AlbumRankingCountryFragment : Fragment, SongAlbumsAdapter.IAlbum {
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
            adapter = SongAlbumsAdapter(this@AlbumRankingCountryFragment)
        }
        reg()
        updataDataAlbum()
        return binding.root

    }

    private fun reg() {
        model.getDiscoverModel().songAlbums.observe(viewLifecycleOwner, Observer {
            binding.rcAlbums.adapter!!.notifyDataSetChanged()

        })
    }
    private fun updataDataAlbum() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(DiscoverModel::class.java)
        sharedViewModel.sharedInfoAlbum.observe(viewLifecycleOwner, Observer {
            LoadDataUtils.loadText(binding.nameSinger, it.nameSong)
            LoadDataUtils.loadImgBitMapBlur(context, binding.bgAlbums, it.imgSong )

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

        model.getDiscoverModel()
            .getInfo(model.getDiscoverModel().songAlbums.value!![position].linkSong)

        model.getDiscoverModel()
            .getRelateVideo(model.getDiscoverModel().songAlbums.value!![position].linkSong)

        model.getDiscoverModel()
            .getMVSong(model.getDiscoverModel().songAlbums.value!![position].linkSong)

        managerDiscover.openVideo()
    }


}




