package com.example.recyclerviewpool.adapter.discover.song

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemMvSongBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class MVSongAlbumAdapter : RecyclerView.Adapter<MVSongAlbumAdapter.ItemRelateMVSongViewHolder> {
    var iItemSong: interItemSong

    constructor(iItemSong: interItemSong) {
        this.iItemSong = iItemSong
    }

    interface interItemSong {
        fun getMVCount(): Int
        fun getMVData(position: Int): ItemSong
        fun getOnClickMVItem(position: Int)
    }

    class ItemRelateMVSongViewHolder(val binding: ItemMvSongBinding) : RecyclerView.ViewHolder(binding.root)


    @SuppressLint("ResourceAsColor")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRelateMVSongViewHolder {

        val holder = ItemRelateMVSongViewHolder(
            ItemMvSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return holder
    }

    override fun onBindViewHolder(holderMV: ItemRelateMVSongViewHolder, position: Int) {
        val data = iItemSong.getMVData(position)
        holderMV.binding.itemMV = data
        holderMV.itemView.setOnClickListener{
            iItemSong.getOnClickMVItem(position)
        }
    }

    override fun getItemCount() = iItemSong.getMVCount()
}