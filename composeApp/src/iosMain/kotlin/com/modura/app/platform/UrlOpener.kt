package com.modura.app.platform

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun openUrl(url: String) {
    val nsUrl = url.let { NSURL.URLWithString(it) }
    nsUrl?.let {
        UIApplication.sharedApplication.openURL(it)
    }
}