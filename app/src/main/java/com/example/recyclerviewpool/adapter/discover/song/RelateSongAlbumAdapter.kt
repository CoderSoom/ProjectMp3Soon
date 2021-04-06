package com.example.recyclerviewpool.adapter.discover.song

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemRelateSongBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class RelateSongAlbumAdapter : RecyclerView.Adapter<RelateSongAlbumAdapter.ItemRelateSongViewHolder> {
    var iItemSong: interItemSong

    constructor(iItemSong: interItemSong) {
        this.iItemSong = iItemSong
    }

    interface interItemSong {
        fun getCount(): Int
        fun getData(position: Int): ItemSong
        fun getOnClickItem(position: Int)
    }

    class ItemRelateSongViewHolder(val binding: ItemRelateSongBinding) : RecyclerView.ViewHolder(binding.root)


    @SuppressLint("ResourceAsColor")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRelateSongViewHolder {

        val holder = ItemRelateSongViewHolder(
            ItemRelateSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return holder
    }

    override fun onBindViewHolder(holder: ItemRelateSongViewHolder, position: Int) {
        val data = iItemSong.getData(position)
        holder.binding.itemRelateSong = data
        holder.itemView.setOnClickListener{
            iItemSong.getOnClickItem(position)
        }
    }

    override fun getItemCount() = iItemSong.getCount()
}