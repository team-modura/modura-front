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
import com.modura.app.data.dto.response.list.MediaResponseDto
import com.modura.app.data.repositoryImpl.LocalRepositoryImpl
import com.modura.app.domain.repository.LocalRepository
import com.modura.app.ui.components.ListContentItem
import com.modura.app.ui.components.ListLocationItem
import com.modura.app.ui.components.SearchField
import com.modura.app.ui.screens.detail.ContentDetailScreen
import com.modura.app.ui.theme.Gray100
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.Gray800
import com.modura.app.ui.theme.White
import com.russhwolf.settings.Settings

private enum class SearchCategory { CONTENT, PLACE }
typealias PlaceItemData = Map<String, Any>

data class SearchResultScreen(val searchTerm: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalRootNavigator.current!!
        val tabNavigator  = LocalNavigator.currentOrThrow
        val localRepository: LocalRepository = remember { LocalRepositoryImpl(Settings()) }
        var searchValue by remember { mutableStateOf(searchTerm) }

        var contentList by remember { mutableStateOf<List<MediaResponseDto>>(emptyList()) }
        var placeList by remember { mutableStateOf<List<PlaceItemData>>(emptyList()) }

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
                mapOf(
                    "id" to index,
                    "title" to "검색된 장소 $index",
                    "location" to "서울시 강남구 테헤란로",
                    "region" to "강남",
                    "bookmark" to (index % 3 == 0), // 3개 중 1개 꼴로 북마크 활성화
                    "image" to "" // 이미지는 일단 비워둠
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
                placeList =
                    dummyPlaceData.filter {
                        (it["title"] as String).contains(
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
                        } else{
                            tabNavigator.pop()
                        }
                    })
                Spacer(Modifier.height(10.dp))
                Row(    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = { selectedCategory = SearchCategory.CONTENT },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == SearchCategory.CONTENT) White else Color.Transparent,
                            contentColor = if (selectedCategory == SearchCategory.CONTENT) Gray800 else Gray500
                        ),
                        elevation = if (selectedCategory == SearchCategory.CONTENT) ButtonDefaults.buttonElevation(
                            defaultElevation = 2.dp
                        ) else null
                    ) { Text("컨텐츠") }
                    Spacer(Modifier.width(100.dp))
                    Button(
                        onClick = { selectedCategory = SearchCategory.PLACE },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == SearchCategory.PLACE) White else Color.Transparent,
                            contentColor = if (selectedCategory == SearchCategory.PLACE) Gray800 else Gray500
                        ),
                        elevation = if (selectedCategory == SearchCategory.PLACE) ButtonDefaults.buttonElevation(
                            defaultElevation = 2.dp
                        ) else null
                    ) { Text("장소") }
                }
                when (selectedCategory) {
                    SearchCategory.CONTENT -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            items(contentList) { item ->
                                ListContentItem(
                                    bookmark = item.bookmark,
                                    id = item.id,
                                    image = item.image,
                                    title = item.title,
                                    rank = item.rank,
                                    onClick = { clickedId ->
                                        navigator.push(ContentDetailScreen(clickedId))
                                    }
                                )
                            }
                        }
                    }
                    SearchCategory.PLACE -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            items(placeList) { placeItem ->
                                ListLocationItem(
                                    id = placeItem["id"] as Int,
                                    bookmark = placeItem["bookmark"] as Boolean,
                                    image = placeItem["image"] as String,
                                    title = placeItem["title"] as String,
                                    location = placeItem["location"] as String,
                                    region = placeItem["region"] as String,
                                    rank = "",
                                    onClick = { clickedId ->
                                        // 예: navigator.push(LocationDetailScreen(clickedId))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}