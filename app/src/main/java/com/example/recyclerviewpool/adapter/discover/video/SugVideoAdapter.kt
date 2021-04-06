package com.example.recyclerviewpool.adapter.discover.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemRelateVideoBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class SugVideoAdapter : RecyclerView.Adapter<SugVideoAdapter.SugVideoViewHolder>{
     var iSugVideo: ISugVideo
    constructor(iSugVideo: ISugVideo){
        this.iSugVideo  = iSugVideo
    }
    interface ISugVideo{
        fun getSugVideoData (position: Int): ItemSong
        fun getOnClickSugVideo(position: Int)
        fun getSugVideoCount():Int
    }
    class SugVideoViewHolder (val binding: ItemRelateVideoBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SugVideoViewHolder {
        val binding = ItemRelateVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SugVideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SugVideoViewHolder, position: Int) {
        holder.binding.dataVideo = iSugVideo.getSugVideoData(position)
        holder.itemView.setOnClickListener{
            iSugVideo.getOnClickSugVideo(position)
        }
    }

    override fun getItemCount(): Int {
        return iSugVideo.getSugVideoCount()
    }

}