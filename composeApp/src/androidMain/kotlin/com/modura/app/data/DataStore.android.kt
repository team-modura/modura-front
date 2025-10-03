package com.modura.app.data

import okio.Path
import okio.Path.Companion.toPath
internal actual fun Context.dataStoreFile(fileName: String): Path {
    return androidContext.filesDir.resolve(fileName).absolutePath.toPath()
}
