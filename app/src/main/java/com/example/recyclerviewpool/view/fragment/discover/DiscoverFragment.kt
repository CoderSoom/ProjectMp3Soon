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

        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY: Int = binding.nestedScrollView.scrollY
            if (scrollY == 0) {
//                (activity as MainActivity).pullFresh().isRefreshing = false
//                (activity as MainActivity).pullFresh().isEnabled = true
            } else {
//                (activity as MainActivity).pullFresh().isEnabled =
//                    false
            }
        }
        //setUpAlbumsNews
        binding.rcAlbums.adapter =
            TopicAlbumSongAdapter(sharedViewModel, model, this, managerDiscover)
        binding.rcAlbums.layoutManager = PreCachingLayoutManager(context)
        binding.rcAlbums.hasFixedSize()
        binding.rcAlbums.setItemViewCacheSize(10)


        ///setUpAlbumsVideo
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

        ///set Up newSong
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
            adapter = TopicSugAdapter(model, this@DiscoverFragment, managerDiscover)

        }


        reg()
        //Call API
        model.getModel().albumsSong()
        model.getModel().albumsVideo()
        model.getModel().getTopResult(context!!)
        model.getModel().categoriesStatus()
        model.getModel().categoriesCountry()
        model.getModel().newSong()
        model.getModel().outstandingSinger()
        model.getModel().sugVideoMusic("https://vi.chiasenhac.vn/mp3/quan-ap/bong-hoa-dep-nhat-tsvmq0csq8env4.html")
        return binding.root

    }


    private fun reg() {
        ///TopResult
        model.getModel().topResult.observe(viewLifecycleOwner, Observer {
            binding.rcTopResult.adapter!!.notifyDataSetChanged()


        })
        ///outstanding
        model.getModel().outstandingSinger.observe(viewLifecycleOwner, Observer {
            binding.rcOutStandingSinger.adapter!!.notifyDataSetChanged()

        })


        //Albums SongNew
        model.getModel().albumsSong.observe(viewLifecycleOwner, Observer {
            binding.rcAlbums.adapter!!.notifyDataSetChanged()

        })
        //Albums VideoNew
        model.getModel().albumsVideo.observe(viewLifecycleOwner, Observer {
            binding.rcAlbumsVideo.adapter!!.notifyDataSetChanged()

        })

        ///newSong
        model.getModel().newSong.observe(viewLifecycleOwner, Observer {
            binding.rcNewSong.adapter!!.notifyDataSetChanged()

        })
        //categories
        model.getModel().categoriesStatus.observe(viewLifecycleOwner, Observer {
            binding.rcCategoriesStatus.adapter!!.notifyDataSetChanged()

        })
        model.getModel().categoriesCountry.observe(viewLifecycleOwner, Observer {
            binding.rcCategoriesCountry.adapter!!.notifyDataSetChanged()

        })

        //sug
        model.getModel().sugAlbums.observe(viewLifecycleOwner, Observer {
            binding.rcSug.adapter!!.notifyDataSetChanged()
        })


    }

    override fun getTopResultCount(): Int {
        return if (model.getModel().topResult.value == null) {
            0
        } else {
            model.getModel().topResult.value!!.size
        }
    }

    override fun getTopResultData(position: Int): ItemMusicList<ItemSong> {
        return model.getModel().topResult.value!![position]
    }


    ////////////////////////////////


    override fun getSugDataCout(): Int {
        return if (model.getModel().sugAlbums.value == null) {
            0
        } else {
            model.getModel().sugAlbums.value!!.size
        }
    }

    override fun getSugData(position: Int): ItemMusicList<ItemSong> {
        return model.getModel().sugAlbums.value!![position]
    }

    override fun getNewSongCount(): Int {
        return if (model.getModel().newSong.value == null) {
            0
        } else {
            model.getModel().newSong.value!!.size
        }
    }

    override fun getNewSongData(position: Int): ItemMusicList<ItemSong> {
        return model.getModel().newSong.value!![position]
    }


    //////////////////////////////


    override fun getCategoriesCountryCount(): Int {
        return if (model.getModel().categoriesCountry.value == null) {
            0
        } else {
            model.getModel().categoriesCountry.value!!.size
        }
    }

    override fun getCategoriesCountryData(position: Int): ItemMusicList<ItemCategories> {
        return model.getModel().categoriesCountry.value!![position]
    }

    override fun getOutStandingSingerCount(): Int {
        return if (model.getModel().outstandingSinger.value == null) {
            0
        } else {
            model.getModel().outstandingSinger.value!!.size
        }
    }


    ///////////////////////////

    override fun getOutStandingSingerData(position: Int): ItemMusicList<ItemCategories> {
        return model.getModel().outstandingSinger.value!![position]
    }

    override fun getCategoriesStatusCount(): Int {
        return if (model.getModel().categoriesStatus.value == null || model.getModel().categoriesStatus.value!!.size==0) {
            0
        } else {
            model.getModel().categoriesStatus.value!!.size
        }
    }

    override fun getCategoriesStatusData(position: Int): ItemMusicList<ItemCategories> {
        return model.getModel().categoriesStatus.value!![position]
    }

    override fun getAlbumVideoCount(): Int {
        return if (model.getModel().albumsVideo.value == null) {
            0
        } else {
            model.getModel().albumsVideo.value!!.size
        }
    }

    override fun getAlbumVideoData(position: Int): ItemMusicList<ItemSong> {
        return model.getModel().albumsVideo.value!![position]
    }

    override fun getNewAlbumCount(): Int {
        return if (model.getModel().albumsSong.value==null){
            0
        }else{
            model.getModel().albumsSong.value!!.size
        }
    }

    override fun getNewAlbumData(position: Int): ItemMusicList<ItemSong> {
      return model.getModel().albumsSong.value!![position]
    }


}

