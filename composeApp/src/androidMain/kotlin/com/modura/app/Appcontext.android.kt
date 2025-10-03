package com.modura.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.modura.app.data.Context
import com.modura.app.data.ProvideDataStoreContext

@Composable
actual fun AppContext(content: @Composable () -> Unit) {
    ProvideDataStoreContext(Context(LocalContext.current)) {
        content()
    }
}
