package com.modura.app.ui.screens.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions


object MypageTab : Tab {

    override val options: TabOptions
        @Composable
        get() = remember {
            TabOptions(
                index = 3u,
                title = "Mypage"
            )
        }

    @Composable
    override fun Content() {
        Navigator(screen = MyPageScreen)
    }
}