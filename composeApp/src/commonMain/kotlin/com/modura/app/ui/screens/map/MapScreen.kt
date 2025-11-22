package com.modura.app.ui.screens.map

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.modura.app.LocalRootNavigator
import com.modura.app.data.repositoryImpl.LocalRepositoryImpl
import com.modura.app.domain.repository.LocalRepository
import com.modura.app.ui.components.PlaceListBlock
import com.modura.app.ui.components.SearchField
import com.modura.app.ui.components.map.KakaoMapView
import com.modura.app.ui.screens.detail.PlaceDetailScreen
import com.modura.app.ui.screens.home.HomeScreenModel
import com.modura.app.ui.theme.Gray900
import com.modura.app.ui.theme.White
import com.russhwolf.settings.Settings
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import modura.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource

import modura.composeapp.generated.resources.*

enum class SheetStep { PEEK, MIDDLE, FULL }

object MapScreen : Screen {
    override val key: String = "MapScreenKey"

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalRootNavigator.current!!
        val screenModel = getScreenModel<MapScreenModel>()
        val homeScreenModel = getScreenModel<HomeScreenModel>()
        val uiState by screenModel.uiState.collectAsState()
        val focusedPlaceId by screenModel.focusedPlaceId.collectAsState()
        val coroutineScope = rememberCoroutineScope()
        var scrollToTopTrigger by remember { mutableStateOf<Any?>(null) }

        LaunchedEffect(Unit) {
            screenModel.scrollToTopEvent.collect {
                scrollToTopTrigger = Any()
            }
        }

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val screenHeight = maxHeight

            val peekHeight = 152.dp
            val middleHeight = 280.dp
            val fullHeight = screenHeight - 180.dp

            var currentStep by remember { mutableStateOf(SheetStep.MIDDLE) }
            var visualStep by remember { mutableStateOf(SheetStep.MIDDLE) }
            val targetSheetHeight = when (visualStep) {
                SheetStep.PEEK -> peekHeight
                SheetStep.MIDDLE -> middleHeight
                SheetStep.FULL -> fullHeight
            }

            val animatedSheetHeight by animateDpAsState(
                targetValue = targetSheetHeight,
                animationSpec = tween(durationMillis =300, easing = FastOutSlowInEasing),
                label = "SheetHeightAnimation"
            )

