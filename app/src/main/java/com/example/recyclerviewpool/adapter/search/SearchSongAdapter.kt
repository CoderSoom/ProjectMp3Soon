package com.example.recyclerviewpool.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemSearchSongPlaylistBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class SearchSongAdapter : RecyclerView.Adapter<SearchSongAdapter.SearchSongHolder> {
    var iSearchSong: ISearchSong

    constructor(iSearchSong: ISearchSong) {
        this.iSearchSong = iSearchSong
    }

    interface ISearchSong {
        fun getSearchSongCount(): Int
        fun getSearchSongData(position: Int): ItemSong?
        fun getSearchSongOnClickItem(position: Int)
    }

    class SearchSongHolder(val binding: ItemSearchSongPlaylistBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSongHolder {
        var binding = ItemSearchSongPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchSongHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchSongHolder, position: Int) {
        holder.binding.searchSong = iSearchSong.getSearchSongData(position)
        holder.itemView.setOnClickListener{
            iSearchSong.getSearchSongOnClickItem(position)
        }
    }

    override fun getItemCount() = iSearchSong.getSearchSongCount()
}

