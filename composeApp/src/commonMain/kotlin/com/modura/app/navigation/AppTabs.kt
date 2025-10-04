package com.modura.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.modura.app.ui.screens.home.HomeScreen
import org.jetbrains.compose.resources.painterResource

internal val HomeScreenTab = object : Tab {
    override val key: String = "HomeScreenTabKey"
    override val options: TabOptions
        @Composable
        get() {
            val title = BottomNavItem.Home.title
            val icon = painterResource(BottomNavItem.Home.icon)
            return remember { TabOptions(index = 0u, title = title, icon = icon) }
        }

    @Composable
    override fun Content() {
        Navigator(HomeScreen)
    }
}

internal val ListScreenTab = object : Tab {
    override val key: String = "ListScreenTabKey"
    override val options: TabOptions
        @Composable
        get() {
            val title = BottomNavItem.List.title
            val icon = painterResource(BottomNavItem.List.icon)
            return remember { TabOptions(index = 1u, title = title, icon = icon) }
        }

    @Composable
    override fun Content() {
        Navigator(BottomNavItem.List.route)
    }
}

internal val MyPageScreenTab = object : Tab {
    override val key: String = "MyPageScreenTabKey"
    override val options: TabOptions
        @Composable
        get() {
            val title = BottomNavItem.MyPage.title
            val icon = painterResource(BottomNavItem.MyPage.icon)
            return remember { TabOptions(index = 2u, title = title, icon = icon) }
        }

    @Composable
    override fun Content() {
        Navigator(BottomNavItem.MyPage.route)
    }
}