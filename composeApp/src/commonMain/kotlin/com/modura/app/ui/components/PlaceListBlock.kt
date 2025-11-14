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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.modura.app.LocalRootNavigator
import com.modura.app.data.dev.PlaceInfo
import com.modura.app.ui.screens.detail.PlaceDetailScreen

@Composable
fun PlaceListBlock(
    modifier: Modifier = Modifier,
    places: List<PlaceInfo>,
    onPlaceClick: (String) -> Unit,
    onHandleClick: () -> Unit,
    //onDragDown: () -> Unit,
    //onDragUp: () -> Unit,
    isExpanded: Boolean
) {
    //var translationY by remember { mutableFloatStateOf(0f) }
    val listState = rememberLazyListState()
    val navigator = LocalRootNavigator.current!!

/*    LaunchedEffect(isExpanded) { if (isExpanded) { listState.animateScrollToItem(0) } }

    val draggableState = rememberDraggableState { delta ->
        if (isExpanded && listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0) {
            if (delta > 0) translationY += delta
        } else if (!isExpanded) {
            if (delta < 0) translationY += delta
        }
    }*/

    Column(
        modifier = modifier
            .fillMaxWidth()
            /*.graphicsLayer {
                this.translationY = translationY
            }
            .draggable(
                state = draggableState,
                orientation = Orientation.Vertical,
                onDragStopped = {
                    if (translationY > 150) {
                        onDragDown()
                    } else if (translationY < -150) {
                        onDragUp()
                    }
                    translationY = 0f
                }
            )*/
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier.clickable { onHandleClick() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.Gray)
                )
            }
        }
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            userScrollEnabled = isExpanded,
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(
                items = places,
                key = { it.id }
            ) { place ->
                ListMapItem(
                    place = place,
                    onClick = { onPlaceClick(place.id.toString())
                        navigator?.push(PlaceDetailScreen(place.id))}
                )
            }
        }
    }
}