package com.example.recyclerviewpool.checkconnect

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.recyclerviewpool.R
import com.example.recyclerviewpool.view.MainActivity
import com.tapadoo.alerter.Alerter


class NetworkChangeReceiver : BroadcastReceiver() {


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onReceive(context: Context, intent: Intent) {
        val alerterOn = context.resources.getDrawable(R.drawable.alerter_bg_on)
        val alerterOff = context.resources.getDrawable(R.drawable.alerter_bg_off)

        val status = NetworkUtil.getConnectivityStatusString(context)
        if (context.getString(R.string.uses_permission) == intent.action) {

            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                Alerter.create(context as MainActivity)
                    .setTitle(context.getString(R.string.no_connect))
                    .setText(context.getString(R.string.please_check_connect))
                    .setOnClickListener(View.OnClickListener {
                        var intent = Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                        startActivity(context, intent, null)
                    })
                    .setIcon(ContextCompat.getDrawable(context,
                        R.drawable.access_point_network_off)!!)
                    .setBackgroundDrawable(alerterOff)
                    .show()

            } else {
                    Alerter.create(context as MainActivity)
                        .setTitle(context.getString(R.string.connect))
                        .setIcon(ContextCompat.getDrawable(context,
                            R.drawable.access_point_network)!!)
                        .setBackgroundDrawable(alerterOn)
                        .show()
                }



        }
    }
}

