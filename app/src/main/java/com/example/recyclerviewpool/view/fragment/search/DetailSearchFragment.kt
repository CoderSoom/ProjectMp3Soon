package com.example.recyclerviewpool.view.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.databinding.FragmentDetailSearchBinding
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.adapter.search.TopicDetailSearchAdapter
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.viewmodel.DiscoverModel

class DetailSearchFragment : Fragment, TopicDetailSearchAdapter.ICategories, View.OnClickListener {
    private var managerSearch: ManagerFragmentSearch
    private lateinit var model: MainActivity
    private lateinit var binding: FragmentDetailSearchBinding

    constructor(managerFragmentSearch: ManagerFragmentSearch) {
        this.managerSearch = managerFragmentSearch
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = (activity as MainActivity)
        binding = FragmentDetailSearchBinding.inflate(inflater, container, false)
        //setUpCategoriesCountry
        binding.rcSugCountry.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TopicDetailSearchAdapter( this@DetailSearchFragment, model, managerSearch)

        }

        binding.search.setOnClickListener(this)
        reg()
        model.getDiscoverModel().categoriesCountry()
        return binding.root
    }

    private fun reg() {
        model.getDiscoverModel().categoriesCountry.observe(viewLifecycleOwner, Observer {
            binding.rcSugCountry.adapter!!.notifyDataSetChanged()

        })
    }


    override fun onClick(v: View?) {
        var fg = managerSearch.childFragmentManager!!.beginTransaction()
        fg.replace(R.id.frame_layout_search, SearchFragment(managerSearch))
        fg.addToBackStack(null)
        fg.commit()
    }

    override fun getCategoriesCountryCount(): Int {
        return if (model.getDiscoverModel().categoriesCountry.value == null) {
            0
        } else {
            model.getDiscoverModel().categoriesCountry.value!!.size
        }
    }

    override fun getCategoriesCountryData(position: Int) =
        model.getDiscoverModel().categoriesCountry.value!![position]



}