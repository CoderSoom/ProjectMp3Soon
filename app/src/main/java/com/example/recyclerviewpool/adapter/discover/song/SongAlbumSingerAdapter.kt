package com.example.recyclerviewpool.adapter.discover.song

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemAlbumSingerBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class SongAlbumSingerAdapter : RecyclerView.Adapter<SongAlbumSingerAdapter.SongAlbumSingerViewHolder> {
    var iAlbum: IAlbum

    constructor(iAlbum: IAlbum) {
        this.iAlbum = iAlbum
    }

    interface IAlbum {
        fun getAlbumSingerCount(): Int
        fun getAlbumSingerData(position: Int): ItemSong
        fun getOnClickAlbumSinger(position: Int)
    }

    class SongAlbumSingerViewHolder(var binding: ItemAlbumSingerBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongAlbumSingerViewHolder {
        var binding =
            ItemAlbumSingerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongAlbumSingerViewHolder(binding)
    }

    override fun onBindViewHolder(singerViewHolderSong: SongAlbumSingerViewHolder, position: Int) {
        singerViewHolderSong.binding.albums = iAlbum.getAlbumSingerData(position)

        singerViewHolderSong.itemView.setOnClickListener {
            iAlbum.getOnClickAlbumSinger(position)
        }
    }

    override fun getItemCount() = iAlbum.getAlbumSingerCount()
}