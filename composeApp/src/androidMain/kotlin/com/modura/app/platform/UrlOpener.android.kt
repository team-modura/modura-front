package com.modura.app.platform

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.modura.app.MainApplication

actual fun openUrl(url: String) {
    val context: Context = MainApplication.INSTANCE
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}
