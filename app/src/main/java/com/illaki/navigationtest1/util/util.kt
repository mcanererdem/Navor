package com.illaki.navigationtest1.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.illaki.navigationtest1.R

fun placeHolderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

fun ImageView.downloadFromUrl(url: String, progressBar: CircularProgressDrawable) {
    val rOptions = RequestOptions().placeholder(progressBar)
        .error(R.drawable.ic_launcher_foreground)

    Glide.with(context).setDefaultRequestOptions(rOptions).load(url).into(this)
}

@BindingAdapter("android:downloadWithUrl")
fun downloadUrl(view: ImageView, url: String) {
    view.downloadFromUrl(url, placeHolderProgressBar(view.context))
}