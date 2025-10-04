package com.modura.app.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.modura.app.navigation.HomeScreenTab
import com.modura.app.ui.components.BottomNavigationBar

object MainScreen : Screen {
    @Composable
    override fun Content() {
        TabNavigator(HomeScreenTab) { tabNavigator ->
            Scaffold(
                content = { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    BottomNavigationBar(
                        currentScreen = tabNavigator.current,
                        onTabSelected = { tab -> tabNavigator.current = tab }
                    )
                }
            )
        }
    }
}