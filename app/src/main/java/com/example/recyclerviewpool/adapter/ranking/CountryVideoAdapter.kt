package com.example.recyclerviewpool.adapter.ranking

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemRankingVideoCountryBinding

import com.example.recyclerviewpool.model.itemdata.ItemSong
import kotlinx.android.synthetic.main.item_ranking_music_country.view.*

class CountryVideoAdapter: RecyclerView.Adapter<CountryVideoAdapter.CountryVietNamViewHolder> {
    var iCountry :  ICountry
    constructor(iCountry: ICountry){
        this.iCountry = iCountry
    }
    interface ICountry{
        fun getVideoCountryCout(): Int
        fun getVideoCountryData(position: Int): ItemSong
        fun getVideoCountryOnClick(position: Int)
    }
    class CountryVietNamViewHolder(val binding: ItemRankingVideoCountryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryVietNamViewHolder {
        val binding= ItemRankingVideoCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryVietNamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryVietNamViewHolder, position: Int) {
       holder.binding.itemCountry = iCountry.getVideoCountryData(position)
        holder.itemView.setOnClickListener{
            iCountry.getVideoCountryOnClick(position)
        }
        if (position==0){
            holder.itemView.id_song.setTextColor(Color.parseColor("#882323"))
        }
        if (position==1){
            holder.itemView.id_song.setTextColor(Color.parseColor("#218825"))
        }
        if (position==2){
            holder.itemView.id_song.setTextColor(Color.parseColor("#20698A"))
        }
    }

    override fun getItemCount(): Int {
        return iCountry.getVideoCountryCout()
    }

}