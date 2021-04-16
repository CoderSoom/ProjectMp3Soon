package com.example.recyclerviewpool.view.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.databinding.ManagerSearchFragmentBinding
import com.example.recyclerviewpool.view.fragment.discover.albumsong.AddAlbumFragment

class ManagerFragmentSearch : Fragment(){
    private lateinit var binding : ManagerSearchFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ManagerSearchFragmentBinding.inflate(inflater, container, false)
        addFragment()
        return binding.root


    }
    private fun addFragment(){
        var fg = childFragmentManager!!.beginTransaction()
        fg.add(R.id.content, DetailSearchFragment(this) )
        fg.commit()
    }
//    fun openSongAlbums() {
//        var fg = childFragmentManager!!
//        var tran = fg.beginTransaction()
//        tran.replace(R.id.manager_discover_layout,
//            AddAlbumFragment(this), AddAlbumFragment::class.java.name)
//        tran.addToBackStack(null)
//        tran.commit()
//    }




}