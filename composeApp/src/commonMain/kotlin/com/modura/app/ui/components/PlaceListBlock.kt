package com.modura.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.modura.app.LocalRootNavigator
import com.modura.app.data.dev.PlaceInfo
import com.modura.app.domain.model.response.map.PlaceResponseModel
import com.modura.app.ui.screens.detail.PlaceDetailScreen
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun PlaceListBlock(
    modifier: Modifier = Modifier,
    places: List<PlaceResponseModel>,
    onPlaceClick: (Int) -> Unit,
    focusedPlaceId: Int?,
    onCenterItemChanged: (PlaceResponseModel) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState, places) {
        if (places.isEmpty()) return@LaunchedEffect

        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo

            if (visibleItems.isEmpty()) return@snapshotFlow null

           val focusPoint = layoutInfo.viewportStartOffset + 200

            val focusedItem = visibleItems.find { item ->
                item.offset <= focusPoint && (item.offset + item.size) >= focusPoint
            }

            focusedItem?.index ?: listState.firstVisibleItemIndex
        }
            .distinctUntilChanged()
            .collect { index ->
                if (index != null && index in places.indices) {
                    onCenterItemChanged(places[index])
                }
            }
    }

    LaunchedEffect(focusedPlaceId) {
        if (focusedPlaceId != null) {
            val index = places.indexOfFirst { it.id == focusedPlaceId }
            // 현재 스크롤 위치와 다를 때만 이동 (무한 루프 방지)
            if (index != -1 && listState.firstVisibleItemIndex != index) {
                // listState.animateScrollToItem(index) // 필요하다면 주석 해제
            }
        }
    }


    Column(
        modifier = modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            items(
                items = places,
                key = { it.id }
            ) { place ->
                ListMapItem(
                    place = place,
                    isFocused = place.id == focusedPlaceId,
                    onClick = { onPlaceClick(place.id) }
                )
            }
        }
    }
}