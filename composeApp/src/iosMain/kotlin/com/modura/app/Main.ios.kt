package com.modura.app

import androidx.compose.ui.window.ComposeUIViewController
import com.modura.app.data.AppDataStore
import com.modura.app.data.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
fun MainViewController() = ComposeUIViewController {
    // iOS 앱이 시작될 때 DataStore를 초기화하고 AppDataStore 객체에 할당합니다.
    AppDataStore.dataStore = createDataStore {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/modura_datastore.preferences_pb"
    }

    App() // App 컴포저블 호출
}