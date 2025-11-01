package com.modura.app.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.modura.app.ui.components.BottomNavigationBar
import com.modura.app.ui.navigation.BottomNavItem
import com.modura.app.ui.navigation.toTab

object MainScreen : Screen {
    override val key: ScreenKey = "MainScreenKey"

    @Composable
    override fun Content() {
        TabNavigator(tab = BottomNavItem.Home.route.toTab()) { tabNavigator ->
            Scaffold(
                content = { padding -> Box(modifier = Modifier.padding(padding)) { CurrentTab() } },
                bottomBar = {
                    BottomNavigationBar(
                        currentTab = tabNavigator.current,
                        onTabSelected = { selectedTab -> tabNavigator.current = selectedTab }
                    )
                }
            )
        }
    }
}