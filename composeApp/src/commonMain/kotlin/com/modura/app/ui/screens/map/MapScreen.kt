package com.modura.app.ui.screens.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.modura.app.data.dev.LatLng
import com.modura.app.ui.components.map.KakaoMapView

object MapScreen : Screen {
    override val key: String = "MapScreenKey"
    @Composable
    override fun Content() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            KakaoMapView(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}