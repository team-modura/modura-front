package com.modura.app.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import okio.Path

internal const val DATASTORE_FILE_NAME = "modura_prefs.preferences_pb"

@Composable
fun createDataStore(): DataStore<Preferences> {
    val context = LocalContext.current
    if (context == null) {
        throw IllegalStateException("Context not provided")
    }
    return remember {createDataStore(producePath = { context.dataStoreFile(DATASTORE_FILE_NAME).toString() })}
}

internal expect fun Context.dataStoreFile(fileName: String): Path
