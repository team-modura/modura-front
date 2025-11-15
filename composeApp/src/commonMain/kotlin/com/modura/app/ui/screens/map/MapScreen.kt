package com.modura.app.ui.screens.map

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.modura.app.LocalRootNavigator
import com.modura.app.data.dev.DummyProvider
import com.modura.app.data.dev.PlaceInfo
import com.modura.app.data.repositoryImpl.LocalRepositoryImpl
import com.modura.app.domain.repository.LocalRepository
import com.modura.app.ui.components.PlaceListBlock
import com.modura.app.ui.components.SearchField
import com.modura.app.ui.components.map.KakaoMapView
import com.modura.app.ui.screens.detail.PlaceDetailScreen
import com.russhwolf.settings.Settings
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

object MapScreen : Screen {
    override val key: String = "MapScreenKey"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalRootNavigator.current!!
        val screenModel = getScreenModel<MapScreenModel>()
        val uiState by screenModel.uiState.collectAsState()
        val coroutineScope = rememberCoroutineScope()

        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberStandardBottomSheetState(
                initialValue = SheetValue.PartiallyExpanded,
                skipHiddenState = true
            )
        )

        var searchValue by remember { mutableStateOf("") }
        val localRepository: LocalRepository = remember { LocalRepositoryImpl(Settings()) }

        var isPlaceListExpanded by remember { mutableStateOf(false) }

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 140.dp,
            sheetContent = {
                PlaceListBlock(
                    modifier = Modifier.fillMaxWidth(),
                    places = uiState.places,
                    onCenterItemChanged = { place -> screenModel.setFocusedPlace(place) },
                    onPlaceClick = { placeId -> navigator.push(PlaceDetailScreen(placeId)) }
                )
            },
            sheetContainerColor = Color.White,
            sheetShadowElevation = 0.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (uiState.inProgress && uiState.places.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    KakaoMapView(
                        modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                            detectTapGestures {
                                // 맵 클릭 시 시트 상태를 토글
                                coroutineScope.launch {
                                    if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                                        scaffoldState.bottomSheetState.partialExpand()
                                    } else {
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                }
                            }
                        },
                        places = uiState.places, // screenModel이 아닌 places 직접 전달
                        currentLocation = uiState.currentLocation // screenModel이 아닌 currentLocation 직접 전달
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp) // 상태바 영역 고려
                ) {
                    SearchField(
                        value = searchValue,
                        onValueChange = { searchValue = it },
                        onSearch = { searchTerm ->
                            if (searchTerm.isNotBlank()) {
                                coroutineScope.launch {
                                    screenModel.getPlaces(searchTerm)
                                    localRepository.addSearchTerm(searchTerm)
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                        }
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "AI 추천 촬영지",
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
                                .clip(RoundedCornerShape(20.dp))
                                .clickable {
                                    coroutineScope.launch {
                                        screenModel.getPlacesByDistance()
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                }
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }
            LaunchedEffect(uiState.places) {
                if (uiState.places.isNotEmpty()) {
                    scaffoldState.bottomSheetState.partialExpand()
                }
            }
        }
    }
}