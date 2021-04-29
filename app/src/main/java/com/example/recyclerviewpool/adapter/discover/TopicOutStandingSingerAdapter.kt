package com.example.recyclerviewpool.adapter.discover

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemTopicAlbumSongBinding
import com.example.recyclerviewpool.model.PreCachingLayoutManager
import com.example.recyclerviewpool.model.itemdata.ItemCategories
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.viewmodel.DiscoverModel

class TopicOutStandingSingerAdapter :
    RecyclerView.Adapter<TopicOutStandingSingerAdapter.ItemCategoriesHolder> {
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
        fun getOutStandingSingerCount(): Int
        fun getOutStandingSingerData(position: Int): ItemMusicList<ItemCategories>

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
        val data = iCategories.getOutStandingSingerData(parenPosition)


        val iCategories = object : OutStandingSingerAdapter.IOutStandingSinger {


            override fun getOutStandingData(chilPosition: Int) = data.values[chilPosition]

            override fun getOutStandingCount() = data.values!!.size

            override fun getOutStandingOnClick(position: Int) {
                model.getDiscoverModel().albumsChilSinger(model.getDiscoverModel().outstandingSinger.value!![0].values[position].linkCategory)
                sharedViewModel.setData(
                    data.values[position].imgCategory,
                    data.values[position].nameCategory,
                   ""
                )
                managerDiscover.openSongAlbumsSinger()

            }

        }

        if (holder.binding.rcCategories.adapter == null) {
            holder.binding.rcCategories.adapter = OutStandingSingerAdapter(iCategories)
            holder.binding.titleCategories.text = data.nameCategories

        } else {

            (holder.binding.rcCategories.adapter as OutStandingSingerAdapter).iOutStandingSinger =
                iCategories

            holder.binding.rcCategories.adapter!!.notifyDataSetChanged()


        }

    }

    override fun getItemCount() = iCategories.getOutStandingSingerCount()
    override fun getItemId(position: Int): Long {
        return if (position == 0) {
            0
        } else {
            model.getDiscoverModel().outstandingSinger.value!![position].hashCode().toLong()
        }

    }
}