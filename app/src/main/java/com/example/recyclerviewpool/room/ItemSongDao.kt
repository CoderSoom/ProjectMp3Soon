package com.example.recyclerviewpool.room

import androidx.room.*
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.model.itemdata.ItemSong


@Dao
interface ItemSongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetAll(articles: MutableList<ItemMusicList<ItemSong>>)

    @Query("SELECT * FROM item_song ")
    fun getAllData(): MutableList<ItemMusicList<ItemSong>>




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemSong(itemsSong: ItemSong)

    @Query("DELETE FROM item_song WHERE id = :ids")
    fun delete(ids: MutableList<String>)


    @Query("SELECT * FROM item_song WHERE keySearch = :keySearchs")
    fun findByKeySearch(keySearchs: String):MutableList<ItemMusicList<ItemSong>>
}