package com.example.recyclerviewpool.model.download

import android.app.Activity
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import com.example.recyclerviewpool.checkconnect.CheckingPermission
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.net.URLConnection


class Downloader(val activity: Activity?) : AsyncTask<String, Int, String>() {

    override fun doInBackground(vararg urlLink: String?): String? {
        if (CheckingPermission.showPermission(
                activity!!,
                10,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            var count: Int
            try {
                val url = URL(urlLink[0])
                val conexion: URLConnection = url.openConnection()
                conexion.connect()
                val lenghtOfFile: Int = conexion.contentLength

                val input: InputStream = BufferedInputStream(url.openStream())
                var link = Environment.getExternalStorageDirectory().path
                Log.d("DOWNLOAD", link.toString())
                val output: OutputStream = FileOutputStream("")
                val data = ByteArray(1024)
                var total: Long = 0
                while (input.read(data).also { count = it } != -1) {
                    total += count.toLong()
                    publishProgress((total * 100 / lenghtOfFile).toInt())
                    output.write(data, 0, count)
                }
                output.flush()
                output.close()
                input.close()
            } catch (e: Exception) {
            }

        }
        return ""
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        Log.d("DOWNLOAD", values.toString())



    }

}
