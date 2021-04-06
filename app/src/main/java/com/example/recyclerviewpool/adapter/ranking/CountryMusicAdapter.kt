package com.example.recyclerviewpool.adapter.ranking

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemRankingMusicCountryBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong
import kotlinx.android.synthetic.main.item_ranking_music_country.view.*

class CountryMusicAdapter: RecyclerView.Adapter<CountryMusicAdapter.CountryVietNamViewHolder> {
    var iCountry :  ICountry
    constructor(iCountry: ICountry){
        this.iCountry = iCountry
    }
    interface ICountry{
        fun getCountryMusicCount(): Int
        fun getCountryMusicData(position: Int): ItemSong
        fun getCountryMusicOnClick(position: Int)
    }
    class CountryVietNamViewHolder(val binding:ItemRankingMusicCountryBinding ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryVietNamViewHolder {

        val binding= ItemRankingMusicCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryVietNamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryVietNamViewHolder, position: Int) {
       holder.binding.itemCountry = iCountry.getCountryMusicData(position)
        holder.itemView.setOnClickListener{
            iCountry.getCountryMusicOnClick(position)
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
        return iCountry.getCountryMusicCount()
    }

}