package com.example.recyclerviewpool.adapter.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemTopicAlbumSongBinding
import com.example.recyclerviewpool.model.PreCachingLayoutManager
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.viewmodel.DiscoverModel
import com.example.recyclerviewpool.viewmodel.LoadDataUtils
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.sliding_up_panel.view.*

class TopicNewSongAdapter : RecyclerView.Adapter<TopicNewSongAdapter.ItemCategoriesHolder> {
    private var iCategories: ICategories
    private var fragmentDiscoverManager: ManagerFragmentDiscover
    var model: MainActivity
     var lifecycleOwner: LifecycleOwner
    private var slidingUpPanelLayout: MainActivity
    var sharedViewModel: DiscoverModel

    constructor(
        shareViewModel: DiscoverModel,
        model: MainActivity,
        iCategories: ICategories,
        fragmentDiscoverManager: ManagerFragmentDiscover,
        slidingUpPanelLayout: MainActivity,
        lifecycleOwner: LifecycleOwner

    ) {
        this.sharedViewModel = shareViewModel
        this.model = model
        this.iCategories = iCategories
        this.fragmentDiscoverManager = fragmentDiscoverManager
        this.slidingUpPanelLayout = slidingUpPanelLayout
        this.lifecycleOwner= lifecycleOwner

    }

    interface ICategories {
        fun getNewSongCount(): Int
        fun getNewSongData(position: Int): ItemMusicList<ItemSong>

    }

    class ItemCategoriesHolder(val binding: ItemTopicAlbumSongBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoriesHolder {

        val holder = ItemCategoriesHolder(
            ItemTopicAlbumSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        holder.binding.rcCategories.setRecycledViewPool(RecyclerView.RecycledViewPool())
        holder.binding.rcCategories.layoutManager =
            PreCachingLayoutManager(parent.context, LinearLayoutManager.VERTICAL, false)
        return holder
    }

    override fun onBindViewHolder(holder: ItemCategoriesHolder, parenPosition: Int) {
        val data = iCategories.getNewSongData(parenPosition)


        val iCategories = object : NewSongAdapter.INewSong {


            override fun getNewSongData(chilPosition: Int) = data.values[chilPosition]

            override fun getNewSongCount()= 5

            override fun getNewSongOnClick(position: Int) {
                model.getPlaySevice()!!.currentPositionSong = position

                (slidingUpPanelLayout.getSlidingPanelUp()).panelState = SlidingUpPanelLayout.PanelState.EXPANDED
                (slidingUpPanelLayout.getSlidingPanelUp()).addPanelSlideListener(object :
                    SlidingUpPanelLayout.PanelSlideListener {
                    override fun onPanelSlide(panel: View?, slideOffset: Float) {
                        panel!!.slide_play_song_mini.alpha = 1 - slideOffset
                    }

                    override fun onPanelStateChanged(
                        panel: View?,
                        previousState: SlidingUpPanelLayout.PanelState?,
                        newState: SlidingUpPanelLayout.PanelState
                    ) {
                        if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                            (slidingUpPanelLayout.getSlidingPanelUp()).slide_play_song_big.visibility =
                                View.VISIBLE
                        } else {
                            (slidingUpPanelLayout.getSlidingPanelUp()).slide_play_song_big.visibility =
                                View.INVISIBLE
                        }
                    }
                })
//        if ((activity as MainActivity).getAsyPlay()!!.isRunning) {
//            (activity as MainActivity).getAsyPlay()!!.cancel(true)
//            (activity as MainActivity).panelAsynTask()
//        }
                model.getModel().getInfo(model.getModel().newSong.value!![0]!!.values[position].linkSong)
                model.getModel().getRelateSong(model.getModel().newSong.value!![0]!!.values[position].linkSong)
                model.getModel().getMVSong(model.getModel().newSong.value!![0]!!.values[position].linkSong)

                /////SetLink Music Player
                model.getModel().infoAlbum.observe(lifecycleOwner, Observer {
                    LoadDataUtils.loadImgBitMapBlur(model,
                        (slidingUpPanelLayout.getSlidingPanelUp()).bg_song,
                        it.imgSong)
                    model.getModel().newSong.value!![0]!!.values[model.getPlaySevice()!!.currentPositionSong].linkMusic =
                        it.linkMusic
                    model.getModel().newSong.value!![0]!!.values[model.getPlaySevice()!!.currentPositionSong].nameSong =
                        it.nameSong


                    model.getPlaySevice()!!.releaseMusic()
                    model.getPlaySevice()!!
                        .setDataMusicOnline(model.getModel().newSong.value!![0]!!.values[model.getPlaySevice()!!.currentPositionSong],
                            position,
                            model.getModel().newSong.value!![0]!!.values!!)

                })



                model.getModel().newSong.observe(lifecycleOwner, Observer
                {
                    (slidingUpPanelLayout.getSlidingPanelUp()).slide_play_song_big.play_nameSong.text =
                        it[0].values[position].nameSong
                    (slidingUpPanelLayout.getSlidingPanelUp()).slide_play_song_big.play_singerSong.text =
                        it[0].values[position].singerSong


                    //setName Slide Panel Up
                    (slidingUpPanelLayout.getSlidingPanelUp()).slide_play_song_mini.nameSong.text =
                        it[0].values[position].nameSong
                    (slidingUpPanelLayout.getSlidingPanelUp()).slide_play_song_mini.singerSong.text =
                        it[0].values[position].singerSong
                })

            }

        }

        if (holder.binding.rcCategories.adapter == null) {
            holder.binding.rcCategories.adapter = NewSongAdapter(iCategories)
            holder.binding.titleCategories.text = data.nameCategories

        } else {

            (holder.binding.rcCategories.adapter as NewSongAdapter).iNewSong = iCategories

            holder.binding.rcCategories.adapter!!.notifyDataSetChanged()


        }

    }

    override fun getItemCount() = iCategories.getNewSongCount()
    override fun getItemId(position: Int): Long {
        return if (position == 0) {
            0
        } else {
            model.getModel().newSong.value!![position].hashCode().toLong()
        }

    }
}