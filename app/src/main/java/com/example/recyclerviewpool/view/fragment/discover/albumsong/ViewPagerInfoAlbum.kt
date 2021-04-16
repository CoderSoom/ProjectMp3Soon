package com.example.recyclerviewpool.view.fragment.discover.albumsong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerviewpool.databinding.ViewpagerInfoAlbumBinding
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.viewmodel.DiscoverModel

class ViewPagerInfoAlbum : Fragment(){
    private lateinit var model : MainActivity
    private lateinit var sharedViewModel: DiscoverModel
    private lateinit var binding : ViewpagerInfoAlbumBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewpagerInfoAlbumBinding.inflate(inflater, container, false)
        model = (activity as MainActivity)
        updateText()
        getInfo()
        return binding.root
    }

    private fun updateText() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(DiscoverModel::class.java)
        sharedViewModel.sharedInfoAlbum.observe(viewLifecycleOwner, Observer {
            binding.nameAlbum.text = it.nameSong
            binding.singerAlbum.text = "Nghệ sĩ: " +it.singerSong
        })
    }
    private fun getInfo(){
        model.getDiscoverModel().infoAlbum.observe(viewLifecycleOwner, Observer {
            binding.infoAlbum = it
        })

    }
}