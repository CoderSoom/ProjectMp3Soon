package com.example.recyclerviewpool.adapter.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemTopicAlbumSongBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.viewmodel.DiscoverModel

class TopicAlbumSongAdapter : RecyclerView.Adapter<TopicAlbumSongAdapter.ItemCategoriesHolder> {
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
        fun getNewAlbumCount(): Int
        fun getNewAlbumData(position: Int): ItemMusicList<ItemSong>

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

        holder.binding.rcCategories.layoutManager =
            GridLayoutManager(parent.context, 2, GridLayoutManager.HORIZONTAL, false)
        return holder
    }

    override fun onBindViewHolder(holder: ItemCategoriesHolder, parenPosition: Int) {
        val data = iCategories.getNewAlbumData(parenPosition)


        val iCategories = object : AlbumSongAdapter.interItemSong {

            override fun getCount() = 10

            override fun getData(chilPosition: Int) = data.values[chilPosition]

            override fun getOnClickItem(position: Int) {
                model.getDiscoverModel().albumsChil(data.values[position].linkSong)
                model.getDiscoverModel().getInfo(data.values[position].linkSong)
                sharedViewModel.setData(
                    data.values[position].imgSong,
                    data.values[position].nameSong,
                    data.values[position].singerSong
                )
                fragmentDiscoverManager.openSongAlbums()

            }

        }

        if (holder.binding.rcCategories.adapter == null) {
            holder.binding.rcCategories.adapter = AlbumSongAdapter(iCategories)
            holder.binding.titleCategories.text = data.nameCategories

        } else {

            (holder.binding.rcCategories.adapter as AlbumSongAdapter).iItemSong = iCategories

            holder.binding.rcCategories.adapter!!.notifyDataSetChanged()


        }

    }

    override fun getItemCount() = iCategories.getNewAlbumCount()
    override fun getItemId(position: Int): Long {
        return if (position == 0) {
            0
        } else {
            10
        }

    }
}