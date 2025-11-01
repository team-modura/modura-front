package com.modura.app.ui.screens.Map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions


object MapTab : Tab {

    override val options: TabOptions
        @Composable
        get() = remember {
            TabOptions(
                index = 2u,
                title = "Map"
            )
        }

    @Composable
    override fun Content() {
        Navigator(screen = MapScreen)
    }
}