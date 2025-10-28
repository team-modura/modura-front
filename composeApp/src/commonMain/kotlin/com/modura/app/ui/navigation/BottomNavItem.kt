package com.modura.app.ui.navigation

import cafe.adriel.voyager.core.screen.Screen
import com.modura.app.ui.screens.Map.MapScreen
import com.modura.app.ui.screens.home.HomeScreen
import com.modura.app.ui.screens.search.SearchScreen
import com.modura.app.ui.screens.mypage.MyPageScreen
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

sealed class BottomNavItem(
    val route: Screen,
    val title: String,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource
) {
    object Home : BottomNavItem(
        route = HomeScreen,
        title = "Home",
        selectedIcon = Res.drawable.ic_home_selected,
        unselectedIcon = Res.drawable.ic_home_unselected
    )

    object Search : BottomNavItem(
        route = SearchScreen,
        title = "search",
        selectedIcon = Res.drawable.ic_search_selected,
        unselectedIcon = Res.drawable.ic_search_unselected
    )

    object Map : BottomNavItem(
        route = MapScreen,
        title = "map",
        selectedIcon = Res.drawable.ic_map_selected,
        unselectedIcon = Res.drawable.ic_map_unselected
    )

    object MyPage : BottomNavItem(
        route = MyPageScreen,
        title = "mypage",
        selectedIcon = Res.drawable.ic_mypage_selected,
        unselectedIcon = Res.drawable.ic_mypage_unselected
    )
}