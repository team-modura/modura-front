package com.modura.app.navigation

import cafe.adriel.voyager.core.screen.Screen
import com.modura.app.ui.screens.home.HomeScreen
import com.modura.app.ui.screens.list.ListScreen
import com.modura.app.ui.screens.mypage.MyPageScreen
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.ic_home
import modura.composeapp.generated.resources.ic_list
import modura.composeapp.generated.resources.ic_mypage
import org.jetbrains.compose.resources.DrawableResource

sealed class BottomNavItem(
    val route: Screen,
    val title: String,
    val icon: DrawableResource
) {
    object Home : BottomNavItem(
        route = HomeScreen,
        title = "Home",
        icon = Res.drawable.ic_home
    )

    object List : BottomNavItem(
        route = ListScreen,
        title = "list",
        icon = Res.drawable.ic_list
    )

    object MyPage : BottomNavItem(
        route = MyPageScreen,
        title = "mypage",
        icon = Res.drawable.ic_mypage
    )
}