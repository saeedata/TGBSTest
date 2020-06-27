package org.technical.android.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.widget.ViewUtils
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.internal.ViewUtils.dpToPx
import com.google.android.material.shape.CornerFamily
import org.technical.android.util.glide.GlideApp

@BindingAdapter("imageCenterCrop")
fun loadImageCenterCrop(imageView: ImageView, url: String?) {
    GlideApp.with(imageView.context)
        .load(url)
        .apply(RequestOptions.centerCropTransform())
        .into(imageView)
}

@BindingAdapter("roundCorners")
fun roundTopCorners(imageView: ShapeableImageView, radius: Int) {
    imageView.shapeAppearanceModel = imageView.shapeAppearanceModel
        .toBuilder()
        .setAllCorners(CornerFamily.ROUNDED, dpToPx(imageView.context,radius).toFloat())
        .build()
}

@BindingAdapter("imageResource")
fun setImageResource(imageView: ImageView, drawable: Drawable?) {
    imageView.setImageDrawable(drawable)
}