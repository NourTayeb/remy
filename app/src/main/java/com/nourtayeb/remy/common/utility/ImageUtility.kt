package com.nourtayeb.movies_mvi.common.utility

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget

fun ImageView.loadImageFromUrl(imageUrl: String?) {
    Glide.with(this.context)
        .load(imageUrl)
        .into(this)
}

fun String.getBitmapOfUrl(imageView:ImageView,onLoaded: (Bitmap) -> Unit) {
    Glide.with(imageView.context)
        .asBitmap()
        .load(this)
        .into(object : CustomTarget<Bitmap>(){

            override fun onLoadCleared(placeholder: Drawable?) {

            }

            override fun onResourceReady(
                resource: Bitmap,
                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
            ) {
                onLoaded(resource)
            }
        })
}
