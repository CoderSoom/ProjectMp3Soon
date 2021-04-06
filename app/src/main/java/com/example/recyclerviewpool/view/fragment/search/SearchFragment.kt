package com.example.recyclerviewpool.view.fragment.search

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpool.databinding.FragmentSearchBinding
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.adapter.search.TopicMostSearchAdapter
import com.example.recyclerviewpool.adapter.search.TopicSearchAlbumAdapter
import com.example.recyclerviewpool.adapter.search.TopicSearchSongAdapter
import com.example.recyclerviewpool.adapter.search.TopicSearchVideoAdapter
import com.example.recyclerviewpool.viewmodel.SearchModel
import com.example.recyclerviewpool.viewmodel.LoadDataUtils


class SearchFragment : Fragment, TopicSearchSongAdapter.ICategories,
    TopicSearchVideoAdapter.ICategories, TopicSearchAlbumAdapter.ICategories,
    TopicMostSearchAdapter.ICategories {
    private lateinit var model: SearchModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var sharedViewModel: SearchModel
    private var managerSearch: ManagerFragmentSearch

    constructor(managerSearch: ManagerFragmentSearch) {
        this.managerSearch = managerSearch
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        sharedViewModel = ViewModelProvider(requireActivity()).get(SearchModel::class.java)
        model = SearchModel()
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        model.searchSong("")

        //setUpMostSearched
        binding.rcMostSearched.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter =
                TopicMostSearchAdapter(this@SearchFragment, managerSearch)
        }
        //setUp searchSong
        binding.rcSearchSong.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                TopicSearchSongAdapter(sharedViewModel, (activity as MainActivity), this@SearchFragment, managerSearch)
        }

        //setUp searchVideo
        binding.rcSearchVideo.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                TopicSearchVideoAdapter(this@SearchFragment, managerSearch)
        }

        //setUp searchAlbums
        binding.rcSearchAlbum.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                TopicSearchAlbumAdapter(this@SearchFragment, managerSearch)
        }



        model.mostSearched()
        search()
        reg()
        val originalDrawable = binding.edtSearch.background
        binding.edtSearch.background = originalDrawable
        return binding.root

    }


    private fun reg() {
        model.mostSearched.observe(viewLifecycleOwner, Observer {
            binding.rcMostSearched.adapter!!.notifyDataSetChanged()

        })
        model.listSongs.observe(viewLifecycleOwner, Observer {
            binding.rcSearchSong.adapter!!.notifyDataSetChanged()
            ///setUpTopResultSong
            if (it == null || it.size == 0) {
                binding.rcSearchSong.visibility = View.GONE
                return@Observer
            } else {

                binding.rcSearchSong.visibility = View.VISIBLE

                LoadDataUtils.loadImgBitMapBlur(context, binding.bgSearchSong, it[0].values[0].imgSong)

                binding.topicSong.setTextColor(Color.WHITE)
                binding.topSong.nameSongSearch.text =
                    it[0].values[0].nameSong
                binding.topSong.singerSongSearch.text =
                    it[0].values[0].singerSong
                LoadDataUtils.loadImg(binding.topSong.imgSongSearch, it[0].values[0].imgSong)
            }

        })
        model.listAlbums.observe(viewLifecycleOwner, Observer {
            binding.rcSearchAlbum.adapter!!.notifyDataSetChanged()
            binding.rcSugSong.adapter!!.notifyDataSetChanged()
            binding.rcSugVideo.adapter!!.notifyDataSetChanged()
            if (it == null || it.size == 0) {
                binding.rcSearchAlbum.visibility = View.GONE
            } else {
                binding.rcSearchAlbum.visibility = View.VISIBLE

            }
        })
        model.listVideos.observe(viewLifecycleOwner, Observer {
            binding.rcSearchVideo.adapter!!.notifyDataSetChanged()

            ///setUpTopResultVideo
            if (it == null || it.size == 0) {
                binding.rcSearchVideo.visibility = View.GONE
                return@Observer
            } else {
                binding.rcSearchVideo.visibility = View.VISIBLE

                binding.topicVideo.setTextColor(Color.WHITE)
                binding.topVideo.nameVideoSearch.text =
                    it[0].values[0].nameSong
                binding.topVideo.singerVideoSearch.text =
                    it[0].values[0].singerSong
                LoadDataUtils.loadImg(binding.topVideo.imgVideoSearch, it[0].values[0].imgSong)
            }


        })
    }


    private fun search() {
        binding.arrowBack.setOnClickListener {
            binding.edtSearch.apply {
                requestFocus()
                isFocusableInTouchMode = true
            }
            val inputMethodManager = activity!!.getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(
                activity!!.currentFocus!!.windowToken, 0)

            binding.scalingLayout.collapse()
            binding.searchLayout.visibility = View.INVISIBLE
            binding.textViewSearch.visibility = View.VISIBLE
            binding.edtSearch.visibility = View.INVISIBLE
        }
        binding.scalingLayout.setOnClickListener {
            binding.scalingLayout.expand()
            binding.edtSearch.visibility = View.VISIBLE
            binding.textViewSearch.visibility = View.GONE
            binding.searchLayout.visibility = View.VISIBLE
        }
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val content = binding.edtSearch.text.toString()
                model.searchSong(content)
                model.searchAlbum(content)
                model.searchVideo(content)
                if (content.isNullOrEmpty()) {
                    binding.topicSong.visibility = View.GONE
                    binding.topSong.ll.visibility = View.GONE
                    binding.topicVideo.visibility = View.GONE
                    binding.topVideo.ll.visibility = View.GONE
                    binding.rcMostSearched.visibility = View.VISIBLE
                }else{
                    binding.topicSong.visibility = View.VISIBLE
                    binding.topSong.ll.visibility = View.VISIBLE
                    binding.topicVideo.visibility = View.VISIBLE
                    binding.topVideo.ll.visibility = View.VISIBLE

                }
                binding.rcMostSearched.visibility = View.GONE


            }

            override fun afterTextChanged(s: Editable) {

            }
        })


    }


    ////Search Song Adapter
    override fun getSearchSongCount(): Int {
        return if (model.listSongs.value == null) {
            0
        } else
            1

    }

    override fun getSearchSongData(position: Int): ItemMusicList<ItemSong>? {
        if (model.listSongs.value!!.size == 0) {
            return null
        } else {
            return model.listSongs.value!![position]
        }
    }

    ///Search Video Adapter
    override fun getSearchVideoCount(): Int {
        return if (model.listVideos.value == null) {
            0
        } else
            1

    }

    override fun getSearchVideoData(position: Int): ItemMusicList<ItemSong>? {
        if (model.listVideos.value!!.size == 0) {
            return null
        } else {
            return model.listVideos.value!![position]
        }
    }


    ///Search Album Adapter
    override fun getSearchAlbumCount(): Int {
        return if (model.listAlbums.value == null) {
            0
        } else
            1
    }

    override fun getSearchAlbumData(position: Int): ItemMusicList<ItemSong>? {
        if (model.listAlbums.value!!.size == 0) {
            return null
        } else
            return model.listAlbums.value!![position]
    }

    override fun getMostSearchCount(): Int {
        return if (model.mostSearched.value == null) {
            0
        } else
            1
    }

    override fun getMostSearchData(position: Int): ItemMusicList<ItemSong> {
        return model.mostSearched.value!![position]
    }


}