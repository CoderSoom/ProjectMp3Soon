package com.example.recyclerviewpool.view.fragment.discover.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.recyclerviewpool.databinding.ViewpagerLyricSongBinding
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.lyricsong.LyricManager

class ViewPagerLyricSong : Fragment(), View.OnClickListener {


    private lateinit var lyricManager: LyricManager

    private lateinit var model: MainActivity
    private lateinit var binding: ViewpagerLyricSongBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = (activity as MainActivity)
        binding = ViewpagerLyricSongBinding.inflate(inflater, container, false)
        reg()
        lyricManager = LyricManager(context!!)
        return binding.root
    }

    private fun reg() {
        model.getDiscoverModel().infoAlbum.observe(viewLifecycleOwner, Observer {
            if (it.lyricSong =="") {
                binding.lyricA.text= "Không có lời bài hát"
            } else {
                binding.lyricA.text = model.getDiscoverModel().infoAlbum.value!!.lyricSong

            }

        })
    }


    override fun onClick(v: View?) {
    }

}