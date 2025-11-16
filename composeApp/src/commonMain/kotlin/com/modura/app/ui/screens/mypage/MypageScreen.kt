package com.modura.app.ui.screens.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.text
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import coil3.compose.AsyncImage
import com.modura.app.LocalRootNavigator
import com.modura.app.ui.components.ContentGrid
import com.modura.app.ui.components.ListBottomSheet
import com.modura.app.ui.components.MypageReviewContent
import com.modura.app.ui.components.MypageReviewLocation
import com.modura.app.ui.components.TabItem
import com.modura.app.ui.screens.detail.ContentDetailScreen
import com.modura.app.ui.screens.detail.DetailScreenModel
import com.modura.app.ui.screens.detail.PlaceDetailScreen
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
        val screenModel = getScreenModel<MypageScreenModel>()
        val detailScreenModel = getScreenModel<DetailScreenModel>()
        val uiState by screenModel.uiState.collectAsState()
        val likedSeries by screenModel.likedSeries.collectAsState()
        val likedMovies by screenModel.likedMovies.collectAsState()
        val likedPlaces by screenModel.likedPlaces.collectAsState()
        val stillcuts by screenModel.stillcuts.collectAsState()

        var showBottomSheet by remember { mutableStateOf(false) }
        var selectedReview by remember { mutableStateOf<MypageReview?>(null) }
        val bottomSheetItems = listOf("상세보기", /*"수정",*/ "삭제")


        var selectedTab by remember { mutableStateOf("찜") }

        LaunchedEffect(selectedTab) {
            if (selectedTab == "찜") {
                screenModel.getLikedContents("series")
                screenModel.getLikedContents("movie")
                screenModel.getLikedPlaces()
            } else if (selectedTab == "스틸컷") {
                screenModel.getStillcuts()
            }
        }

        if (showBottomSheet && selectedReview != null) {
            ListBottomSheet(
                items = bottomSheetItems,
                onDismissRequest = { showBottomSheet = false },
                onSelect = { selectedOption ->
                    when (selectedOption) {
                        "상세보기" -> {
                            if (selectedReview!!.type == "place") {
                                navigator.push(PlaceDetailScreen(selectedReview!!.placeId?:0))
                                println("장소 상세보기: ${selectedReview!!.title}")
                            } else {
                                navigator.push(ContentDetailScreen(selectedReview!!.contentId?:0))
                            }
                        }
                        "수정" -> { /* TODO: 수정 로직 */ println("수정: ${selectedReview!!.title}") }
                        "삭제" ->
                            //삭제 후 새로고침 이랑 Toast
                            if( selectedReview!!.type == "place"){
                                detailScreenModel.reviewDelete("place",selectedReview!!.placeId?:0,selectedReview!!.id)
                            }else{
                                detailScreenModel.reviewDelete("content",selectedReview!!.contentId?:0,selectedReview!!.id)
                            }
                    }
                    showBottomSheet = false
                }
            )
        }

        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            val name: String? = "이용자"
            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.img_logo_text),
                    contentDescription = "로고",
                    modifier = Modifier.height(15.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground )
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    painter = painterResource(Res.drawable.ic_setting),
                    contentDescription = "설정",
                    modifier = Modifier.size(20.dp)
                    .clickable { navigator.push(MypageSettingScreen()) },
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(Modifier.height(20.dp))
            Text("${name}님 안녕하세요!", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(horizontal = 20.dp), color = MaterialTheme.colorScheme.onBackground)

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
                        color = if (selectedType == "시리즈") MaterialTheme.colorScheme.onBackground else Gray500,
                        modifier = Modifier.clickable { selectedType = "시리즈" })
                    Text(
                        "영화", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedType == "영화") MaterialTheme.colorScheme.onBackground else Gray500,
                        modifier = Modifier.clickable { selectedType = "영화" })
                    Text(
                        "장소", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedType == "장소") MaterialTheme.colorScheme.onBackground else Gray500,
                        modifier = Modifier.clickable { selectedType = "장소" })
                }
                Spacer(Modifier.height(10.dp))

                if(uiState.inProgress){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        CircularProgressIndicator()
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        when (selectedType) {
                            "시리즈" -> {
                                items(likedSeries, key = { it.id }) { item ->
                                    ContentGrid(
                                        image = item.thumbnail?:"",
                                        title = item.title,
                                        onClick = { navigator.push(ContentDetailScreen(id = item.id)) }
                                    )
                                }
                            }
                            "영화" -> {
                                items(likedMovies, key = { it.id }) { item ->
                                    ContentGrid(
                                        image = item.thumbnail?:"",
                                        title = item.title,
                                        onClick = { navigator.push(ContentDetailScreen(id = item.id)) }
                                    )
                                }
                            }
                            "장소" -> {
                                items(likedPlaces, key = { it.id }) { item ->
                                    ContentGrid(
                                        image = item.thumbnail?:"",
                                        title = item.name,
                                        onClick = { navigator.push(PlaceDetailScreen(item.id)) }
                                    )
                                }
                            }
                        }
                    }
                }
                /*LazyVerticalGrid(
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
                                    navigator.push(ContentDetailScreen(id = item.id))
                                }
                            )
                        }
                    }
                }*/
            }else if(selectedTab == "스틸컷") {
                if(uiState.inProgress){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        CircularProgressIndicator()
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(stillcuts, key = { it.id }) { stillcut ->
                            AsyncImage(
                                model = stillcut.imageUrl,
                                contentDescription = "스틸컷 이미지 ${stillcut.id}",
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clickable {
                                        println("스틸컷 ${stillcut.id} 클릭됨")
                                    },
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
            else if (selectedTab == "리뷰") {
                val reviews by screenModel.reviews.collectAsState()
                var selectedReviewType by remember { mutableStateOf("시리즈") }
                LaunchedEffect(selectedReviewType) {
                    screenModel.clearReviews()
                    when (selectedReviewType) {
                        "시리즈" -> screenModel.getContentReviewsMypage("series")
                        "영화" -> screenModel.getContentReviewsMypage("movie")
                        "장소" -> screenModel.getPlaceReviewsMypage("place")
                    }
                }
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.padding(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)){
                    Text(
                        "시리즈", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedReviewType == "시리즈") MaterialTheme.colorScheme.onBackground else Gray500,
                        modifier = Modifier.clickable { selectedReviewType = "시리즈" }
                    )
                    Text(
                        "영화", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedReviewType == "영화") MaterialTheme.colorScheme.onBackground else Gray500,
                        modifier = Modifier.clickable { selectedReviewType = "영화" })
                    Text(
                        "장소", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedReviewType == "장소") MaterialTheme.colorScheme.onBackground else Gray500,
                        modifier = Modifier.clickable { selectedReviewType = "장소" })
                }
                Spacer(Modifier.height(10.dp))
                if (uiState.inProgress) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(reviews, key = { "${it.type}-${it.id}" }) { review ->
                            if (review.type == "place") {
                                println(review.imageUrl)
                                MypageReviewLocation(
                                    thumbnail = review.thumbnail?:"",
                                    id = review.id,
                                    placeId = review.placeId?:0,
                                    title = review.title,
                                    name = review.username,
                                    score = review.rating,
                                    date = review.createdAt,
                                    text = review.comment,
                                    image = review.imageUrl,
                                    onClick = {
                                        selectedReview = review
                                        showBottomSheet = true
                                        println("장소 리뷰 ${review.id} 클릭")
                                    }
                                )
                            } else {
                                MypageReviewContent(
                                    thumbnail = review.thumbnail?:"",
                                    id = review.id,
                                    contentId = review.contentId?:0,
                                    title = review.title,
                                    name = review.username,
                                    score = review.rating,
                                    date = review.createdAt,
                                    text = review.comment,
                                    onClick = {
                                        selectedReview = review
                                        showBottomSheet = true
                                        println("콘텐츠 리뷰 ${review.id} 클릭")
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