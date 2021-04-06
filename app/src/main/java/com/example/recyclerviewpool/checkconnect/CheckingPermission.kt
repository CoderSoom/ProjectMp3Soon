package com.example.recyclerviewpool.checkconnect

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object CheckingPermission {

    fun checkPermission(context: Context, vararg permissions: String): Boolean {
        for (permission in permissions) {
            val check = ActivityCompat.checkSelfPermission(context, permission)
            if (check != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun showPermission(act: Activity, requestCode:Int, vararg permissions: String ): Boolean {
        if (checkPermission(act, *permissions)) {
            return true
        }
        ActivityCompat.requestPermissions(
            act, permissions,
            requestCode
        )
        return false
    }

}