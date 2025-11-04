package com.modura.app.ui.screens.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.modura.app.data.dev.DummyProvider
import com.modura.app.data.dev.LatLng
import com.modura.app.data.dev.PlaceInfo
import com.modura.app.data.repositoryImpl.LocalRepositoryImpl
import com.modura.app.domain.repository.LocalRepository
import com.modura.app.ui.components.LoginBottomSheet
import com.modura.app.ui.components.PlaceListBlock
import com.modura.app.ui.components.SearchField
import com.modura.app.ui.components.map.KakaoMapView
import com.modura.app.ui.screens.main.MainScreen
import com.modura.app.ui.screens.search.SearchResultScreen
import com.russhwolf.settings.Settings

object MapScreen : Screen {
    override val key: String = "MapScreenKey"

    @Composable
    override fun Content() {

        var searchValue by remember { mutableStateOf("") }
        val localRepository: LocalRepository = remember { LocalRepositoryImpl(Settings()) }
        var recentSearches by remember { mutableStateOf(listOf<String>()) }

        var places by remember { mutableStateOf<List<PlaceInfo>>(emptyList()) }
        var isPlaceListExpanded by remember { mutableStateOf(false) }

        val fullHeight = 400.dp
        val collapsedHeight = 160.dp

        val blockHeight by animateDpAsState(
            targetValue = if (isPlaceListExpanded) fullHeight else collapsedHeight,
            label = "placeListHeightAnimation"
        )

        LaunchedEffect(Unit) {
            places = DummyProvider.dummyPlaces
            if (places.isNotEmpty()) {
                isPlaceListExpanded = true
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            KakaoMapView(
                modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                    detectTapGestures {
                        isPlaceListExpanded = !isPlaceListExpanded
                        println("클릭됨")
                    }
                }
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .align(Alignment.TopCenter),
            ) {
                Spacer(Modifier.height(20.dp))
                SearchField(
                    value = searchValue,
                    onValueChange = { searchValue = it },
                    onSearch = { searchTerm ->
                        if (searchTerm.isNotBlank()) {
                            localRepository.addSearchTerm(searchTerm)
                            recentSearches = localRepository.getRecentSearches()
                            searchValue = ""
                        }
                    })
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    //여유있을 때 chip으로
                    Text(text = "AI 추천 촬영지",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { println("AI 추천 촬영지 클릭됨") }
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                    Text(
                        text = "인기 촬영지",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
                            .clickable { println("인기 촬영지 클릭됨") }
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                    Text(
                        text = "주변 촬영지",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
                            .clickable { println("주변 촬영지 클릭됨") }
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
            PlaceListBlock(
                modifier = Modifier.align(Alignment.BottomCenter).height(blockHeight),
                places = places,
                onPlaceClick = { placeId ->
                    println("장소 ID 클릭됨: $placeId")
                },
                onHandleClick = {
                    isPlaceListExpanded = !isPlaceListExpanded
                },
                isExpanded = isPlaceListExpanded
            )
            }
    }
}