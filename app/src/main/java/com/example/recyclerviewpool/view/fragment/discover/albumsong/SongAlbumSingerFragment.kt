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
        model.getModel().songChilSinger.observe(viewLifecycleOwner, Observer {
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
        if (model.getModel().songChilSinger.value == null) {
            return 0
        } else {
            return model.getModel().songChilSinger.value!!.size
        }
    }

    override fun getAlbumSingerData(position: Int): ItemSong {
        return model.getModel().songChilSinger.value!![position]
    }

    override fun getOnClickAlbumSinger(position: Int) {

        model.getModel()
            .getInfo(model.getModel().songChilSinger.value!![position].linkSong)

        model.getModel()
            .getRelateSong(model.getModel().songChilSinger.value!![position].linkSong)

        model.getModel()
            .getMVSong(model.getModel().songChilSinger.value!![position].linkSong)

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
                        View.GONE
                }
            }
        })
    }



}




