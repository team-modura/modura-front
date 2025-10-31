package com.modura.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

//Screen을 Tab으로 변환해주는 확장 함수
internal fun Screen.toTab(): Tab = object : Tab {
    override val key: ScreenKey = this@toTab.key

    @Composable
    override fun Content() {
        this@toTab.Content()
    }

    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(index = 0u, title = "")
        }
}