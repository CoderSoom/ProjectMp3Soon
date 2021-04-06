package com.example.recyclerviewpool.adapter.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemOutstandingsingerBinding
import com.example.recyclerviewpool.model.itemdata.ItemCategories

class OutStandingSingerAdapter : RecyclerView.Adapter<OutStandingSingerAdapter.OutStandingSingerViewHolder>{
    var iOutStandingSinger : IOutStandingSinger
    constructor(iOutStandingSinger: IOutStandingSinger){
        this.iOutStandingSinger = iOutStandingSinger
    }


    interface IOutStandingSinger{
        fun getOutStandingData(position: Int): ItemCategories
        fun getOutStandingCount ():Int
        fun getOutStandingOnClick(position: Int)
    }
    class OutStandingSingerViewHolder (val binding: ItemOutstandingsingerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutStandingSingerViewHolder {
        val binding = ItemOutstandingsingerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OutStandingSingerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OutStandingSingerViewHolder, position: Int) {
        holder.binding.outStandingSinger = iOutStandingSinger.getOutStandingData(position)
        holder.itemView.setOnClickListener{
            iOutStandingSinger.getOutStandingOnClick(position)
        }
    }

    override fun getItemCount(): Int {
        return iOutStandingSinger.getOutStandingCount()
    }

}