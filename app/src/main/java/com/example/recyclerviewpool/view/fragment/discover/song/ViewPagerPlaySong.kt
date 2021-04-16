package com.example.recyclerviewpool.view.fragment.discover.song

import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.recyclerviewpool.databinding.ViewpagerPlaySongBinding
import com.example.recyclerviewpool.view.MainActivity
import java.text.SimpleDateFormat


open class ViewPagerPlaySong : Fragment(){
    private lateinit var playService: MainActivity
    private  lateinit var model : MainActivity
    private lateinit var binding : ViewpagerPlaySongBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        model = (activity as MainActivity)
        reg()
        binding = ViewpagerPlaySongBinding.inflate(inflater, container, false)


        return binding.root
    }
    fun reg(){
        model.getDiscoverModel().infoAlbum.observe(viewLifecycleOwner, Observer {
            binding.mainSong = it
        })

    }


}