            val scaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberStandardBottomSheetState(
                    initialValue = SheetValue.Expanded,
                    skipHiddenState = true
                )
            )
            LaunchedEffect(currentStep) {
                when (currentStep) {
                    SheetStep.PEEK -> {
                        scaffoldState.bottomSheetState.partialExpand()
                        delay(150)
                        visualStep = SheetStep.PEEK
                    }
                    SheetStep.MIDDLE -> {
                        if (visualStep == SheetStep.PEEK) {
                            visualStep = SheetStep.MIDDLE
                            delay(50)
                            scaffoldState.bottomSheetState.expand()
                        } else {
                            scaffoldState.bottomSheetState.expand()
                            visualStep = SheetStep.MIDDLE
                        }
                    }
                    SheetStep.FULL -> {
                        visualStep = SheetStep.FULL
                        scaffoldState.bottomSheetState.expand()
                    }
                }
            }

            var searchValue by remember { mutableStateOf("") }
            val localRepository: LocalRepository = remember { LocalRepositoryImpl(Settings()) }

            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetContentColor = MaterialTheme.colorScheme.surface,
                sheetContainerColor = MaterialTheme.colorScheme.surface,
                sheetPeekHeight = peekHeight,
                sheetShadowElevation = 0.dp,
                sheetSwipeEnabled = false,
                sheetDragHandle = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .height(30.dp)
                            .background(MaterialTheme.colorScheme.surface)
                            .draggable(
                                orientation = Orientation.Vertical,
                                state = rememberDraggableState { delta ->
                                },
                                onDragStopped = { velocity ->
                                    val threshold = 100f

                                    coroutineScope.launch {
                                        if (velocity < -threshold) {
                                            when (currentStep) {
                                                SheetStep.PEEK -> {
                                                    currentStep = SheetStep.MIDDLE
                                                    scaffoldState.bottomSheetState.expand()
                                                }
                                                SheetStep.MIDDLE -> {
                                                    currentStep = SheetStep.FULL
                                                    scaffoldState.bottomSheetState.expand()
                                                }
                                                else -> { }
                                            }
                                        }
                                        else if (velocity > threshold) {
                                            when (currentStep) {
                                                SheetStep.FULL -> {
                                                    currentStep = SheetStep.MIDDLE
                                                    scaffoldState.bottomSheetState.expand()
                                                }
                                                SheetStep.MIDDLE -> {
                                                    currentStep = SheetStep.PEEK
                                                    scaffoldState.bottomSheetState.partialExpand()
                                                }
                                                else -> {}
                                            }
                                        }
                                    }
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color.Gray)
                        )
                    }
                },

                sheetContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(animatedSheetHeight)
                            .clipToBounds(),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(fullHeight)
                        ) {
                            PlaceListBlock(
                                modifier = Modifier.fillMaxSize(),
                                places = uiState.places,
                                focusedPlaceId = focusedPlaceId,
                                onCenterItemChanged = { place -> screenModel.setFocusedPlace(place) },
                                onPlaceClick = { placeId -> navigator.push(PlaceDetailScreen(placeId)) },
                                scrollToTopTrigger = scrollToTopTrigger
                            )
                        }
                    }
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)
                ) {
                    if (uiState.inProgress && uiState.places.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        KakaoMapView(
                            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface).pointerInput(Unit) {
                                detectTapGestures {
                                    coroutineScope.launch {
                                        if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                                            scaffoldState.bottomSheetState.partialExpand()
                                        }
                                    }
                                }
                            },
                            places = uiState.places,
                            focusedPlaceId = focusedPlaceId,
                            currentLocation = uiState.currentLocation,
                            cameraEvent = uiState.cameraEvent,
                            onCameraEventConsumed = { screenModel.consumeCameraEvent() },
                            onMarkerClick = { place ->
                                screenModel.setFocusedPlace(place)
                                if(currentStep == SheetStep.PEEK) currentStep = SheetStep.MIDDLE
                            }
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    ) {
                        SearchField(
                            value = searchValue,
                            onValueChange = { searchValue = it },
                            onSearch = { searchTerm ->
                                if (searchTerm.isNotBlank()) {
                                    coroutineScope.launch {
                                        screenModel.getPlaces(searchTerm)
                                        localRepository.addSearchTerm(searchTerm)
                                        currentStep = SheetStep.MIDDLE
                                        scaffoldState.bottomSheetState.expand()                                    }
                                }
                            },
                            white = true
                        )
                        Spacer(Modifier.height(4.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "AI 추천 촬영지",
                                style = MaterialTheme.typography.bodySmall,
                                color = Gray900,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable { println("AI 추천 촬영지 클릭됨") }
                                    .background(White)
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                            Text(
                                text = "인기 촬영지",
                                style = MaterialTheme.typography.bodySmall,
                                color = Gray900,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable {
                                        coroutineScope.launch {
                                            screenModel.getPlacesByPopularity()
                                            currentStep = SheetStep.MIDDLE
                                            scaffoldState.bottomSheetState.expand()                                        }
                                    }
                                    .background(White)
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                            Text(
                                text = "주변 촬영지",
                                style = MaterialTheme.typography.bodySmall,
                                color = Gray900,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable {
                                        coroutineScope.launch {
                                            screenModel.getPlacesByDistance()
                                            currentStep = SheetStep.MIDDLE
                                            scaffoldState.bottomSheetState.expand()                                        }
                                    }
                                    .background(White)
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                            Spacer(Modifier.weight(1f))
                            IconButton(
                                onClick = { screenModel.moveToCurrentLocation() }
                            ) {
                                Icon(
                                    modifier = Modifier.size(36.dp),
                                    painter = painterResource(Res.drawable.ic_my_location),
                                    contentDescription = "내 위치로 이동",
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
                }

                LaunchedEffect(uiState.places) {
                    if (uiState.places.isNotEmpty()) {
                        if (scaffoldState.bottomSheetState.currentValue == SheetValue.Hidden) {
                            scaffoldState.bottomSheetState.partialExpand()
                        }
                    }
                }
            }
        }
    }
}