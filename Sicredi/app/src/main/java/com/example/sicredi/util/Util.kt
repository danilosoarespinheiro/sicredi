package com.example.sicredi.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sicredi.R
import java.text.SimpleDateFormat
import java.util.*

class Util {

    companion object {
        @BindingAdapter("android:imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, url: String?) {
            loadImage(view, url, getProgressDrawable(view.context))
        }

        private fun loadImage(
            imageView: ImageView,
            url: String?,
            progressDrawable: CircularProgressDrawable?
        ) {
            val options: RequestOptions = RequestOptions()
                .placeholder(progressDrawable)
                .error(R.mipmap.ic_launcher)
            Glide.with(imageView.context)
                .setDefaultRequestOptions(options)
                .load(url)
                .into(imageView)
        }

        private fun getProgressDrawable(context: Context?): CircularProgressDrawable? {
            val cpd = CircularProgressDrawable(context!!)
            cpd.strokeWidth = 10f
            cpd.centerRadius = 50f
            cpd.start()
            return cpd
        }
    }
}
