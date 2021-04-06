package com.example.recyclerviewpool.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemMostSearchedBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class MostSearchedAdapter : RecyclerView.Adapter<MostSearchedAdapter.MostSearchedViewHolder> {
    var iMostSearched: InterMostSearched

    constructor(interMostSearched: InterMostSearched) {
        this.iMostSearched = interMostSearched
    }

    interface InterMostSearched {
        fun getMostSearchedCount(): Int
        fun getMostSearchedData(position: Int): ItemSong
        fun getMostSearchedOnClickItem(position: Int)
    }

    class MostSearchedViewHolder(val binding: ItemMostSearchedBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostSearchedViewHolder {
        var binding = ItemMostSearchedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MostSearchedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MostSearchedViewHolder, position: Int) {
        holder.binding.mostSearched = iMostSearched.getMostSearchedData(position)
    }

    override fun getItemCount() = iMostSearched.getMostSearchedCount()
}