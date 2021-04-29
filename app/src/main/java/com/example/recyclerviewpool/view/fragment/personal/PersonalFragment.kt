package com.example.recyclerviewpool.view.fragment.personal

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewpool.adapter.personal.SongOfflineAdapter
import com.example.recyclerviewpool.checkconnect.CheckingPermission
import com.example.recyclerviewpool.databinding.PersonalFragmentBinding
import com.example.recyclerviewpool.model.download.Downloader
import com.example.recyclerviewpool.model.itemdata.ItemMusicOffline
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.viewmodel.PersonalModel
import com.example.recyclerviewpool.viewmodel.SetDataSlidingPanel
import kotlinx.android.synthetic.main.sliding_up_panel.view.*


class PersonalFragment : Fragment(), SongOfflineAdapter.ISongOffline {

    var mContext: Context? = null
    var mActivity: Activity? = null
    private lateinit var model: PersonalModel
    private lateinit var modelActivity: PersonalModel
    private lateinit var binding: PersonalFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = PersonalModel()
        binding = PersonalFragmentBinding.inflate(inflater, container, false)
        showMusicOffline()
        reg()
        binding.rcSongOff.layoutManager = LinearLayoutManager(context)
        binding.rcSongOff.adapter = SongOfflineAdapter(this)

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    fun showMusicOffline() {
        if (CheckingPermission.showPermission(activity!!,
                10,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
        ) {
            model.loadAllMusicOffline(context!!)


        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var ok = true
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                ok = false
                break
            }
        }
        if (requestCode == 10) {
            if (ok) {
                showMusicOffline()
            } else {
                showDialog()


            }
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(activity!!)
            .setMessage("Bạn phải cho phép truy cập bộ nhớ để sử dụng đầy đủ chức năng!")
            .setTitle("BỎ QUA")
            .setPositiveButton("OK") { _: DialogInterface, type: Int ->
                run {
                    openSettings()
                }
            }
            .setCancelable(false)
            .show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context!!.packageName, null)
        intent.data = uri
        startActivityForResult(intent, 20)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 20) {
            showMusicOffline()
        }

    }


    private fun reg() {
        model.songOffline.observe(viewLifecycleOwner, Observer {
            binding.rcSongOff.adapter!!.notifyDataSetChanged()
        })
    }

    override fun getCount(): Int {
        if (model.songOffline.value == null) {
            return 0
        }
        return model.songOffline.value!!.size
    }

    override fun getData(position: Int): ItemMusicOffline {
        return model.songOffline.value!![position]
    }

    override fun onClickItem(position: Int) {
        (activity as MainActivity).getPlaySevice()!!.currentPositionSong = position
        SetDataSlidingPanel.setDataSlidingOffline((activity as MainActivity), model.songOffline.value!!, position)

    }

    @SuppressLint("ResourceType")
    override fun onClickOptions(
        holder: SongOfflineAdapter.SongOfflineViewHolder,
        adapterPosition: Int
    ) {
        holder.binding.btnOptions.setOnClickListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}