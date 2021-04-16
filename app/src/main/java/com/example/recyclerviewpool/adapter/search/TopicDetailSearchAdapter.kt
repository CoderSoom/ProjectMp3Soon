package com.example.recyclerviewpool.adapter.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemTopicAlbumSongBinding
import com.example.recyclerviewpool.model.itemdata.ItemCategories
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.view.fragment.search.ManagerFragmentSearch
import com.example.recyclerviewpool.viewmodel.DiscoverModel
import com.example.recyclerviewpool.viewmodel.SearchModel

class TopicDetailSearchAdapter :
    RecyclerView.Adapter<TopicDetailSearchAdapter.ItemCategoriesHolder> {
    private  var managerDiscover= ManagerFragmentDiscover()
    private var iCategories: ICategories

    private var model : MainActivity

    constructor(

         iCategories: ICategories, model: MainActivity
    ) {

        this.iCategories = iCategories
        this.model = model



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

        holder.binding.rcCategories.layoutManager =
            GridLayoutManager(parent.context!!, 2, GridLayoutManager.VERTICAL, false)
        return holder
    }

    override fun onBindViewHolder(holder: ItemCategoriesHolder, parenPosition: Int) {
        val data = iCategories.getCategoriesCountryData(parenPosition)


        val iCategories = object : DetailAdapter.InterCategoriesCountry {


            override fun getCategoriesCountryData(chilPosition: Int) = data.values[chilPosition]

            override fun getCategoriesCountryCount() = data.values.size

            override fun getCategoriesCountryOnClick(position: Int) {
//                sharedViewModel.setData(
//                    data.values[position].imgCategory,
//                    data.values[position].nameCategory,
//                    ""
//                )
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
            holder.binding.rcCategories.adapter = DetailAdapter(iCategories)

        } else {

            (holder.binding.rcCategories.adapter as DetailAdapter).iCategoriesCountry = iCategories

            holder.binding.rcCategories.adapter!!.notifyDataSetChanged()


        }

    }

    override fun getItemCount() = iCategories.getCategoriesCountryCount()
//    override fun getItemId(position: Int): Long {
//        return if (position == 0) {
//            0
//        } else {
//            10
//        }
//
//    }
}