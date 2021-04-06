package com.example.recyclerviewpool.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemCategoriesCountryBinding
import com.example.recyclerviewpool.model.itemdata.ItemCategories

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailSearchViewHolder> {
     var iCategoriesCountry: InterCategoriesCountry

    constructor(interCategoriesCountry: InterCategoriesCountry) {
        this.iCategoriesCountry = interCategoriesCountry
    }


    interface InterCategoriesCountry {
        fun getCategoriesCountryData(position: Int): ItemCategories
        fun getCategoriesCountryCount(): Int
        fun getCategoriesCountryOnClick(position: Int)

    }

    class DetailSearchViewHolder(val binding: ItemCategoriesCountryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailSearchViewHolder {
        val binding = ItemCategoriesCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailSearchViewHolder, position: Int) {
        holder.binding.detailSearch = iCategoriesCountry.getCategoriesCountryData(position)
        holder.itemView.setOnClickListener {
            iCategoriesCountry.getCategoriesCountryOnClick(position)
        }
    }

    override fun getItemCount(): Int {
        return iCategoriesCountry.getCategoriesCountryCount()
    }

}