package com.example.recyclerviewpool.adapter.discover

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemTopicAlbumSongBinding
import com.example.recyclerviewpool.model.itemdata.ItemCategories
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.viewmodel.DiscoverModel

class TopicCategoriesCountryAdapter : RecyclerView.Adapter<TopicCategoriesCountryAdapter.ItemCategoriesHolder> {
    private var iCategories: ICategories
    private var managerDiscover: ManagerFragmentDiscover
    var model: MainActivity
    var sharedViewModel: DiscoverModel

    constructor(
        shareViewModel: DiscoverModel,
        model: MainActivity,
        iCategories: ICategories,
        managerDiscover: ManagerFragmentDiscover
    ) {
        this.sharedViewModel = shareViewModel
        this.model = model
        this.iCategories = iCategories
        this.managerDiscover = managerDiscover

    }

    interface ICategories {
        fun getCategoriesCountryCount(): Int
        fun getCategoriesCountryData(position: Int): ItemMusicList<ItemCategories>

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
            GridLayoutManager(parent.context!!, 2, GridLayoutManager.VERTICAL, false)
        return holder
    }

    override fun onBindViewHolder(holder: ItemCategoriesHolder, parenPosition: Int) {
        val data = iCategories.getCategoriesCountryData(parenPosition)


        val iCategories = object : CategoriesCountryAdapter.InterCategoriesCountry {


            override fun getCategoriesCountryData(chilPosition: Int)= data.values[chilPosition]

            override fun getCategoriesCountryCount()=4

            override fun getCategoriesCountryOnClick(position: Int) {
                sharedViewModel.setData(
                    data.values[position].imgCategory,
                    data.values[position].nameCategory,
                    ""
                )
                var endLink = data.values[position].linkCategory.endsWith("video.html")
                if (endLink){

                    model.getDiscoverModel().albumsChil(data.values[position].linkCategory)
                    model.getDiscoverModel().getInfo(data.values[position].linkCategory)

                    managerDiscover.openAlbumRankingCountry()
                }else {
                    model.getDiscoverModel().albumsChil(data.values[position].linkCategory)
                    model.getDiscoverModel().getInfo(data.values[position].linkCategory)
                    managerDiscover.openSongAlbums()
                }
            }


        }

        if (holder.binding.rcCategories.adapter == null) {
            holder.binding.rcCategories.adapter = CategoriesCountryAdapter(iCategories)
            holder.binding.titleCategories.text = data.nameCategories

        } else {

            (holder.binding.rcCategories.adapter as CategoriesCountryAdapter).iCategoriesCountry = iCategories

            holder.binding.rcCategories.adapter!!.notifyDataSetChanged()


        }

    }

    override fun getItemCount() = iCategories.getCategoriesCountryCount()
    override fun getItemId(position: Int): Long {
        return if (position == 0) {
            0
        } else {
            model.getDiscoverModel().categoriesCountry.value!![position].hashCode().toLong()
        }

    }
}