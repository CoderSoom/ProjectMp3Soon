package com.example.recyclerviewpool.view.fragment.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpool.databinding.FragmentAlbumCategoriesStatusBinding
import com.example.recyclerviewpool.model.itemdata.ItemMusicList
import com.example.recyclerviewpool.model.itemdata.ItemSong
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.adapter.discover.TopicChildCategoriesAdapter
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.viewmodel.DiscoverModel

class AlbumCategoriesStatusFragment : Fragment , TopicChildCategoriesAdapter.ICategories{
    var managerDiscover: ManagerFragmentDiscover
    lateinit var sharedViewModel : DiscoverModel

    constructor(managerDiscover: ManagerFragmentDiscover) {
        this.managerDiscover = managerDiscover
    }


    private var listFragment = mutableListOf<Fragment>()

    private lateinit var model: MainActivity
    private lateinit var binding: FragmentAlbumCategoriesStatusBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity()).get(DiscoverModel::class.java)
        model = (activity as MainActivity)
        binding = FragmentAlbumCategoriesStatusBinding.inflate(inflater, container, false)
        reg()
        setUpModel()


        return binding.root
    }

    private fun setUpModel() {
        binding.rcAlbums.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TopicChildCategoriesAdapter(sharedViewModel, model, this@AlbumCategoriesStatusFragment, managerDiscover, viewLifecycleOwner)
        }
    }

    private fun reg() {

        model.getDiscoverModel().childCategoriesStatus.observe(viewLifecycleOwner, Observer {
            binding.rcAlbums.adapter!!.notifyDataSetChanged()
        })
    }

    override fun getChildCategoriesStatusCount(): Int {
        if (model.getDiscoverModel().childCategoriesStatus.value ==null){
            return 0

        }else{
            return 1
        }
    }

    override fun getChildCategoriesStatusData(position: Int): ItemMusicList<ItemSong> {
       return model.getDiscoverModel().childCategoriesStatus.value!![position]
    }
}