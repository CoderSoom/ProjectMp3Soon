package com.example.recyclerviewpool.adapter.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpool.databinding.ItemTopicAlbumSongBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.viewmodel.DiscoverModel

class TopicAlbumVideoAdapter : RecyclerView.Adapter<TopicAlbumVideoAdapter.ItemCategoriesHolder> {
    private var iCategories: ICategories
    private var managerDiscover: ManagerFragmentDiscover
    var model: MainActivity
    var sharedViewModel: DiscoverModel

    constructor(
        shareViewModel: DiscoverModel,
        model: MainActivity,
        iCategories: ICategories,
        managerDiscover: ManagerFragmentDiscover
    ) {
        this.sharedViewModel = shareViewModel
        this.model = model
        this.iCategories = iCategories
        this.managerDiscover = managerDiscover

    }

    interface ICategories {
        fun getAlbumVideoCount(): Int
        fun getAlbumVideoData(position: Int): ItemMusicList<ItemSong>

    }

    class ItemCategoriesHolder(val binding: ItemTopicAlbumSongBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoriesHolder {

        val holder = ItemCategoriesHolder(
            ItemTopicAlbumSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        holder.binding.rcCategories.setRecycledViewPool(RecyclerView.RecycledViewPool())
        holder.binding.rcCategories.layoutManager =
            GridLayoutManager(parent.context, 2, GridLayoutManager.HORIZONTAL, false)
        return holder
    }

    override fun onBindViewHolder(holder: ItemCategoriesHolder, parenPosition: Int) {
        val data = iCategories.getAlbumVideoData(parenPosition)


        val iCategories = object : AlbumsVideoAdapter.IAlbumVideo {

            override fun getVideoData(chilPosition: Int) = data.values[chilPosition]

            override fun getOnClickVideo(position: Int) {
               ///////////////
                model.getDiscoverModel().getInfo(data.values[position].linkSong)
                model.getDiscoverModel()
                    .getRelateVideo(data.values[position].linkSong)
                model.getDiscoverModel().sugVideoMusic(data.values[position].linkSong)
                managerDiscover.openVideo()

            }

            override fun getCountVideo()=10

        }

        if (holder.binding.rcCategories.adapter == null) {
            holder.binding.rcCategories.adapter = AlbumsVideoAdapter(iCategories)
            holder.binding.titleCategories.text = data.nameCategories

        } else {

            (holder.binding.rcCategories.adapter as AlbumsVideoAdapter).iAlbumVideo = iCategories

            holder.binding.rcCategories.adapter!!.notifyDataSetChanged()


        }

    }

    override fun getItemCount() = iCategories.getAlbumVideoCount()
    override fun getItemId(position: Int): Long {
        return if (position == 0) {
            0
        } else {
            model.getDiscoverModel().albumsVideo.value!![position].hashCode().toLong()
        }

    }
}