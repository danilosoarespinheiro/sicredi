package com.example.sicredi.util

import android.content.Context
import android.text.format.DateFormat
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sicredi.R
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

        @BindingAdapter("android:price")
        @JvmStatic
        fun price(view: TextView, text: Double?) {
            view.text = "R$ " + text.toString()
        }

        @BindingAdapter("android:convertLongToDate")
        @JvmStatic
        fun convertLongToDate(view: TextView, time: Long) {
            view.text = convert(time)
        }

        fun convert(time: Long): String {
            val calendar = Calendar.getInstance(Locale.ENGLISH)
            calendar.timeInMillis = time * 1000L
            return DateFormat.format("yyyy-MM-dd", calendar).toString()

        }
    }
}
