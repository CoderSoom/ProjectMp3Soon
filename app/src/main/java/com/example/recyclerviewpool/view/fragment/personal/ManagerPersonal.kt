package com.example.recyclerviewpool.view.fragment.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.databinding.ManagerPersonalBinding

class ManagerPersonal(val permission:PersonalFragment) : Fragment(){
    private lateinit var binding : ManagerPersonalBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ManagerPersonalBinding.inflate(inflater, container, false)
        addManagerPersonal()
        return binding.root

    }

    private fun addManagerPersonal() {
        var fg = childFragmentManager!!.beginTransaction()
        fg.add(R.id.frame_personal_Layout, permission)
        fg.commit()
    }

}