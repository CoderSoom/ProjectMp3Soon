package com.example.recyclerviewpool.adapter.discover

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemTopicAlbumSongBinding
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.adapter.discover.video.ChilCategoriesStatusAdapter
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.viewmodel.DiscoverModel

class TopicChildCategoriesAdapter :
    RecyclerView.Adapter<TopicChildCategoriesAdapter.ItemCategoriesHolder> {
    private var sharedViewModel: DiscoverModel
    private var iCategories: ICategories
    private var fragmentDiscoverManager: ManagerFragmentDiscover
    var model: MainActivity
    var lifecycleOwner: LifecycleOwner

    constructor(
        sharedViewModel: DiscoverModel,
        model: MainActivity,
        iCategories: ICategories,
        fragmentDiscoverManager: ManagerFragmentDiscover,
        lifecycleOwner: LifecycleOwner

    ) {
        this.sharedViewModel = sharedViewModel
        this.model = model
        this.iCategories = iCategories
        this.fragmentDiscoverManager = fragmentDiscoverManager
        this.lifecycleOwner = lifecycleOwner

    }

    interface ICategories {
        fun getChildCategoriesStatusCount(): Int
        fun getChildCategoriesStatusData(position: Int): ItemMusicList<ItemSong>

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
        holder.binding.rcCategories.layoutManager =  GridLayoutManager(parent.context, 2, GridLayoutManager.VERTICAL, false)
        return holder
    }

    override fun onBindViewHolder(holder: ItemCategoriesHolder, parenPosition: Int) {
        val data = iCategories.getChildCategoriesStatusData(parenPosition)


        val iCategories = object : ChilCategoriesStatusAdapter.IChildCategoriesStatus {
            override fun getChildCategoriesStatusData(chilPosition: Int) = data.values[chilPosition]
            override fun getChildCategoriesStatusCount() = data.values.size

            override fun getChildCategoriesStatusOnClick(position: Int) {

                model.getDiscoverModel()
                    .getInfo(model.getDiscoverModel().childCategoriesStatus.value!![0].values[position].linkSong)

                model.getDiscoverModel().albumsChil(model.getDiscoverModel().childCategoriesStatus.value!![0].values[position].linkSong)

                model.getDiscoverModel().childCategoriesStatus.observe(lifecycleOwner, Observer {

                    sharedViewModel.setData(it[0].values[position].imgSong,
                        it[0].values[position].nameSong,
                        it[0].values[position].singerSong)
                })

                fragmentDiscoverManager.openSongAlbums()
            }


        }

        if (holder.binding.rcCategories.adapter == null) {
            holder.binding.rcCategories.adapter = ChilCategoriesStatusAdapter(iCategories)
            holder.binding.titleCategories.text = data.nameCategories

        } else {

            (holder.binding.rcCategories.adapter as ChilCategoriesStatusAdapter).iChildCategoriesStatus =
                iCategories

            holder.binding.rcCategories.adapter!!.notifyDataSetChanged()


        }

    }

    override fun getItemCount() = iCategories.getChildCategoriesStatusCount()
    override fun getItemId(position: Int): Long {
        return if (position == 0) {
            0
        } else {
            model.getDiscoverModel().childCategoriesStatus.value!![position].hashCode().toLong()
        }

    }
}