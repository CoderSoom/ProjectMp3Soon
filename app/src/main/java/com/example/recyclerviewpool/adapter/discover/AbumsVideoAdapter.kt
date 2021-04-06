package com.example.recyclerviewpool.adapter.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemVideoBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class AlbumsVideoAdapter : RecyclerView.Adapter<AlbumsVideoAdapter.AlbumsVideoHolder> {
     var iAlbumVideo : IAlbumVideo


    constructor(iAlbumVideo: IAlbumVideo){
        this.iAlbumVideo = iAlbumVideo
    }
    interface IAlbumVideo{
        fun getVideoData(position: Int): ItemSong
        fun getOnClickVideo (position: Int)
        fun getCountVideo():Int
    }
    class AlbumsVideoHolder(val binding : ItemVideoBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsVideoHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumsVideoHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumsVideoHolder, position: Int) {
        holder.binding.subItemSong = iAlbumVideo.getVideoData(position)
        holder.itemView.setOnClickListener{
            iAlbumVideo.getOnClickVideo(position)
        }
    }

    override fun getItemCount(): Int {
        return iAlbumVideo.getCountVideo()
    }
}