package com.modura.app.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.modura.app.data.repositoryImpl.LocalRepositoryImpl
import com.modura.app.domain.repository.LocalRepository
import com.modura.app.ui.components.PopularSearchTerm
import com.modura.app.ui.components.RecentSearch
import com.modura.app.ui.components.SearchField
import com.modura.app.ui.screens.login.LoginScreenModel
import com.modura.app.ui.theme.Gray100
import com.modura.app.ui.theme.Gray600
import com.modura.app.ui.theme.Gray700
import com.russhwolf.settings.Settings
import org.jetbrains.compose.ui.tooling.preview.Preview

class SearchScreen : Screen {
    override val key: String = "SearchScreenKey"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val localRepository: LocalRepository = remember { LocalRepositoryImpl(Settings()) }
        var searchValue by remember { mutableStateOf("") }
        val screenModel = getScreenModel<SearchScreenModel>()
        val uiState by screenModel.uiState.collectAsState()
        var recentSearches by remember { mutableStateOf(listOf<String>()) }
        LaunchedEffect(Unit) {
            recentSearches = localRepository.getRecentSearches()
            screenModel.searchPopular()
        }
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
            ) {
                SearchField(
                    value = searchValue,
                    onValueChange = { searchValue = it },
                    onSearch = { searchTerm ->
                        if (searchTerm.isNotBlank()) {
                            localRepository.addSearchTerm(searchTerm)
                            recentSearches = localRepository.getRecentSearches()
                            navigator.push(SearchResultScreen(searchTerm))
                            searchValue = ""
                        }
                    })
                Spacer(Modifier.height(40.dp))
                Text("인기 검색어", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(12.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (uiState.inProgress) {
                        Text("인기 검색어를 불러오는 중...", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
                    }
                    else if (uiState.errorMessage != null) {
                        Text(uiState.errorMessage!!,style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
                    }
                    else {
                        uiState.keywords.forEachIndexed { index, term ->
                            PopularSearchTerm(
                                rank = index + 1,
                                term = term,
                                onClick = {
                                    searchValue = term
                                    navigator.push(SearchResultScreen(searchValue))
                                }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(40.dp))
                Text("최근 검색어", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(12.dp))
                if (recentSearches.isEmpty()) {
                    Text(
                        "최근 검색 기록이 없습니다.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        recentSearches.take(5).forEach { term ->
                            RecentSearch(
                                term = term,
                                onClick = {
                                    searchValue = term
                                    navigator.push(SearchResultScreen(searchValue))
                                },
                                onDelete = {
                                    localRepository.removeSearchTerm(term)
                                    recentSearches = localRepository.getRecentSearches()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}