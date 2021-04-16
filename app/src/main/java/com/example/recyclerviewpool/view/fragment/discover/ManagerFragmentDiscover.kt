package com.example.recyclerviewpool.view.fragment.discover

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.databinding.ManagerDiscoverFragmentBinding
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.view.fragment.discover.albumsong.AddAlbumFragment
import com.example.recyclerviewpool.view.fragment.discover.albumsong.AlbumRankingCountryFragment
import com.example.recyclerviewpool.view.fragment.discover.albumsong.SongAlbumSingerFragment
import com.example.recyclerviewpool.view.fragment.discover.video.FragmentVideo
import com.example.recyclerviewpool.view.fragment.ranking.AlbumCategoriesStatusFragment


class ManagerFragmentDiscover : Fragment() {
    private lateinit var navigationBar: MainActivity
    private lateinit var binding: ManagerDiscoverFragmentBinding


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        navigationBar = (activity as MainActivity)
        binding = ManagerDiscoverFragmentBinding.inflate(inflater, container, false)
        addDiscoverFragment()
        return binding.root
    }

    private fun addDiscoverFragment() {
        var fg = fragmentManager!!
        var tran = fg.beginTransaction()
        tran.replace(R.id.manager_discover_layout,
            DiscoverFragment(this),
            DiscoverFragment::class.java.name)
        tran.commit()

    }

    fun openSongAlbums() {
        var fg = childFragmentManager!!
        var tran = fg.beginTransaction()
        tran.replace(R.id.manager_discover_layout,
            AddAlbumFragment(this, null), AddAlbumFragment::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
    }


    fun openSongAlbumsSinger() {
        var fg = childFragmentManager!!
        var tran = fg.beginTransaction()
        tran.replace(R.id.manager_discover_layout,
            SongAlbumSingerFragment(this), SongAlbumSingerFragment::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
    }




    fun openVideo() {
        var fg = fragmentManager!!.beginTransaction()
        fg.replace(R.id.manager_discover_layout, FragmentVideo(), FragmentVideo::class.java.name)
        fg.addToBackStack(null)
        fg.commit()
    }

    fun openAlbumRankingCountry() {
        var fg = fragmentManager!!
        var tran = fg.beginTransaction()
        tran.replace(R.id.manager_discover_layout,
            AlbumRankingCountryFragment(this), AlbumRankingCountryFragment::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
    }


    fun openAlbumCategoriesStatus() {
        var fg = fragmentManager!!
        var tran = fg.beginTransaction()
        tran.replace(R.id.manager_discover_layout,
            AlbumCategoriesStatusFragment(this), AlbumCategoriesStatusFragment::class.java.name)
        tran.addToBackStack(null)
        tran.commit()
    }


}