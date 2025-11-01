package com.modura.app.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions


object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() = remember {
            TabOptions(
                index = 0u,
                title = "Home"
            )
        }

    @Composable
    override fun Content() {
        Navigator(screen = HomeScreen)
    }
}