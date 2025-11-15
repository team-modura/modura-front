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
    onCenterItemChanged: (PlaceResponseModel) -> Unit
) {
    val listState = rememberLazyListState()
    LaunchedEffect(listState, places) {
        if (places.isEmpty()) return@LaunchedEffect

        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (visibleItemsInfo.isEmpty()) {
                null
            } else {
                val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
                val centerMostItem = visibleItemsInfo.minByOrNull {
                    kotlin.math.abs((it.offset + it.size / 2) - viewportCenter)
                }
                centerMostItem?.index
            }
        }
            .distinctUntilChanged()
            .collect { centerIndex ->
                if (centerIndex != null && centerIndex in places.indices) {
                    onCenterItemChanged(places[centerIndex])
                }
            }
    }

    Column(
        modifier = modifier.fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            // userScrollEnabled는 항상 true여야 시트 내부 스크롤이 가능합니다.
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 32.dp), // 하단 여백 추가
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = places,
                key = { it.id }
            ) { place ->
                ListMapItem(
                    place = place,
                    onClick = { onPlaceClick(place.id) }
                )
            }
        }
    }
}