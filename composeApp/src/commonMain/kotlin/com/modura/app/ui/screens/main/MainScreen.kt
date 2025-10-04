package com.modura.app.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.modura.app.navigation.HomeScreenTab
import com.modura.app.navigation.ListScreenTab
import com.modura.app.navigation.MyPageScreenTab

object MainScreen : Screen {
    @Composable
    override fun Content() {
        // 1. TabNavigator로 시작 탭(HomeScreenTab)을 감쌉니다.
        TabNavigator(HomeScreenTab) {
            // 2. Scaffold를 사용하여 화면의 전체적인 구조를 잡습니다.
            Scaffold(
                content = { padding ->
                    // 3. 현재 선택된 탭의 Content를 content 영역에 표시합니다.
                    Box(modifier = Modifier.padding(padding)) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    // 4. bottomBar 영역에 NavigationBar를 구현합니다.
                    NavigationBar {
                        // 5. 만들어둔 모든 탭들을 아이템으로 추가합니다.
                        TabNavigationItem(HomeScreenTab)
                        TabNavigationItem(ListScreenTab)
                        TabNavigationItem(MyPageScreenTab)
                    }
                }
            )
        }
    }
}

// NavigationBar의 각 아이템을 그리는 Composable 함수
@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        label = { Text(text = tab.options.title) },
        icon = {
            tab.options.icon?.let { icon ->
                Icon(painter = icon, contentDescription = tab.options.title)
            }
        }
    )
}