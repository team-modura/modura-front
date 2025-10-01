package com.modura.app.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import com.modura.app.HomeScreenTab
import com.modura.app.ListScreenTab
import com.modura.app.MyPageScreenTab

@Composable
fun BottomNavigationBar(
    currentScreen: Tab,
    onTabSelected: (Tab) -> Unit
) {
    val tabs = listOf(HomeScreenTab, ListScreenTab, MyPageScreenTab)

    NavigationBar (
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
        containerColor = Color.White
    ){
        tabs.forEach { tab ->
            val title = tab.options.title
            val iconPainter = tab.options.icon ?: return@forEach

            NavigationBarItem(
                selected = currentScreen == tab,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        painter = iconPainter,
                        contentDescription = title
                    )
                },
                label = { Text(title) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}