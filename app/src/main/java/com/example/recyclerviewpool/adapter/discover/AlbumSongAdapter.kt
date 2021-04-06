package com.example.recyclerviewpool.adapter.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemSongBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class AlbumSongAdapter : RecyclerView.Adapter<AlbumSongAdapter.ItemSongHolder> {
    var iItemSong: interItemSong

    constructor(iItemSong: interItemSong) {
        this.iItemSong = iItemSong
    }

    interface interItemSong {
        fun getCount(): Int
        fun getData(position: Int): ItemSong
        fun getOnClickItem(position: Int)
    }

    class ItemSongHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSongHolder {

        val holder = ItemSongHolder(
            ItemSongBinding.inflate(
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


