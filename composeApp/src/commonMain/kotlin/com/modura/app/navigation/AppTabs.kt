package com.modura.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
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
        Navigator(BottomNavItem.Home.route)
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
