package com.example.recyclerviewpool.adapter.discover

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemCategoriesCountryBinding
import com.example.recyclerviewpool.model.itemdata.ItemCategories

class CategoriesCountryAdapter : RecyclerView.Adapter<CategoriesCountryAdapter.DetailSearchViewHolder> {
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
        if (viewType % 2 == 0) {
            val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT)
            params.gravity = Gravity.RIGHT
            binding.holderView.layoutParams = params
        } else {
            val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT)
            params.gravity = Gravity.LEFT
            binding.holderView.layoutParams = params
        }
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