package com.example.recyclerviewpool.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.recyclerviewpool.R
import com.vansuita.gaussianblur.GaussianBlur
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


object LoadDataUtils {
    @JvmStatic
    @BindingAdapter("loadImg")
    open fun loadImg(img: ImageView, linkImg: String?) {
        if (linkImg == "" || linkImg.equals("https://chiasenhac.vn/imgs/no_cover.jpg")) {
            img.setImageResource(R.drawable.logoapp)
        } else {
            Glide.with(img).load(linkImg)
                .fitCenter()
                .error(R.drawable.logoapp)
                .into(img)
        }

    }

    open fun loadImgBitMapBlur(context: Context?, img: ImageView, linkImg: String?) {

        if (linkImg == "") {
            img.setImageResource(R.drawable.logoapp)
        }
        Glide.with(img).asBitmap()
            .load(linkImg)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    GaussianBlur.with(context).size(50f).radius(10).put(resource, img)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }

    open fun loadImgBitMap(context: Context?, img: ImageView, linkImg: String?) {

        if (linkImg == "") {
            img.setImageResource(R.drawable.logoapp)
        }
        Glide.with(img).asBitmap()
            .load(linkImg)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    img.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }

    @JvmStatic
    @BindingAdapter("loadText")
    fun loadText(tv: TextView, subTitleSong: String?) {
        tv.text = subTitleSong
    }

    @SuppressLint("ResourceAsColor")
    @JvmStatic
    @BindingAdapter("loadTextColor")
    fun loadTextColor(tv: TextView, subTitleSong: String?) {
        tv.text = subTitleSong
        tv.setTextColor(R.color.colorGreen)


//        if (subTitleSong == "" || subTitleSong == null) {
//            return
//        } else {
//            var subText = subTitleSong!!.endsWith("kbps")
//            if (subText) {
//                var numberQuality = subTitleSong!!.indexOf("kbps")
//                var quality = subTitleSong!!.substring(0, numberQuality)
//                var number = Integer.parseInt(quality)
//                when {
//                    number <= 128 -> {
//                        tv.setTextColor(R.color.Green_600)
//                    }
//                    number in 129..320 -> {
//                        tv.setTextColor(R.color.Blue_600)
//                    }
//                }
//            } else {
//                tv.setTextColor(R.color.colorRed)
//            }
//        }

    }


    @JvmStatic
    @BindingAdapter("setTextDuration")
    fun setTextDuration(tv: TextView, content: Long?) {
        if (content == null) {
            tv.setText("0")
        }
        val format = SimpleDateFormat("mm:ss")
        tv.setText(
            format.format(content)
        )
    }

    @JvmStatic
    @BindingAdapter("loadImageUri")
    fun loadImageUri(tv: ImageView, content: String?) {
        if (content == null) {
            Glide.with(tv)
                .load(R.drawable.logoapp)
                .error(R.drawable.logoapp)
                .into(tv)
            return
        }
        Glide.with(tv)
            .load(Uri.parse(content))
            .error(R.drawable.logoapp)
            .into(tv)
    }

    fun getProgressText(time: Long): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date(time)
        val minute = calendar[Calendar.MINUTE].toDouble()
        val second = calendar[Calendar.SECOND].toDouble()
        val format = DecimalFormat("00")
        return format.format(minute) + ":" + format.format(second)
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }
}