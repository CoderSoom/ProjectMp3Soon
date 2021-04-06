package com.example.recyclerviewpool.adapter.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemResultBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong

class TopResultAdapter : RecyclerView.Adapter<TopResultAdapter.TopResultViewHolder>{
    var iTopResult : ITopResult
    constructor(iTopResult: ITopResult){
        this.iTopResult = iTopResult
    }


    interface ITopResult{
        fun getTopResultData(position: Int): ItemSong
        fun getTopResultCount ():Int
        fun getTopResultOnClick(position: Int)
    }
    class TopResultViewHolder (val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopResultViewHolder {
       val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopResultViewHolder, position: Int) {
       holder.binding.topResult = iTopResult.getTopResultData(position)
        holder.itemView.setOnClickListener{
            iTopResult.getTopResultOnClick(position)
        }
    }

    override fun getItemCount(): Int {
       return iTopResult.getTopResultCount()
    }

}