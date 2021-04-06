package com.example.recyclerviewpool.model.itemdata

class ItemMusicList<T>{
    var nameCategories = ""
    var values = mutableListOf<T>()
    constructor(nameCategories:String, values:MutableList<T>){
        this.nameCategories = nameCategories
        this.values = values
    }
    constructor()
}