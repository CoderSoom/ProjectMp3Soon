package com.example.recyclerviewpool.adapter.discover.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.FragmentVideoBinding
import com.example.recyclerviewpool.databinding.ItemTopicAlbumSongBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.view.MainActivity

class TopicSugVideoAdapter : RecyclerView.Adapter<TopicSugVideoAdapter.ItemCategoriesHolder> {
    private var iCategories: ICategories
    private var model: MainActivity
    private var binding : FragmentVideoBinding


    constructor(
        binding: FragmentVideoBinding,
        model: MainActivity,
        iCategories: ICategories
    ) {

        this.iCategories = iCategories
        this.model = model
        this.binding = binding


    }

    interface ICategories {
        fun getSugAlbumsCout(): Int
        fun getSugAlbumsData(position: Int): ItemMusicList<ItemSong>

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
        val data = iCategories.getSugAlbumsData(parenPosition)


        val iCategories = object : SugVideoAdapter.ISugVideo {
            override fun getSugVideoData(chilPosition: Int)= data.values[chilPosition]

            override fun getOnClickSugVideo(position: Int) {
                model.getModel().getRelateVideo(data.values[position].linkSong)
                model.getModel().getInfo(data.values[position].linkSong)
                model.getModel().sugVideoMusic(data.values[position].linkSong)
                binding.rcRelate.animate().translationY(0f)
            }

            override fun getSugVideoCount()= 10


        }

        if (holder.binding.rcCategories.adapter == null) {
            holder.binding.rcCategories.adapter = SugVideoAdapter(iCategories)
            holder.binding.titleCategories.text = "Video Khác"

        } else {

            (holder.binding.rcCategories.adapter as SugVideoAdapter).iSugVideo = iCategories
            holder.binding.rcCategories.adapter!!.notifyDataSetChanged()


        }

    }

    override fun getItemCount() = iCategories.getSugAlbumsCout()
    override fun getItemId(position: Int): Long {
        return if (position == 0) {
            0
        } else {
            10
        }

    }
}