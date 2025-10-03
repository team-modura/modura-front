package com.modura.app.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

expect class Context

val LocalContext: ProvidableCompositionLocal<Context?> = staticCompositionLocalOf { null }

@Composable
expect fun ProvideDataStoreContext(context: Context, content: @Composable () -> Unit)
