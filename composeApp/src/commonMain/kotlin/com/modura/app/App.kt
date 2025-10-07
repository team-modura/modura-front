package com.modura.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.modura.app.data.createDataStore
import com.modura.app.navigation.HomeScreenTab
import com.modura.app.ui.components.BottomNavigationBar
import com.modura.app.ui.screens.login.LoginScreen
import com.modura.app.ui.theme.ModuraTheme

@Composable
fun App() {
    ModuraTheme {
        MainAppContent()
    }
}

@Composable
private fun MainAppContent() {
    TabNavigator(HomeScreenTab) { tabNavigator ->
        Scaffold(
            content = { padding ->
                Box(Modifier.padding(padding)) {
                    CurrentTab()
                }
            },
            bottomBar = {
                BottomNavigationBar(
                    currentScreen = tabNavigator.current,
                    onTabSelected = { selectedTab ->
                        tabNavigator.current = selectedTab
                    }
                )
            }
        )
    }
}