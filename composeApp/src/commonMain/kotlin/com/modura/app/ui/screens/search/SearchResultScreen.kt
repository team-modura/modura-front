package com.modura.app.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.modura.app.LocalRootNavigator
import com.modura.app.data.dto.response.list.MediaResponseDto
import com.modura.app.data.repositoryImpl.LocalRepositoryImpl
import com.modura.app.domain.repository.LocalRepository
import com.modura.app.ui.components.ListContentItem
import com.modura.app.ui.components.SearchField
import com.modura.app.ui.screens.detail.ContentDetailScreen
import com.modura.app.ui.theme.Gray100
import com.russhwolf.settings.Settings

data class SearchResultScreen(val searchTerm: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalRootNavigator.current!!
        val localRepository: LocalRepository = remember { LocalRepositoryImpl(Settings()) }
        var searchValue by remember { mutableStateOf(searchTerm) }
        var searchContentList by remember { mutableStateOf<List<MediaResponseDto>>(emptyList()) }
        val dummyData = remember {
            List(20) { index ->
                MediaResponseDto(
                    bookmark = true,
                    id = index,
                    title = "검색 결과 아이템 $index",
                    rank = "1",
                    ott = listOf("netflix", "watcha"),
                    image = ""
                )
            }
        }
        LaunchedEffect(searchValue) {
            if (searchValue.isNotBlank()) {
                searchContentList = dummyData.filter { it.title.contains(searchValue.trim(), ignoreCase = true) }
            } else {
                searchContentList = emptyList()
            }
        }

        Box(
            modifier = Modifier
                .background(Gray100)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxSize(),
            ) {
                Spacer(Modifier.height(20.dp))
                SearchField(
                    value = searchValue,
                    onValueChange = { searchValue = it },
                    onSearch = { searchTerm ->
                        if (searchTerm.isNotBlank()) {
                            localRepository.addSearchTerm(searchTerm)
                            searchValue = searchTerm
                        }
                    })
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 20.dp)
                ) {
                    items(searchContentList) { item ->
                        ListContentItem(
                            bookmark = item.bookmark,
                            id = item.id,
                            image = item.image,
                            title = item.title,
                            rank = item.rank,
                            onClick = { clickedId ->
                                navigator?.push(ContentDetailScreen(clickedId))
                            }
                        )
                    }
                }
            }
        }
    }
}