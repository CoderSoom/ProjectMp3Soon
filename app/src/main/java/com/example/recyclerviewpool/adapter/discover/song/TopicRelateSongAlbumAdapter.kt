package com.example.recyclerviewpool.adapter.discover.song

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.databinding.ItemRelateAlbumSongBinding
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.view.MainActivity

class TopicRelateSongAlbumAdapter : RecyclerView.Adapter<TopicRelateSongAlbumAdapter.RelateSongViewHolder> {
    private var iCategories: ICategories
    private var managerDiscover: ManagerFragmentDiscover
    var model: MainActivity


    constructor(
        model: MainActivity,
        iCategories: ICategories,
        managerDiscover: ManagerFragmentDiscover
    ) {
        this.model = model
        this.iCategories = iCategories
        this.managerDiscover = managerDiscover

    }

    interface ICategories {
        fun getSizeCategories(): Int
        fun getItemCategories(position: Int): ItemMusicList<ItemSong>

    }

    class RelateSongViewHolder(val binding: ItemRelateAlbumSongBinding) :
        RecyclerView.ViewHolder(binding.root){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelateSongViewHolder {

        val holder = RelateSongViewHolder(
            ItemRelateAlbumSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )

        holder.binding.rcCategoriesRelate.layoutManager =
            LinearLayoutManager(parent.context, LinearLayoutManager.VERTICAL, false)
        return holder
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RelateSongViewHolder, parenPosition: Int) {
        val data = iCategories.getItemCategories(parenPosition)


        val iCategories = object : RelateSongAlbumAdapter.interItemSong {

            override fun getCount() = data.values.size

            override fun getData(chilPosition: Int) = data.values[chilPosition]

            override fun getOnClickItem(position: Int) {
                model.getModel().albumsChil(data.values[position].linkSong)
//                model.getModel().getInfo(data.values[position].linkSong)
                managerDiscover.openSongAlbums()


            }

        }

        if (holder.binding.rcCategoriesRelate.adapter == null) {
            holder.binding.rcCategoriesRelate.adapter = RelateSongAlbumAdapter(iCategories)
            holder.binding.titleCategories.text = data.nameCategories
            holder.binding.titleCategories.setTextColor(R.color.colorWhite)

        } else {

            (holder.binding.rcCategoriesRelate.adapter as RelateSongAlbumAdapter).iItemSong =
                iCategories

            holder.binding.rcCategoriesRelate.adapter!!.notifyDataSetChanged()


        }

    }

    override fun getItemCount() = iCategories.getSizeCategories()
    override fun getItemId(position: Int): Long {
        return if (position == 0) {
            0
        } else {
            10
        }

    }
}