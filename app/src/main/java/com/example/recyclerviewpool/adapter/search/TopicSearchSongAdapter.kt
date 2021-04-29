package com.example.recyclerviewpool.adapter.search

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemTopicAlbumSongBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.view.fragment.search.ManagerFragmentSearch
import com.example.recyclerviewpool.viewmodel.LoadDataUtils
import com.example.recyclerviewpool.viewmodel.SearchModel
import com.example.recyclerviewpool.viewmodel.SetDataSlidingPanel

class TopicSearchSongAdapter : RecyclerView.Adapter<TopicSearchSongAdapter.ItemCategoriesHolder> {
    private var iCategories: ICategories
    private var fragmentSearchManager: ManagerFragmentSearch
    private var model : MainActivity
    var lifecycleOwner: LifecycleOwner
    private var sharedViewModel : SearchModel

    constructor(
        sharedViewModel: SearchModel,
        model: MainActivity,
        iCategories: ICategories,
        fragmentSearchManager: ManagerFragmentSearch,
        lifecycleOwner: LifecycleOwner
    ) {
        this.sharedViewModel = sharedViewModel
        this.model = model
        this.iCategories = iCategories
        this.fragmentSearchManager = fragmentSearchManager
        this.lifecycleOwner = lifecycleOwner

    }

    interface ICategories {
        fun getSearchSongCount(): Int
        fun getSearchSongData(position: Int): ItemMusicList<ItemSong>?

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
        val data = iCategories.getSearchSongData(parenPosition)


        val iCategories = object : SearchSongAdapter.ISearchSong {
            override fun getSearchSongCount() :Int{
                return if (iCategories.getSearchSongCount() ==0){
                    0
                }else{
                    3
                }
            }

            override fun getSearchSongData(chilPosition: Int): ItemSong? {
                if (iCategories.getSearchSongCount() == 0 || data == null || data.values.size ==0) {
                    return null
                } else {
                    return data.values[chilPosition]
                }
            }

            override fun getSearchSongOnClickItem(position: Int) {  LoadDataUtils.hideSoftKeyboard(model)
                LoadDataUtils.hideSoftKeyboard(model)
                model.getPlaySevice()!!.currentPositionSong = position
                sharedViewModel.setData(
                    data!!.values[position].imgSong,
                    data.values[position].nameSong,
                    data.values[position].singerSong
                )

                SetDataSlidingPanel.setDataSlidingPanel(model,data.values!!,  position)
                model.getDiscoverModel().infoAlbum.observe(lifecycleOwner, Observer
                {
                    SetDataSlidingPanel.setDataMusic(model, data.values!!, it, position)
                })

            }


        }

        if (holder.binding.rcCategories.adapter == null) {
            holder.binding.rcCategories.adapter = SearchSongAdapter(iCategories)
            holder.binding.titleCategories.text = data!!.nameCategories
            holder.binding.titleCategories.setTextColor(Color.WHITE)

        } else {

            (holder.binding.rcCategories.adapter as SearchSongAdapter).iSearchSong = iCategories

            holder.binding.rcCategories.adapter!!.notifyDataSetChanged()


        }

    }

    override fun getItemCount() = iCategories.getSearchSongCount()
    override fun getItemId(position: Int): Long {
        return if (position == 0) {
            0
        } else {
            10
        }

    }
}