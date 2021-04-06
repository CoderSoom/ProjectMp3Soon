package com.example.recyclerviewpool.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemSearchSongPlaylistBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class SearchAlbumAdapter : RecyclerView.Adapter<SearchAlbumAdapter.SearchSongHolder> {
    var iSearchAlbum: ISearchAlbum

    constructor(iSearchAlbum: ISearchAlbum) {
        this.iSearchAlbum = iSearchAlbum
    }

    interface ISearchAlbum {
        fun getSearchAlbumCount(): Int
        fun getSearchAlbumData(position: Int): ItemSong
        fun getSearchAlbumOnClickItem(position: Int)
    }

    class SearchSongHolder(val binding: ItemSearchSongPlaylistBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSongHolder {
        var binding = ItemSearchSongPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchSongHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchSongHolder, position: Int) {
        holder.binding.searchSong = iSearchAlbum.getSearchAlbumData(position)
        holder.itemView.setOnClickListener{
            iSearchAlbum.getSearchAlbumOnClickItem(position)
        }
    }

    override fun getItemCount() = iSearchAlbum.getSearchAlbumCount()
}

