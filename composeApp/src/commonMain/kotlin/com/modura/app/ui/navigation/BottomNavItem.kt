package com.modura.app.ui.navigation

import cafe.adriel.voyager.navigator.tab.Tab
import com.modura.app.ui.screens.Map.MapTab
import com.modura.app.ui.screens.home.HomeTab
import com.modura.app.ui.screens.mypage.MypageTab
import com.modura.app.ui.screens.search.SearchTab
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.ic_home_selected
import modura.composeapp.generated.resources.ic_home_unselected
import modura.composeapp.generated.resources.ic_map_selected
import modura.composeapp.generated.resources.ic_map_unselected
import modura.composeapp.generated.resources.ic_mypage_selected
import modura.composeapp.generated.resources.ic_mypage_unselected
import modura.composeapp.generated.resources.ic_search_selected
import modura.composeapp.generated.resources.ic_search_unselected
import org.jetbrains.compose.resources.DrawableResource

sealed class BottomNavItem(
    val route: Tab,
    val title: String,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource
) {
    object Home : BottomNavItem(
        route = HomeTab,
        title = "Home",
        selectedIcon = Res.drawable.ic_home_selected,
        unselectedIcon = Res.drawable.ic_home_unselected
    )

    object Search : BottomNavItem(
        route = SearchTab,
        title = "search",
        selectedIcon = Res.drawable.ic_search_selected,
        unselectedIcon = Res.drawable.ic_search_unselected
    )

    object Map : BottomNavItem(
        route = MapTab,
        title = "map",
        selectedIcon = Res.drawable.ic_map_selected,
        unselectedIcon = Res.drawable.ic_map_unselected
    )

    object MyPage : BottomNavItem(
        route = MypageTab,
        title = "mypage",
        selectedIcon = Res.drawable.ic_mypage_selected,
        unselectedIcon = Res.drawable.ic_mypage_unselected
    )
}