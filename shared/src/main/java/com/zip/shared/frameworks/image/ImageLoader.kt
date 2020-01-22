package com.zip.shared.frameworks.image

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.zip.shared.R

//Helper class for image loading
object ImageLoader {
    //Load image with Glide
    fun loadImage(
        view: ImageView,
        url: String,
        placeholder: Int = R.drawable.ic_image_grey_24dp
    ) {
        Glide.with(view)
            .load(url)
            .placeholder(placeholder)
            .error(placeholder)
            .fallback(placeholder)
            .into(view)
    }
}