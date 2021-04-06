package com.example.recyclerviewpool.adapter.discover.song

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemAlbumBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong


class SongAlbumsAdapter : RecyclerView.Adapter<SongAlbumsAdapter.AlbumHolder> {
    var iAlbum: IAlbum

    constructor(iAlbum: IAlbum) {
        this.iAlbum = iAlbum
    }

    interface IAlbum {
        fun getCount(): Int
        fun getData(position: Int): ItemSong
        fun getOnClickSong(position: Int)
    }

    class AlbumHolder(var binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        var binding =
            ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        holder.binding.albums = iAlbum.getData(position)

        holder.itemView.setOnClickListener {
            iAlbum.getOnClickSong(position)


        }
    }

    override fun getItemCount() = iAlbum.getCount()
}