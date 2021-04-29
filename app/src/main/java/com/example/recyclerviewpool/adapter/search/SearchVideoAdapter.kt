package com.example.recyclerviewpool.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemSearchVideoBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.viewmodel.SearchModel

class SearchVideoAdapter : RecyclerView.Adapter<SearchVideoAdapter.SearchSongHolder> {
    var iSearchVideo: ISearchVideo


    constructor(iSearchVideo: ISearchVideo) {
        this.iSearchVideo = iSearchVideo

    }

    interface ISearchVideo {
        fun getSearchVideoCount(): Int
        fun getSearchVideoData(position: Int): ItemSong?
        fun getSearchVideoOnClickItem(position: Int)
    }

    class SearchSongHolder(val binding: ItemSearchVideoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSongHolder {
        var binding = ItemSearchVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchSongHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchSongHolder, position: Int) {
        holder.binding.searchSong = iSearchVideo.getSearchVideoData(position)
        holder.itemView.setOnClickListener{
            iSearchVideo.getSearchVideoOnClickItem(position)
        }
    }

    override fun getItemCount() = iSearchVideo.getSearchVideoCount()
}

