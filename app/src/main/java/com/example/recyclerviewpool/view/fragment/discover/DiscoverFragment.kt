package com.example.recyclerviewpool.view.fragment.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpool.databinding.FragmentDiscoverBinding
import com.example.recyclerviewpool.model.PreCachingLayoutManager
import com.example.recyclerviewpool.model.itemdata.ItemCategories
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.adapter.discover.*
import com.example.recyclerviewpool.viewmodel.DiscoverModel


class DiscoverFragment : Fragment, TopicAlbumSongAdapter.ICategories,
    TopicOutStandingSingerAdapter.ICategories, TopicAlbumVideoAdapter.ICategories,
    TopicTopResultAdapter.ICategories,
    TopicCategoriesStatusAdapter.ICategories, TopicCategoriesCountryAdapter.ICategories,
    TopicNewSongAdapter.ICategories, TopicSugAdapter.ICategories {
    private lateinit var sharedViewModel: DiscoverModel
    private var managerDiscover: ManagerFragmentDiscover
    private lateinit var model: MainActivity
    private lateinit var binding: FragmentDiscoverBinding
    private lateinit var slidingUpPanelLayout: MainActivity

    constructor(managerDiscover: ManagerFragmentDiscover) {
        this.managerDiscover = managerDiscover
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedViewModel = ViewModelProvider(requireActivity()).get(DiscoverModel::class.java)
        model = (activity as MainActivity)
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        slidingUpPanelLayout = (activity as MainActivity)

        //setUPTopResult

        binding.rcTopResult.apply {
            layoutManager = PreCachingLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
            setItemViewCacheSize(20)
            adapter = TopicTopResultAdapter(sharedViewModel,
                model,
                this@DiscoverFragment,
                managerDiscover)

        }
        //setUpAlbumsNews

        binding.rcAlbums.apply {
            layoutManager = PreCachingLayoutManager(context)
            hasFixedSize()
            setItemViewCacheSize(10)
            adapter = TopicAlbumSongAdapter(sharedViewModel,
                model,
                this@DiscoverFragment,
                managerDiscover)

        }

//        /setUpAlbumsVideo
        binding.rcAlbumsVideo.apply {
            layoutManager = PreCachingLayoutManager(context)
            hasFixedSize()
            setItemViewCacheSize(10)
            adapter = TopicAlbumVideoAdapter(sharedViewModel,
                model,
                this@DiscoverFragment,
                managerDiscover)

        }

        //setUpCategoriesStatus
        binding.rcCategoriesStatus.apply {
            layoutManager = PreCachingLayoutManager(context)
            hasFixedSize()
            setItemViewCacheSize(10)
            adapter = TopicCategoriesStatusAdapter(sharedViewModel,
                model,
                this@DiscoverFragment,
                managerDiscover)

        }
        //setUpCountry
        binding.rcCategoriesCountry.apply {
            layoutManager = PreCachingLayoutManager(context)
            hasFixedSize()
            setItemViewCacheSize(10)
            adapter = TopicCategoriesCountryAdapter(sharedViewModel,
                model,
                this@DiscoverFragment,
                managerDiscover)

        }

        //set Up newSong
        binding.rcNewSong.apply {
            layoutManager = PreCachingLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
            setItemViewCacheSize(10)
            adapter =
                TopicNewSongAdapter(sharedViewModel,
                    model,
                    this@DiscoverFragment,
                    managerDiscover, slidingUpPanelLayout, viewLifecycleOwner)

        }

        //setUp OutStandingSinger
        binding.rcOutStandingSinger.apply {
            layoutManager = PreCachingLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
            setItemViewCacheSize(10)
            adapter = TopicOutStandingSingerAdapter(sharedViewModel,
                model,
                this@DiscoverFragment,
                managerDiscover)

        }

        //setUpSug
        binding.rcSug.apply {
            layoutManager = PreCachingLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
            setItemViewCacheSize(5)
            adapter = TopicSugAdapter(sharedViewModel, model, this@DiscoverFragment, managerDiscover, viewLifecycleOwner)

        }


        reg()
        //Call API
        model.getDiscoverModel().albumsSong()
        model.getDiscoverModel().albumsVideo()
        model.getDiscoverModel().getTopResult()
        model.getDiscoverModel().categoriesStatus()
        model.getDiscoverModel().categoriesCountry()
        model.getDiscoverModel().newSong()
        model.getDiscoverModel().outstandingSinger()
        model.getDiscoverModel().sugVideoMusic("https://vi.chiasenhac.vn/mp3/quan-ap/bong-hoa-dep-nhat-tsvmq0csq8env4.html")
        return binding.root

    }


    private fun reg() {
        ///TopResult
        model.getDiscoverModel().topResult.observe(viewLifecycleOwner, Observer {
            binding.rcTopResult.adapter!!.notifyDataSetChanged()


        })
        ///outstanding
        model.getDiscoverModel().outstandingSinger.observe(viewLifecycleOwner, Observer {
            binding.rcOutStandingSinger.adapter!!.notifyDataSetChanged()

        })


        //Albums SongNew
        model.getDiscoverModel().albumsSong.observe(viewLifecycleOwner, Observer {
            binding.rcAlbums.adapter!!.notifyDataSetChanged()

        })
        //Albums VideoNew
        model.getDiscoverModel().albumsVideo.observe(viewLifecycleOwner, Observer {
            binding.rcAlbumsVideo.adapter!!.notifyDataSetChanged()

        })

        ///newSong
        model.getDiscoverModel().newSong.observe(viewLifecycleOwner, Observer {
            binding.rcNewSong.adapter!!.notifyDataSetChanged()

        })
        //categories
        model.getDiscoverModel().categoriesStatus.observe(viewLifecycleOwner, Observer {
            binding.rcCategoriesStatus.adapter!!.notifyDataSetChanged()

        })
        model.getDiscoverModel().categoriesCountry.observe(viewLifecycleOwner, Observer {
            binding.rcCategoriesCountry.adapter!!.notifyDataSetChanged()

        })

        //sug
        model.getDiscoverModel().sugAlbums.observe(viewLifecycleOwner, Observer {
            binding.rcSug.adapter!!.notifyDataSetChanged()
        })


    }

    override fun getTopResultCount(): Int {
        return if (model.getDiscoverModel().topResult.value == null) {
            0
        } else {
            model.getDiscoverModel().topResult.value!!.size
        }
    }

    override fun getTopResultData(position: Int): ItemMusicList<ItemSong> {
        return model.getDiscoverModel().topResult.value!![position]
    }


    ////////////////////////////////


    override fun getSugDataCout(): Int {
        return if (model.getDiscoverModel().sugAlbums.value == null) {
            0
        } else {
            model.getDiscoverModel().sugAlbums.value!!.size
        }
    }

    override fun getSugData(position: Int): ItemMusicList<ItemSong> {
        return model.getDiscoverModel().sugAlbums.value!![position]
    }

    override fun getNewSongCount(): Int {
        return if (model.getDiscoverModel().newSong.value == null) {
            0
        } else {
            model.getDiscoverModel().newSong.value!!.size
        }
    }

    override fun getNewSongData(position: Int): ItemMusicList<ItemSong> {
        return model.getDiscoverModel().newSong.value!![position]
    }


    //////////////////////////////


    override fun getCategoriesCountryCount(): Int {
        return if (model.getDiscoverModel().categoriesCountry.value == null) {
            0
        } else {
            model.getDiscoverModel().categoriesCountry.value!!.size
        }
    }

    override fun getCategoriesCountryData(position: Int): ItemMusicList<ItemCategories> {
        return model.getDiscoverModel().categoriesCountry.value!![position]
    }

    override fun getOutStandingSingerCount(): Int {
        return if (model.getDiscoverModel().outstandingSinger.value == null) {
            0
        } else {
            model.getDiscoverModel().outstandingSinger.value!!.size
        }
    }


    ///////////////////////////

    override fun getOutStandingSingerData(position: Int): ItemMusicList<ItemCategories> {
        return model.getDiscoverModel().outstandingSinger.value!![position]
    }

    override fun getCategoriesStatusCount(): Int {
        return if (model.getDiscoverModel().categoriesStatus.value == null || model.getDiscoverModel().categoriesStatus.value!!.size==0) {
            0
        } else {
            model.getDiscoverModel().categoriesStatus.value!!.size
        }
    }

    override fun getCategoriesStatusData(position: Int): ItemMusicList<ItemCategories> {
        return model.getDiscoverModel().categoriesStatus.value!![position]
    }

    override fun getAlbumVideoCount(): Int {
        return if (model.getDiscoverModel().albumsVideo.value == null) {
            0
        } else {
            model.getDiscoverModel().albumsVideo.value!!.size
        }
    }

    override fun getAlbumVideoData(position: Int): ItemMusicList<ItemSong> {
        return model.getDiscoverModel().albumsVideo.value!![position]
    }

    override fun getNewAlbumCount(): Int {
        return if (model.getDiscoverModel().albumsSong.value==null){
            0
        }else{
            model.getDiscoverModel().albumsSong.value!!.size
        }
    }

    override fun getNewAlbumData(position: Int): ItemMusicList<ItemSong> {
      return model.getDiscoverModel().albumsSong.value!![position]
    }


}

