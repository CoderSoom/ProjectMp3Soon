package com.example.recyclerviewpool.view.fragment.discover.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpool.databinding.FragmentVideoBinding
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.adapter.discover.video.RelateVideoAdapter
import com.example.recyclerviewpool.adapter.discover.video.TopicSugVideoAdapter
import com.example.recyclerviewpool.viewmodel.LoadDataUtils
import fm.jiecao.jcvideoplayer_lib.*


class FragmentVideo : Fragment(), RelateVideoAdapter.IRelateVideo, TopicSugVideoAdapter.ICategories,
    View.OnClickListener {
    private lateinit var model: MainActivity
    private lateinit var binding: FragmentVideoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        model = (activity as MainActivity)
        binding = FragmentVideoBinding.inflate(inflater, container, false)

        reg()
        setUpModel()
        setOnClick()

        return binding.root
    }

    private fun reg() {

        binding.rcRelate.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RelateVideoAdapter(this@FragmentVideo)
        }


        binding.rcSugVideo.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TopicSugVideoAdapter(binding, model, this@FragmentVideo)

        }

        model.getModel().relateVideo.observe(viewLifecycleOwner, Observer {
            binding.rcRelate.adapter!!.notifyDataSetChanged()
        })

        model.getModel().sugAlbums.observe(viewLifecycleOwner, Observer {
            binding.rcSugVideo.adapter!!.notifyDataSetChanged()
        })

    }

    @SuppressLint("SetTextI18n")
    private fun setUpModel() {
        model.getModel().infoAlbum.observe(viewLifecycleOwner, Observer {
            binding.viewVideo.text = it.listenSong + " lượt xem"
            binding.infoVideoData = it
            binding.videoplayer.setUp(it.linkMusic,
                JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,
                it.nameSong)

            LoadDataUtils.loadImgBitMap(context, binding.videoplayer.thumbImageView, it.imgSong)
            if (model.getModel().infoAlbum.value!!.category ==""){
                binding.categories.visibility = View.GONE
            }
            if (model.getModel().infoAlbum.value!!.singerSong ==""){
                binding.singerSong.visibility = View.GONE
            }
            if (model.getModel().infoAlbum.value!!.yearSong ==""){
                binding.yearSong.visibility = View.GONE
            }
            if (model.getModel().infoAlbum.value!!.authorSong ==""){
                binding.authorSong.visibility = View.GONE
            }
        })




    }

    private fun setOnClick() {
        binding.reTitle.setOnClickListener(this)
    }


    override fun onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos()
    }

    var cout = 0
    override fun onClick(v: View?) {
        when (v) {
            binding.reTitle -> {
                if (cout == 0) {
                    cout++
                    binding.rcRelate.animate()
                        .translationY(binding.infoVideo.height.toFloat() + 82).duration =
                        10
                    binding.rcSugVideo.animate()
                        .translationY(binding.infoVideo.height.toFloat() + 82).duration =
                        10
                    binding.moreDown.visibility = View.INVISIBLE
                    binding.moreUp.visibility = View.VISIBLE
                    binding.infoVideo.visibility = View.VISIBLE
                    binding.viewLine.visibility = View.VISIBLE

                } else if (cout == 1) {
                    cout--
                    binding.rcRelate.animate().translationY(0f)
                    binding.rcSugVideo.animate().translationY(0f);
                    binding.moreDown.visibility = View.VISIBLE
                    binding.moreUp.visibility = View.INVISIBLE
                    binding.infoVideo.visibility = View.GONE
                    binding.viewLine.visibility = View.INVISIBLE

                }

            }
        }
    }

    override fun getRelateVideoData(position: Int): ItemSong {
        return model.getModel().relateVideo.value!![position]

    }

    override fun getOnClickRelateVideo(position: Int) {
        model.getModel().getRelateVideo(model.getModel().relateVideo.value!![position].linkSong)
        model.getModel().getInfo(model.getModel().relateVideo.value!![position].linkSong)
        model.getModel().sugVideoMusic(model.getModel().relateVideo.value!![position].linkSong)
        binding.rcRelate.animate().translationY(0f)
        binding.rcSugVideo.animate().translationY(0f)


    }

    override fun getRelateVideoCount(): Int {
        return if (model.getModel().relateVideo.value == null) {
            0

        } else {
            model.getModel().relateVideo.value!!.size
        }
    }



    override fun getSugAlbumsCout(): Int {
        return if (model.getModel().sugAlbums.value == null) {
            0

        } else {
            model.getModel().sugAlbums.value!!.size
        }
    }

    override fun getSugAlbumsData(position: Int): ItemMusicList<ItemSong> {
        return model.getModel().sugAlbums.value!![position]
    }


    override fun onDestroy() {
        super.onDestroy()
        JCVideoPlayerStandard.releaseAllVideos()
    }

}