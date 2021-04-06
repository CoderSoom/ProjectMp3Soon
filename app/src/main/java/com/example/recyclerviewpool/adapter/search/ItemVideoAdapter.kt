package com.example.recyclerviewpool.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemVideoBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class ItemVideoAdapter : RecyclerView.Adapter<ItemVideoAdapter.ItemSongHolder> {
    var iItemSong: interItemSong

    constructor(iItemSong: interItemSong) {
        this.iItemSong = iItemSong
    }

    interface interItemSong {
        fun getCount(): Int
        fun getData(position: Int): ItemSong
        fun getOnClickItem(position: Int)
    }

    class ItemSongHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSongHolder {

        val holder = ItemSongHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return holder
    }

    override fun onBindViewHolder(holder: ItemSongHolder, position: Int) {
        val data = iItemSong.getData(position)
        holder.binding.subItemSong = data
        holder.itemView.setOnClickListener{
            iItemSong.getOnClickItem(position)
        }
    }

    override fun getItemCount() = iItemSong.getCount()
}


