package com.example.recyclerviewpool.adapter.personal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemSongOfflineBinding
import com.example.recyclerviewpool.model.itemdata.ItemMusicOffline


class SongOfflineAdapter : RecyclerView.Adapter<SongOfflineAdapter.SongOfflineViewHolder> {
    private val inter: ISongOffline

    constructor(inter: ISongOffline) {
        this.inter = inter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongOfflineViewHolder {
        return SongOfflineViewHolder(
            ItemSongOfflineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount()=inter.getCount()

    override fun onBindViewHolder(holder: SongOfflineViewHolder, position: Int) {
        holder.binding.itemSongOffline = inter.getData(position)
        holder.binding.ll.setOnClickListener{
            inter.onClickItem(holder.adapterPosition)
        }
        holder.binding.btnOptions.setOnClickListener{
            inter.onClickOptions(holder, holder.adapterPosition)
        }

    }

    interface ISongOffline {
        fun getCount(): Int
        fun getData(position: Int): ItemMusicOffline
        fun onClickItem(position: Int)
        fun onClickOptions(holder: SongOfflineViewHolder, adapterPosition: Int)
    }

    class SongOfflineViewHolder(val binding: ItemSongOfflineBinding) :
        RecyclerView.ViewHolder(binding.root){

    }
}