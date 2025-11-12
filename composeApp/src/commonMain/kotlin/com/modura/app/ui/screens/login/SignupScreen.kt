package com.modura.app.ui.screens.login

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.forEach
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.modura.app.LocalRootNavigator
import com.modura.app.data.dev.Category
import com.modura.app.data.dev.City
import com.modura.app.data.dev.DummyProvider.categories
import com.modura.app.data.dev.States
import com.modura.app.ui.components.AddressBottomSheet
import com.modura.app.ui.components.CategoryChip
import com.modura.app.ui.components.LoginBottomSheet
import com.modura.app.ui.components.MiddleButton
import com.modura.app.ui.components.TextField
import com.modura.app.ui.screens.home.HomeScreen
import com.modura.app.ui.screens.main.MainScreen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_file
import modura.composeapp.generated.resources.img_flicker_1
import modura.composeapp.generated.resources.img_flicker_2
import modura.composeapp.generated.resources.img_kakao_login
import modura.composeapp.generated.resources.img_logo_text
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

class SignupScreen() : Screen {
    override val key: String = "SignupScreen"

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<LoginScreenModel>()
        val uiState by screenModel.uiState.collectAsState()
        val rootNavigator = LocalRootNavigator.current

        var selectedState by remember { mutableStateOf<States?>(null) }
        var selectedCity by remember { mutableStateOf<City?>(null) }
        var showAddressSheet by remember { mutableStateOf(false) }
        var selectedCategories by remember { mutableStateOf<Set<Category>>(emptySet()) }

        val isButtonEnabled = selectedState != null && selectedCity != null && selectedCategories.size >= 3

        if (showAddressSheet) {
            AddressBottomSheet(
                onDismissRequest = { showAddressSheet = false },
                onAddressSelected = { state, city ->
                    selectedState = state
                    selectedCity = city
                    showAddressSheet = false
                }
            )
        }
        if (uiState.success) {
                rootNavigator?.push(MainScreen)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    "인생작과 내 주변 명소를\n발견하도록, 지금 알려주세요.",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(top = 90.dp, bottom = 20.dp)
                )
                val addressText = if (selectedState != null && selectedCity != null) {
                    "${selectedState!!.name} ${selectedCity!!.name}"
                } else {
                    ""
                }
                TextField(
                    value = addressText,
                    onValueChange = {},
                    title = "거주지",
                    placeholder = "거주지를 선택해주세요",
                    enabled = false,
                    onBodyClick = {
                        showAddressSheet = true
                    }
                )
                Spacer(Modifier.height(40.dp))
                Text("좋아하는 주제 (최소 3개 선택 가능)", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(16.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp), // 칩 사이의 가로 간격
                    verticalArrangement = Arrangement.spacedBy(8.dp)  // 칩 사이의 세로 간격
                ) {
                    categories.forEach { category ->
                        val isSelected = selectedCategories.contains(category)
                        CategoryChip(
                            category = category,
                            isSelected = isSelected, // isSelected 파라미터를 받는 CategoryChip 필요
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null, // 클릭 효과 제거
                                onClick = {
                                    // Set의 특징을 이용한 다중 선택 로직
                                    selectedCategories = if (isSelected) {
                                        selectedCategories - category // 이미 있으면 제거
                                    } else {
                                        selectedCategories + category // 없으면 추가
                                    }
                                }
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                MiddleButton(
                    text = "시작하기",
                    onClick = {
                        val address = "${selectedState?.name} ${selectedCity?.name}"
                        val categoryIds = selectedCategories.map { it.id }
                        screenModel.user(address = address, categoryList = categoryIds)
                    },
                    enabled = isButtonEnabled && !uiState.inProgress
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}