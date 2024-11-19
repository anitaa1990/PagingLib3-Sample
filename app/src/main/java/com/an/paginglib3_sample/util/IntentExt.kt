package com.an.paginglib3_sample.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.share(url: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    this.startActivity(shareIntent)
}

fun Context.openUrl(url: String) {
    val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    this.startActivity(urlIntent)
}
