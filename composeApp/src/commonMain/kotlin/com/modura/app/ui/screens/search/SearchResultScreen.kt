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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
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
import com.modura.app.ui.screens.detail.DetailScreenModel
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
        val screenModel = getScreenModel<SearchScreenModel>()
        val detailScreenModel = getScreenModel<DetailScreenModel>()
        val uiState by screenModel.uiState.collectAsState()

        val navigator = LocalRootNavigator.current!!
        val tabNavigator = LocalNavigator.currentOrThrow
        val localRepository: LocalRepository = remember { LocalRepositoryImpl(Settings()) }

        var searchValue by remember { mutableStateOf(searchTerm) }
        var selectedCategory by remember { mutableStateOf(SearchCategory.CONTENT) }

        LaunchedEffect(searchValue) {
            if (searchValue.isNotBlank()) {
                screenModel.searchContents(searchValue)
                screenModel.searchPlaces(searchValue)
            } else {
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
                    modifier = Modifier.padding(horizontal = 20.dp),
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
                if (uiState.inProgress) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    if (selectedCategory == SearchCategory.CONTENT) {
                        if (uiState.contents.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("컨텐츠 검색 결과가 없습니다.")
                            }
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(vertical = 20.dp)
                            ) {
                                items(uiState.contents, key = { it.id }) { item ->
                                    SearchContentGrid(
                                        id = item.id,
                                        image = item.thumbnail ?: "",
                                        isLiked = item.isLiked,
                                        onClick = { navigator.push(ContentDetailScreen(item.id)) },
                                        onLikeClick = {contentId, isLiked ->
                                            if(isLiked){
                                                detailScreenModel.contentLikeCancel(contentId)
                                            }else{
                                                detailScreenModel.contentLike(contentId)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    } else {
                        if (uiState.places.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("장소 검색 결과가 없습니다.")
                            }
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(vertical = 20.dp)
                            ) {
                                items(uiState.places, key = { it.id }) { item ->
                                    SearchPlaceGrid(
                                        id = item.id,
                                        image = item.thumbnail ?: "",
                                        isLiked = item.isLiked,
                                        onClick = { navigator.push(LocationDetailScreen(item.id)) },
                                        onLikeClick = {placeId, isLiked ->
                                            if(isLiked){
                                                detailScreenModel.placeLikeCancel(placeId)
                                            }else{
                                                detailScreenModel.placeLike(placeId)
                                            }
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
}