package com.modura.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.modura.app.domain.model.response.search.SearchContentResponseModel
import com.modura.app.ui.screens.detail.ContentDetailScreen

@Composable
 fun HomeContentRow(
    items: List<SearchContentResponseModel>,
    navigator: Navigator?
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        itemsIndexed(items) { index, item ->
            ContentItemSmall(
                id = item.id,
                bookmark = item.isLiked,
               // ott = item.ottList,
                image = item.thumbnail?:"",
                title = item.title,
                rank = index + 1,
                onClick = {
                    navigator?.push(ContentDetailScreen(item.id))
                }
            )
        }
    }
}