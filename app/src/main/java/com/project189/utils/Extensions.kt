package com.project189.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.project189.R
import java.io.File

fun ImageView.loadImage(url: String?) {
    if (url.isNullOrEmpty()) return
    // If it looks like a local asset name (no http), load from assets
    if (!url.startsWith("http")) {
        val assetPath = "file:///android_asset/$url"
        Glide.with(this)
            .load(assetPath)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.intro_pic)
            .error(R.drawable.intro_pic)
            .into(this)
    } else {
        Glide.with(this)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.intro_pic)
            .error(R.drawable.intro_pic)
            .into(this)
    }
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.dialPhone(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
    startActivity(intent)
}

fun Context.sendSms(phone: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$phone"))
    startActivity(intent)
}
