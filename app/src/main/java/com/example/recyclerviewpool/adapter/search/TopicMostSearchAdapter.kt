package com.example.recyclerviewpool.adapter.search

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemTopicAlbumSongBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.view.fragment.search.ManagerFragmentSearch

class TopicMostSearchAdapter : RecyclerView.Adapter<TopicMostSearchAdapter.ItemCategoriesHolder> {
    private var iCategories: ICategories
    private var fragmentSearchManager: ManagerFragmentSearch


    constructor(
        iCategories: ICategories,
        fragmentSearchManager: ManagerFragmentSearch
    ) {
        this.iCategories = iCategories
        this.fragmentSearchManager = fragmentSearchManager

    }

    interface ICategories {
        fun getMostSearchCount(): Int
        fun getMostSearchData(position: Int): ItemMusicList<ItemSong>


    }

    class ItemCategoriesHolder(val binding: ItemTopicAlbumSongBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoriesHolder {

        val holder = ItemCategoriesHolder(
            ItemTopicAlbumSongBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

        holder.binding.rcCategories.layoutManager =
           LinearLayoutManager(parent.context)
        return holder
    }

    override fun onBindViewHolder(holder: ItemCategoriesHolder, parenPosition: Int) {
        val data = iCategories.getMostSearchData(parenPosition)


        val iCategories = object : MostSearchedAdapter.InterMostSearched {
            override fun getMostSearchedCount() = data.values.size

            override fun getMostSearchedData(chilPosition: Int)= data.values[chilPosition]

            override fun getMostSearchedOnClickItem(position: Int) {
//                model.getModel().albumsChil(data.values[position].linkSong)
//                model.getModel().getInfo(data.values[position].linkSong)
//                sharedViewModel.setData(
//                    data.values[position].imgSong,
//                    data.values[position].nameSong,
//                    data.values[position].singerSong
//                )
//                fragmentSearchManager.openSongAlbums()
            }


        }

        if (holder.binding.rcCategories.adapter == null) {
            holder.binding.rcCategories.adapter = MostSearchedAdapter(iCategories)
            holder.binding.titleCategories.text = data.nameCategories
            holder.binding.titleCategories.setTextColor(Color.WHITE)

        } else {

            (holder.binding.rcCategories.adapter as MostSearchedAdapter).iMostSearched = iCategories

            holder.binding.rcCategories.adapter!!.notifyDataSetChanged()


        }

    }

    override fun getItemCount() = iCategories.getMostSearchCount()
    override fun getItemId(position: Int): Long {
        return if (position == 0) {
            0
        } else {
            5
        }

    }
}