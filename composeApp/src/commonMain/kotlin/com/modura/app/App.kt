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
import com.modura.app.data.AuthRepository
import com.modura.app.data.createDataStore
import com.modura.app.navigation.HomeScreenTab
import com.modura.app.ui.components.BottomNavigationBar
import com.modura.app.ui.screens.login.LoginScreen
import com.modura.app.ui.theme.ModuraTheme

@Composable
expect fun AppContext(content: @Composable () -> Unit)

@Composable
fun App() {
    AppContext {
        ModuraTheme {
            val dataStore = createDataStore()
            val authRepository = remember { AuthRepository(dataStore) }

            /* --- API 연동 전까지 임시 주석 처리 ---
            val loginTokenState by authRepository.authTokenStream.collectAsState(initial = null)

            when (loginTokenState) {
                null -> { // 로딩 상태
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                "" -> { // 로그아웃 상태
                    Navigator(LoginScreen(authRepository))
                }
                else -> { // 로그인 상태
                    MainAppContent()
                }
            }
            */

            Navigator(LoginScreen(authRepository))
        }
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
                    // 현재 선택된 탭을 전달
                    currentScreen = tabNavigator.current,
                    onTabSelected = { selectedTab ->
                        tabNavigator.current = selectedTab
                    }
                )
            }
        )
    }
}