package com.example.recyclerviewpool.view.fragment.discover.albumsong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.adapter.discover.song.SongAlbumsAdapter
import com.example.recyclerviewpool.databinding.FragmentAlbumsBinding
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.view.fragment.search.ManagerFragmentSearch
import com.example.recyclerviewpool.viewmodel.DiscoverModel
import com.example.recyclerviewpool.viewmodel.LoadDataUtils
import com.example.recyclerviewpool.viewmodel.SetDataSlidingPanel


class AddAlbumFragment : Fragment, SongAlbumsAdapter.IAlbum, View.OnClickListener {
    private lateinit var sharedViewModel: DiscoverModel
    private lateinit var playService: MainActivity

    private lateinit var slidingUpPanelLayout: MainActivity
    lateinit var managerDiscover: ManagerFragmentDiscover

    lateinit var managerSearch: ManagerFragmentSearch

    constructor(managerDiscover: ManagerFragmentDiscover) {
        this.managerDiscover = managerDiscover
    }

    constructor(managerSearch: ManagerFragmentSearch) {
        this.managerSearch = managerSearch
    }


    private var listFragment = mutableListOf<Fragment>()

    private lateinit var model: MainActivity
    private lateinit var binding: FragmentAlbumsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        playService = (activity as MainActivity)
        slidingUpPanelLayout = (activity as MainActivity)
        model = (activity as MainActivity)
        binding = FragmentAlbumsBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener(this)
        reg()
        setUpViewPager()

        updataDataAlbum()
        return binding.root

    }


    private fun setUpViewPager() {
        listFragment.add(DetailsAlbumFragment())
        listFragment.add(ViewPagerInfoAlbum())
        val viewPager = binding.viewPagerSong
        val adapterViewPager: FragmentPagerAdapter =
            object : FragmentPagerAdapter(fragmentManager!!) {
                override fun getItem(position: Int): Fragment {
                    return listFragment[position]
                }

                override fun getCount(): Int {
                    return listFragment.size
                }
            }
        viewPager.adapter = adapterViewPager
        binding.rcAlbums.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SongAlbumsAdapter(this@AddAlbumFragment)
        }
    }

    private fun updataDataAlbum() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(DiscoverModel::class.java)
        sharedViewModel.sharedInfoAlbum.observe(viewLifecycleOwner, Observer {
            LoadDataUtils.loadImgBitMapBlur(context, binding.bgAlbums, it.imgSong)

        })
    }


    private fun reg() {
        model.getDiscoverModel().songAlbums.observe(viewLifecycleOwner, Observer {
            binding.rcAlbums.adapter!!.notifyDataSetChanged()
        })
    }

    override fun getCount(): Int {
        if (model.getDiscoverModel().songAlbums.value == null) {
            return 0
        } else {
            return model.getDiscoverModel().songAlbums.value!!.size
        }
    }

    override fun getData(position: Int): ItemSong {
        return model.getDiscoverModel().songAlbums.value!![position]
    }

    override fun getOnClickSong(position: Int) {
        (activity as MainActivity).songAlbums = (activity as MainActivity).getDiscoverModel().songAlbums.value!!
        (activity as MainActivity).getPlaySevice()!!.currentPositionSong = position
        SetDataSlidingPanel.setDataSlidingPanel(
            activity as MainActivity,
            (activity as MainActivity).getDiscoverModel().songAlbums.value!!,
            position
        )
        /////SetLink Music Player
        model.getDiscoverModel().infoAlbum.observe(this, Observer
        {
            SetDataSlidingPanel.setDataMusic(
                activity as MainActivity,
                (activity as MainActivity).getDiscoverModel().songAlbums.value!!,
                it,
                position
            )
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
//              managerSearch.hasOnCreateViewBeenCalled()){
                managerDiscover.childFragmentManager.popBackStack()


            }
        }
    }
}
