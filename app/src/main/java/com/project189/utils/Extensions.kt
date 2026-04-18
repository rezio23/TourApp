package com.project189.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.project189.R
import java.security.MessageDigest

fun ImageView.loadImage(url: String?) {
    if (url.isNullOrEmpty()) return
    // If it looks like a local asset name (no http), load from assets
    if (!url.startsWith("http")) {
        val assetPath = "file:///android_asset/$url"
        // Compute an MD5 signature of the asset contents so Glide reloads when the file changes
        val signature = try {
            context.assets.open(url).use { input ->
                val buffer = ByteArray(8 * 1024)
                val md = MessageDigest.getInstance("MD5")
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    md.update(buffer, 0, read)
                }
                md.digest().joinToString("") { "%02x".format(it) }
            }
        } catch (e: Exception) {
            null
        }

        val request = Glide.with(this)
            .load(assetPath)
            .transition(DrawableTransitionOptions.withCrossFade())

        if (signature != null) request.signature(ObjectKey(signature))

        request
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
