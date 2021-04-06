package com.example.recyclerviewpool.view.fragment.discover.albumsong

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.databinding.ViewpagerDetailAlbumBinding
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.viewmodel.DiscoverModel
import com.example.recyclerviewpool.viewmodel.LoadDataUtils
import com.vansuita.gaussianblur.GaussianBlur

class DetailsAlbumFragment : Fragment() {
    private lateinit var sharedViewModel: DiscoverModel
    private lateinit var binding: ViewpagerDetailAlbumBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        updateDataAlbums()
        binding = ViewpagerDetailAlbumBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun updateDataAlbums() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(DiscoverModel::class.java)
        sharedViewModel.sharedInfoAlbum.observe(viewLifecycleOwner, Observer {
            binding.nameAlbum.text = it.nameSong
            binding.singerAlbum.text = it.singerSong
            var endImg = it.imgSong.endsWith("245x140.png")
            if (it.singerSong == ""){
                binding.singerAlbum.visibility = View.GONE
            }
            if (!endImg){
                LoadDataUtils.loadImg(binding.imgAlbum, it.imgSong)
            }else{
                (activity as MainActivity).getModel().infoAlbum.observe(viewLifecycleOwner, Observer {
                    LoadDataUtils.loadImg(binding.imgAlbum, it.imgSong)
                })
            }
        })

    }
}