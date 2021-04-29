package com.example.recyclerviewpool.view.fragment.discover.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpool.databinding.ViewpagerInfoSongBinding
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.adapter.discover.song.MVSongAlbumAdapter
import com.example.recyclerviewpool.adapter.discover.song.TopicRelateSongAlbumAdapter
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import kotlinx.android.synthetic.main.viewpager_info_song.*

class ViewPagerInfoSong : Fragment, TopicRelateSongAlbumAdapter.ICategories,
    MVSongAlbumAdapter.interItemSong {
    private lateinit var model: MainActivity
    private var managerDiscover: ManagerFragmentDiscover
    private lateinit var binding: ViewpagerInfoSongBinding

    constructor(managerDiscover: ManagerFragmentDiscover) {
        this.managerDiscover = managerDiscover
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = (activity as MainActivity)
        binding = ViewpagerInfoSongBinding.inflate(inflater, container, false)

        reg()
        regRelate()

        binding.rcRelateSong.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = TopicRelateSongAlbumAdapter(model, this@ViewPagerInfoSong, managerDiscover, viewLifecycleOwner)
        }

        binding.rcMVSong.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = MVSongAlbumAdapter( this@ViewPagerInfoSong)
        }

        return binding.root


    }

    private fun regRelate() {
        model.getDiscoverModel().relateSong.observe(viewLifecycleOwner, Observer {
            model.rcRelateSong.adapter!!.notifyDataSetChanged()
        })
        model.getDiscoverModel().mvSong.observe(viewLifecycleOwner, Observer {
            model.rcMVSong.adapter!!.notifyDataSetChanged()
            if (model.getDiscoverModel().mvSong.value!![0].nameSong!="" ){
                binding.rcMVSong.visibility = View.VISIBLE
            }else{
                binding.rcMVSong.visibility = View.GONE
            }
        })
    }

    private fun reg() {
        model.getDiscoverModel().infoAlbum.observe(viewLifecycleOwner, Observer {
            binding.infoSong = it
            when {
                it.albumsSong == "" || it.albumsSong == null -> {
                    binding.albumsSong.visibility = View.GONE
                }
                it.singerSong == "" -> {
                    binding.singerSong.visibility = View.GONE
                }
                it.authorSong == "" -> {
                    binding.authorSong.visibility = View.GONE
                }
                it.yearSong == "" -> {
                    binding.yearSong.visibility = View.GONE
                }
                it.nameSong == "" -> {
                    binding.nameSong.visibility = View.GONE
                }
            }
            binding.nameSong.text = it.nameSong
            binding.singerSong.text = it.singerSong
            binding.yearSong.text = it.yearSong
            binding.authorSong.text = it.authorSong
            binding.albumsSong.text = it.albumsSong
        })
    }
    override fun getOnClickMVItem(position: Int) {
        (activity as MainActivity).getDiscoverModel().getInfo(model.getDiscoverModel().mvSong.value!![position].linkSong)
        (activity as MainActivity).getDiscoverModel().getRelateVideo(model.getDiscoverModel().mvSong.value!![position].linkSong)
        managerDiscover.openVideo()
    }



    override fun getSizeCategories(): Int {
        if (model.getDiscoverModel().relateSong.value == null) {
            return 0
        } else {
            return model.getDiscoverModel().relateSong.value!!.size
        }
    }

    override fun getItemCategories(position: Int): ItemMusicList<ItemSong> {
        return model.getDiscoverModel().relateSong.value!![position]
    }

    override fun getMVCount(): Int {
        if (model.getDiscoverModel().mvSong.value==null){
            return 0
        }else {
            return model.getDiscoverModel().mvSong.value!!.size
        }
    }

    override fun getMVData(position: Int): ItemSong {
       return model.getDiscoverModel().mvSong.value!![position]
    }




}