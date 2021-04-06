package com.example.recyclerviewpool.adapter.discover.video

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemSongStatusBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong


class ChilCategoriesStatusAdapter : RecyclerView.Adapter<ChilCategoriesStatusAdapter.ChilCategoriesStatusViewHolder>{
     var iChildCategoriesStatus : IChildCategoriesStatus
    constructor(iChildCategoriesStatus: IChildCategoriesStatus){
        this.iChildCategoriesStatus = iChildCategoriesStatus
    }


    interface IChildCategoriesStatus{
        fun getChildCategoriesStatusData(position: Int): ItemSong
        fun getChildCategoriesStatusCount ():Int
        fun getChildCategoriesStatusOnClick(position: Int)
    }
    class ChilCategoriesStatusViewHolder(val binding: ItemSongStatusBinding) : RecyclerView.ViewHolder(
        binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChilCategoriesStatusViewHolder {

        val binding = ItemSongStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChilCategoriesStatusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChilCategoriesStatusViewHolder, position: Int) {
        holder.binding.subItemSong = iChildCategoriesStatus.getChildCategoriesStatusData(position)
        holder.itemView.setOnClickListener{
            iChildCategoriesStatus.getChildCategoriesStatusOnClick(position)
        }
    }

    override fun getItemCount(): Int {
        return iChildCategoriesStatus.getChildCategoriesStatusCount()
    }

}