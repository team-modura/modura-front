package com.modura.app.data


import okio.Path.Companion.toPath
import android.content.Context
import okio.Path

internal actual fun dataStoreFile(context: Any, fileName: String): Path {
    val androidContext = context as Context
    return context.filesDir.resolve(fileName).absolutePath.toPath()
}
