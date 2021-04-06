package com.example.recyclerviewpool.adapter.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemNewsongBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class NewSongAdapter : RecyclerView.Adapter<NewSongAdapter.NewSongViewHolder>{
     var iNewSong : INewSong
    constructor(iNewSong: INewSong){
        this.iNewSong = iNewSong
    }


    interface INewSong{
        fun getNewSongData(position: Int): ItemSong
        fun getNewSongCount ():Int
        fun getNewSongOnClick(position: Int)
    }
    class NewSongViewHolder (val binding: ItemNewsongBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewSongViewHolder {
        val binding = ItemNewsongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewSongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewSongViewHolder, position: Int) {
        holder.binding.newSong = iNewSong.getNewSongData(position)
        holder.itemView.setOnClickListener{
            iNewSong.getNewSongOnClick(position)
        }
    }

    override fun getItemCount(): Int {
        return iNewSong.getNewSongCount()
    }

}