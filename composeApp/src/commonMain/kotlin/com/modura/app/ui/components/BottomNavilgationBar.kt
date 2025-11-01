package com.modura.app.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import com.modura.app.ui.navigation.BottomNavItem
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomNavigationBar(
    currentTab: Tab,
    onTabSelected: (Tab) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Map,
        BottomNavItem.MyPage
    )


    NavigationBar (
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        containerColor = Color.White
    ){
        items.forEach { item ->
            val tab = item.route
            val isSelected =  currentTab.options.index == tab.options.index

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        painter = painterResource(if (isSelected) item.selectedIcon else item.unselectedIcon),
                        contentDescription = item.title
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Unspecified,
                    unselectedIconColor = Color.Unspecified,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}