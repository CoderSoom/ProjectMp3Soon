package com.example.recyclerviewpool.adapter.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemSugBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class SugAdapter : RecyclerView.Adapter<SugAdapter.SugVideoViewHolder>{
     var iSug: ISug
    constructor(iSugVideo: ISug){
        this.iSug  = iSugVideo
    }
    interface ISug{
        fun getSugData (position: Int): ItemSong
        fun getOnClickSug(position: Int)
        fun getSugCout():Int
    }
    class SugVideoViewHolder (val binding: ItemSugBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SugVideoViewHolder {
        val binding = ItemSugBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SugVideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SugVideoViewHolder, position: Int) {
        holder.binding.itemSug = iSug.getSugData(position)
        holder.itemView.setOnClickListener{
            iSug.getOnClickSug(position)
        }
    }

    override fun getItemCount(): Int {
        return iSug.getSugCout()
    }

}