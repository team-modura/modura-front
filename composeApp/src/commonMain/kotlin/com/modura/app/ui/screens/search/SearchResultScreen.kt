package com.modura.app.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.modura.app.LocalRootNavigator
import com.modura.app.data.dev.PlaceInfo
import com.modura.app.data.dto.response.list.MediaResponseDto
import com.modura.app.data.repositoryImpl.LocalRepositoryImpl
import com.modura.app.domain.repository.LocalRepository
import com.modura.app.ui.components.ContentGrid
import com.modura.app.ui.components.ListContentItem
import com.modura.app.ui.components.ListLocationItem
import com.modura.app.ui.components.PlaceGrid
import com.modura.app.ui.components.SearchContentGrid
import com.modura.app.ui.components.SearchField
import com.modura.app.ui.components.SearchPlaceGrid
import com.modura.app.ui.components.TabItem
import com.modura.app.ui.screens.detail.ContentDetailScreen
import com.modura.app.ui.screens.detail.LocationDetailScreen
import com.modura.app.ui.theme.Gray100
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.Gray800
import com.modura.app.ui.theme.White
import com.russhwolf.settings.Settings

private enum class SearchCategory { CONTENT, PLACE }

data class SearchResultScreen(val searchTerm: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalRootNavigator.current!!
        val tabNavigator = LocalNavigator.currentOrThrow
        val localRepository: LocalRepository = remember { LocalRepositoryImpl(Settings()) }
        var searchValue by remember { mutableStateOf(searchTerm) }

        var contentList by remember { mutableStateOf<List<MediaResponseDto>>(emptyList()) }
        var placeList by remember { mutableStateOf<List<PlaceInfo>>(emptyList()) }

        var selectedCategory by remember { mutableStateOf(SearchCategory.CONTENT) }

        val dummyContentData = remember {
            List(20) { index ->
                MediaResponseDto(
                    bookmark = true,
                    id = index,
                    title = "검색된 컨텐츠 $index",
                    rank = "1",
                    ott = listOf("netflix", "watcha"),
                    image = ""
                )
            }
        }
        val dummyPlaceData = remember {
            List(15) { index ->
                PlaceInfo(
                    id = index,
                    name = "검색된 장소 $index",
                    address = "주소 $index",
                    distance = 1000,
                    rating = 4.5,
                    reviewCount = 100,
                    bookmark = true,
                    photoUrl = ""
                )
            }
        }

        LaunchedEffect(searchValue) {
            if (searchValue.isNotBlank()) {
                contentList = dummyContentData.filter {
                    it.title.contains(
                        searchValue.trim(),
                        ignoreCase = true
                    )
                }
                placeList = dummyPlaceData.filter {
                        it.name.contains(
                            searchValue.trim(),
                            ignoreCase = true
                        )
                    }
            } else {
                contentList = emptyList()
                placeList = emptyList()
            }
        }

        LaunchedEffect(searchValue) {
            if (searchValue.isEmpty()) {
                tabNavigator.pop()
            }
        }


        Box(
            modifier = Modifier.background(Gray100)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Spacer(Modifier.height(20.dp))
                SearchField(
                    value = searchValue,
                    onValueChange = { searchValue = it },
                    onSearch = { searchTerm ->
                        if (searchTerm.isNotBlank()) {
                            localRepository.addSearchTerm(searchTerm)
                            searchValue = searchTerm
                        } else {
                            tabNavigator.pop()
                        }
                    })

                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        TabItem(
                            text = "컨텐츠",
                            isSelected = selectedCategory == SearchCategory.CONTENT,
                            onClick = { selectedCategory = SearchCategory.CONTENT }
                        )
                        TabItem(
                            text = "장소",
                            isSelected = selectedCategory == SearchCategory.PLACE,
                            onClick = { selectedCategory = SearchCategory.PLACE }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(0.5.dp)
                            .background(Gray500)
                    )
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 20.dp)
                ) {
                    if (selectedCategory == SearchCategory.PLACE) {
                        items(placeList.size) { index ->
                            val item = placeList[index] as PlaceInfo
                            SearchPlaceGrid(
                                image = item.photoUrl ?: "",
                                onClick = {
                                    println("${item.name} 클릭됨")
                                    navigator.push(LocationDetailScreen(item.id))
                                }
                            )
                        }
                    } else {
                        items(contentList.size) { index ->
                            val item = contentList[index] as MediaResponseDto
                            SearchContentGrid(
                                image = item.image,
                                bookmark = true,
                                onClick = {
                                    println("${item.title} 클릭됨")
                                    navigator.push(ContentDetailScreen(title = item.title))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}