package com.example.recyclerviewpool.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.databinding.SplashScreenBinding
import com.example.recyclerviewpool.view.fragment.discover.DiscoverFragment
import com.example.recyclerviewpool.view.fragment.discover.ManagerFragmentDiscover
import com.example.recyclerviewpool.view.fragment.ranking.ManagerRanking
import com.example.recyclerviewpool.view.fragment.search.ManagerFragmentSearch
import java.util.*
import kotlin.collections.ArrayList


 class SplashScreen : AppCompatActivity() {

    private lateinit var binding: SplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen)
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)

    }



}
