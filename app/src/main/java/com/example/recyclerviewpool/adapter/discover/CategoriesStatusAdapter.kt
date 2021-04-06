package com.example.recyclerviewpool.adapter.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemCategoriesStatusBinding
import com.example.recyclerviewpool.model.itemdata.ItemCategories


class CategoriesStatusAdapter : RecyclerView.Adapter<CategoriesStatusAdapter.CategoriesCountryViewHolder> {
     var iCategoriesStatus: ICategoriesStatus

    constructor(iCategoriesStatus: ICategoriesStatus) {
        this.iCategoriesStatus = iCategoriesStatus
    }


    interface ICategoriesStatus {
        fun getCategoriesStatusData(position: Int): ItemCategories
        fun getCategoriesStatusCount(): Int
        fun getCategoriesStatusOnClick(position: Int)
    }

    class CategoriesCountryViewHolder(val binding: ItemCategoriesStatusBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesCountryViewHolder {
        val binding = ItemCategoriesStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesCountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesCountryViewHolder, position: Int) {
        holder.binding.categoriesContry = iCategoriesStatus.getCategoriesStatusData(position)
        holder.itemView.setOnClickListener {
            iCategoriesStatus.getCategoriesStatusOnClick(position)
        }
    }

    override fun getItemCount(): Int {
        return iCategoriesStatus.getCategoriesStatusCount()
    }

}