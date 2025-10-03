package com.modura.app.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

actual class Context(val androidContext: android.content.Context)
@Composable
actual fun ProvideDataStoreContext(context: Context, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalContext provides context) {
        content()
    }
}
