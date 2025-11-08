package com.modura.app.ui.screens.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.modura.app.LocalRootNavigator
import com.modura.app.data.dev.DummyProvider
import com.modura.app.data.dev.PlaceInfo
import com.modura.app.data.dto.response.list.MediaResponseDto
import com.modura.app.ui.components.ContentGrid
import com.modura.app.ui.components.ContentItemSmall
import com.modura.app.ui.components.LocationItemSmall
import com.modura.app.ui.components.MypageReviewContent
import com.modura.app.ui.components.MypageReviewLocation
import com.modura.app.ui.components.PlaceGrid
import com.modura.app.ui.components.TabItem
import com.modura.app.ui.screens.detail.ContentDetailScreen
import com.modura.app.ui.screens.detail.LocationDetailScreen
import com.modura.app.ui.theme.Black
import com.modura.app.ui.theme.Gray100
import com.modura.app.ui.theme.Gray500
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource

object MyPageScreen : Screen {
    override val key: String = "MypageScreenKey"

    @Composable
    override fun Content() {
        val navigator = LocalRootNavigator.current!!
        val mediaList = DummyProvider.dummyMedia
        val placeList = DummyProvider.dummyPlaces

        Column(
            modifier = Modifier.background(Gray100)
        ) {
            val name: String? = "김승혁"
            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.img_logo_text),
                    contentDescription = "로고",
                    modifier = Modifier.height(15.dp)
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    painter = painterResource(Res.drawable.ic_setting),
                    contentDescription = "설정",
                    modifier = Modifier.size(20.dp)
                    .clickable { navigator.push(MypageSettingScreen) }
                )
            }
            Spacer(Modifier.height(20.dp))
            Text("${name}님 안녕하세요!", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(horizontal = 20.dp))

            var selectedTab by remember { mutableStateOf("찜") }

            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    TabItem(
                        text = "찜",
                        isSelected = selectedTab == "찜",
                        onClick = { selectedTab = "찜" }
                    )
                    TabItem(
                        text = "스틸컷",
                        isSelected = selectedTab == "스틸컷",
                        onClick = { selectedTab = "스틸컷" }
                    )
                    TabItem(
                        text = "리뷰",
                        isSelected = selectedTab == "리뷰",
                        onClick = { selectedTab = "리뷰" }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(Gray500)
                )
            }
            if (selectedTab == "찜") {
                var selectedType by remember { mutableStateOf("시리즈") }
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        "시리즈", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedType == "시리즈") Black else Gray500,
                        modifier = Modifier.clickable { selectedType = "시리즈" })
                    Text(
                        "영화", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedType == "영화") Black else Gray500,
                        modifier = Modifier.clickable { selectedType = "영화" })
                    Text(
                        "장소", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedType == "장소") Black else Gray500,
                        modifier = Modifier.clickable { selectedType = "장소" })
                }
                Spacer(Modifier.height(10.dp))

                val filteredList = when (selectedType) {
                    "시리즈" -> mediaList.filter { "netflix" in it.ott }
                    "영화" -> mediaList.filter { "netflix" !in it.ott }
                    "장소" -> placeList
                    else -> emptyList()
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (selectedType == "장소") {
                        items(filteredList.size) { index ->
                            val item = filteredList[index] as PlaceInfo
                            PlaceGrid(
                                image = item.photoUrl?: "",
                                title = item.name,
                                onClick = {
                                    println("${item.name} 클릭됨")
                                    navigator.push(LocationDetailScreen(item.id))
                                }
                            )
                        }
                    } else {
                        items(filteredList.size) { index ->
                            val item = filteredList[index] as MediaResponseDto
                            ContentGrid(
                                image = item.image,
                                title = item.title,
                                onClick = {
                                    println("${item.title} 클릭됨")
                                    navigator.push(ContentDetailScreen(title = item.title))
                                }
                            )
                        }
                    }
                }
            }else if(selectedTab == "스틸컷") {
                val stillCutList = DummyProvider.dummyStillCuts

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                ) {
                    items(stillCutList.size) { index ->
                        val stillCutImage = stillCutList[index]
                        Image(
                            painter = painterResource(stillCutImage),
                            contentDescription = "스틸컷 이미지 $index",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clickable {
                                    println("스틸컷 $index 클릭됨")
                                },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            else if (selectedTab == "리뷰") {
                var selectedReviewType by remember { mutableStateOf("전체") }
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.padding(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)){
                    Text(
                        "전체", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedReviewType == "전체") Black else Gray500,
                        modifier = Modifier.clickable { selectedReviewType = "전체" }
                    )
                    Text(
                        "시리즈", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedReviewType == "시리즈") Black else Gray500,
                        modifier = Modifier.clickable { selectedReviewType = "시리즈" }
                    )
                    Text(
                        "영화", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedReviewType == "영화") Black else Gray500,
                        modifier = Modifier.clickable { selectedReviewType = "영화" })
                    Text(
                        "장소", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedReviewType == "장소") Black else Gray500,
                        modifier = Modifier.clickable { selectedReviewType = "장소" })
                }
                Spacer(Modifier.height(10.dp))

                val reviewList = DummyProvider.dummyReviews
                val filteredReviews = when (selectedReviewType) {
                    "전체" -> reviewList
                    "시리즈" -> reviewList.filter { it.type == "시리즈" }
                    "영화" -> reviewList.filter { it.type == "영화" }
                    "장소" -> reviewList.filter { it.type == "장소" }
                    else -> emptyList()
                }
            }
        }
    }
}