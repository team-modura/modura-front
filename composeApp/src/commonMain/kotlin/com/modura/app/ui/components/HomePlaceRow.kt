package com.modura.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.modura.app.domain.model.response.search.SearchPlaceResponseModel
import com.modura.app.ui.screens.detail.ContentDetailScreen
import com.modura.app.ui.screens.detail.PlaceDetailScreen

@Composable
 fun HomePlaceRow(
    items: List<SearchPlaceResponseModel>,
    navigator: Navigator?
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        itemsIndexed(items) { index, item ->
            LocationItemSmall(
                id = item.id,
                image = item.thumbnail?:"",
                location = item.name,
                rank = index + 1,
                onClick = {
                    navigator?.push(PlaceDetailScreen(item.id))
                }
            )
        }
    }
}