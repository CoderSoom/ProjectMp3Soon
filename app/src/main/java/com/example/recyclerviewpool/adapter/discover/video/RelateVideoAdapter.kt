package com.example.recyclerviewpool.adapter.discover.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemSugVideoBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class RelateVideoAdapter : RecyclerView.Adapter<RelateVideoAdapter.RelateVideoVieHolder>{
    private var iRelateVideo: IRelateVideo
    constructor(iRelateVideo: IRelateVideo){
        this.iRelateVideo  = iRelateVideo
    }
    interface IRelateVideo{
        fun getRelateVideoData (position: Int): ItemSong
        fun getOnClickRelateVideo(position: Int)
        fun getRelateVideoCount():Int
    }
    class RelateVideoVieHolder (val binding: ItemSugVideoBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelateVideoVieHolder {
        val binding = ItemSugVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RelateVideoVieHolder(binding)
    }

    override fun onBindViewHolder(holder: RelateVideoVieHolder, position: Int) {
        holder.binding.dataVideo = iRelateVideo.getRelateVideoData(position)
        holder.itemView.setOnClickListener{
            iRelateVideo.getOnClickRelateVideo(position)
        }
    }

    override fun getItemCount(): Int {
        return iRelateVideo.getRelateVideoCount()
    }

}