package com.example.recyclerviewpool.adapter.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.recyclerviewpool.viewmodel.SetDataSlidingPanel
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.sliding_up_panel.view.*

class TopicNewSongAdapter : RecyclerView.Adapter<TopicNewSongAdapter.ItemCategoriesHolder> {
    private var iCategories: ICategories
    private var managerDiscover: ManagerFragmentDiscover
    var model: MainActivity
     var lifecycleOwner: LifecycleOwner
    private var slidingUpPanelLayout: MainActivity
    var sharedViewModel: DiscoverModel

    constructor(
        shareViewModel: DiscoverModel,
        model: MainActivity,
        iCategories: ICategories,
        managerDiscover: ManagerFragmentDiscover,
        slidingUpPanelLayout: MainActivity,
        lifecycleOwner: LifecycleOwner

    ) {
        this.sharedViewModel = shareViewModel
        this.model = model
        this.iCategories = iCategories
        this.managerDiscover = managerDiscover
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
                model.songAlbums = data.values
                model.getPlaySevice()!!.currentPositionSong = position
                SetDataSlidingPanel.setDataSlidingPanel(model,data.values, position)
                model.getDiscoverModel().infoAlbum.observe(lifecycleOwner, Observer {
                    SetDataSlidingPanel.setDataMusic(model, data.values, it, position)
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
            model.getDiscoverModel().newSong.value!![position].hashCode().toLong()
        }

    }
}