package com.example.recyclerviewpool.adapter.discover

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemTopicAlbumSongBinding
import com.example.recyclerviewpool.model.PreCachingLayoutManager
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.viewmodel.DiscoverModel


class TopicTopResultAdapter : RecyclerView.Adapter<TopicTopResultAdapter.ItemCategoriesHolder> {
    private var iCategories: ICategories
    private var fragmentDiscoverManager: ManagerFragmentDiscover
    var model: MainActivity
    var sharedViewModel: DiscoverModel

    constructor(
        shareViewModel: DiscoverModel,
        model: MainActivity,
        iCategories: ICategories,
        fragmentDiscoverManager: ManagerFragmentDiscover
    ) {
        this.sharedViewModel = shareViewModel
        this.model = model
        this.iCategories = iCategories
        this.fragmentDiscoverManager = fragmentDiscoverManager

    }

    interface ICategories {
        fun getTopResultCount(): Int
        fun getTopResultData(position: Int): ItemMusicList<ItemSong>

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
           PreCachingLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
        return holder
    }

    override fun onBindViewHolder(holder: ItemCategoriesHolder, parenPosition: Int) {
        val data = iCategories.getTopResultData(parenPosition)


        val iCategories = object : TopResultAdapter.ITopResult {

            override fun getTopResultData(chilPosition: Int)= data.values[chilPosition]

            override fun getTopResultCount()=data.values!!.size

            override fun getTopResultOnClick(position: Int) {
                model.getModel().albumsChil(data.values[position].linkSong)
                sharedViewModel.setData(
                    data.values[position].imgSong,
                    data.values[position].nameSong,
                    data.values[position].singerSong
                )

                fragmentDiscoverManager.openSongAlbums()
            }

        }

        if (holder.binding.rcCategories.adapter == null) {
            val speedScroll = 3000L
            val handler = Handler()
            val runnable: Runnable = object : Runnable {
                
                var count = 0
                var flag = true
                override fun run() {
                    if (count < 20) {
                        if (count == holder.binding.rcCategories.adapter!!.itemCount - 1) {
                            flag = false
                        } else if (count == 0) {
                            flag = true
                        }
                        if (flag) count++ else count--
                        holder.binding.rcCategories.smoothScrollToPosition(count)
                        handler.postDelayed(this, speedScroll)
                    }
                }
            }

            handler.postDelayed(runnable, speedScroll)
            holder.binding.rcCategories.apply {
                adapter = TopResultAdapter(iCategories)

            }

            holder.binding.titleCategories.text = data.nameCategories

        } else {

            (holder.binding.rcCategories.adapter as TopResultAdapter).iTopResult = iCategories

            holder.binding.rcCategories.adapter!!.notifyDataSetChanged()


        }

    }

    override fun getItemCount() = iCategories.getTopResultCount()
    override fun getItemId(position: Int): Long {
        return if (position == 0) {
            0
        } else {
            model.getModel().topResult.value!![position].hashCode().toLong()
        }

    }
}