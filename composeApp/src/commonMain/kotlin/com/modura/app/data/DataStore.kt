package com.modura.app.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path

internal expect fun dataStoreFile(context: Any, fileName: String): Path
fun createDataStore(context: Any, fileName: String): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
    produceFile = {
        dataStoreFile(context, fileName)
    }
)
