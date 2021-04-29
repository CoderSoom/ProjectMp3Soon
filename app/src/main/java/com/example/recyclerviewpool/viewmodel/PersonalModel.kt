package com.example.recyclerviewpool.viewmodel

import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerviewpool.model.itemdata.ItemMusicOffline


class PersonalModel : ViewModel {
    val songOffline: MutableLiveData<MutableList<ItemMusicOffline>> = MutableLiveData()


    constructor()

    fun loadAllMusicOffline(context: Context) {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = context.contentResolver.query(uri,
            null,
            null,
            null,
            null)
        cursor!!.moveToFirst()
        val songs = mutableListOf<ItemMusicOffline>()
        val indexData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
        val indexName = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)
        val indexDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
        val indexAlbumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
        var indexAlbum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
        var indexArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
        var indexTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
        var indexYear = cursor.getColumnIndex(MediaStore.Audio.Media.YEAR)
        while (!cursor.isAfterLast) {
            val pathSong = cursor.getString(indexData)
            val nameMp3 = cursor.getString(indexName)
            val timeMp3 = cursor.getInt(indexDuration)
            val albumId = cursor.getString(indexAlbumId)
            val albumSong = cursor.getString(indexAlbum)
            val nameSinger = cursor.getString(indexArtist)
            val nameSong = cursor.getString(indexTitle)
            val yearSong = cursor.getString(indexYear)
            val albumArtUri = "content://media/external/audio/albumart/$albumId"
            var durationSong = setCorrectDuration(timeMp3)


            songs.add(
                ItemMusicOffline(nameSong,
                    nameSinger,
                    albumArtUri,
                    durationSong,
                    pathSong,
                    nameMp3,
                    albumSong,
                    yearSong, albumId)
            )
            cursor.moveToNext()
        }

        cursor.close()
        songOffline.value = songs
    }

    private fun setCorrectDuration(songs_duration: Int): String {
        var a :String
        var seconds = songs_duration / 1000
        val minutes = seconds / 60
        seconds %= 60
        if (minutes<10){
            a = "$minutes:0$seconds"
        }else{
            a = "$minutes:$seconds"
        }
        return a
    }


}