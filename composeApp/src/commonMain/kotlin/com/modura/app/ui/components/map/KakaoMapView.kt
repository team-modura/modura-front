package com.modura.app.ui.components.map

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.modura.app.data.dev.LatLng
import com.modura.app.ui.screens.map.MapScreenModel


@Composable
expect fun KakaoMapView(modifier: Modifier, screenModel: MapScreenModel)