package com.modura.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.modura.app.data.createDataStore
import com.modura.app.ui.components.BottomNavigationBar
import com.modura.app.ui.navigation.BottomNavItem
import com.modura.app.ui.screens.login.LoginScreen
import com.modura.app.ui.theme.ModuraTheme

@Composable
fun App() {
    ModuraTheme {
        Navigator(screen = LoginScreen()) {
            CurrentScreen()
        }
    }
}