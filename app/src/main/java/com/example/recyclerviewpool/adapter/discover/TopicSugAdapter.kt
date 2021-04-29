package com.example.recyclerviewpool.adapter.discover

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
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.viewmodel.DiscoverModel
import com.example.recyclerviewpool.viewmodel.SetDataSlidingPanel

class TopicSugAdapter : RecyclerView.Adapter<TopicSugAdapter.ItemCategoriesHolder> {
    private  var managerDiscover: ManagerFragmentDiscover
    private var iCategories: ICategories
    private var model: MainActivity
    var sharedViewModel: DiscoverModel
    var lifecycleOwner: LifecycleOwner


    constructor(
        shareViewModel: DiscoverModel,
        model: MainActivity,
        iCategories: ICategories,
        managerDiscover: ManagerFragmentDiscover,
        lifecycleOwner: LifecycleOwner
    ) {
        this.sharedViewModel = shareViewModel
        this.iCategories = iCategories
        this.model = model
        this.managerDiscover = managerDiscover
        this.lifecycleOwner= lifecycleOwner




    }

    interface ICategories {
        fun getSugDataCout(): Int
        fun getSugData(position: Int): ItemMusicList<ItemSong>
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
           LinearLayoutManager(parent.context)
        return holder
    }

    override fun onBindViewHolder(holder: ItemCategoriesHolder, parenPosition: Int) {
        val data = iCategories.getSugData(parenPosition)


        val iCategories = object : SugAdapter.ISug {
            override fun getSugData(chilPosition: Int)= data.values[chilPosition]

            override fun getOnClickSug(position: Int) {
                model.songAlbums = data.values
                model.getPlaySevice()!!.currentPositionSong = position
                SetDataSlidingPanel.setDataSlidingPanel(model,data.values, position)
                model.getDiscoverModel().infoAlbum.observe(lifecycleOwner, Observer {
                    SetDataSlidingPanel.setDataMusic(model, data.values, it, position)
                })

            }

            override fun getSugCout()=5


        }

        if (holder.binding.rcCategories.adapter == null) {
            holder.binding.rcCategories.adapter = SugAdapter(iCategories)
            holder.binding.titleCategories.text = data.nameCategories

        } else {

            (holder.binding.rcCategories.adapter as SugAdapter).iSug = iCategories
            holder.binding.rcCategories.adapter!!.notifyDataSetChanged()


        }

    }

    override fun getItemCount() = iCategories.getSugDataCout()
    override fun getItemId(position: Int): Long {
        return if (position == 0) {
            0
        } else {
            10
        }

    }
}