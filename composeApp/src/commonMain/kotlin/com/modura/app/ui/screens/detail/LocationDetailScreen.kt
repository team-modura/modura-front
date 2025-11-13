package com.modura.app.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.modura.app.LocalRootNavigator
import com.modura.app.data.dev.DummyProvider
import com.modura.app.ui.components.*
import com.modura.app.ui.screens.camera.StillcutScreen
import com.modura.app.ui.theme.*
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource


data class LocationDetailScreen(val id: Int?=1) : Screen {
    override val key: String = "ContentDetailScreen_$id"

    @Composable
    override fun Content() {

        val place = remember(id) { DummyProvider.dummyPlaces.find { it.id == id } }

        val rootNavigator = LocalRootNavigator.current
        val screenModel: DetailScreenModel = getScreenModel()
        val uiState by screenModel.youtubeUiState

        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Gray100)
        ) {
            if (place != null) { //추후 삭제
                item {
                    Column(modifier = Modifier.fillMaxWidth().padding(top = 60.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)) {
                        Row {
                            Icon(
                                painter = painterResource(Res.drawable.ic_back),
                                contentDescription = "뒤로가기",
                                modifier = Modifier.size(24.dp)
                                    .clickable {
                                        rootNavigator?.pop()
                                    }
                            )
                            Spacer(Modifier.weight(1f))
                            Icon(
                                painter = painterResource(Res.drawable.ic_camera),
                                contentDescription = "카메라",
                                modifier = Modifier.size(24.dp).clickable {
                                    rootNavigator?.push(StillcutScreen())
                                }
                            )
                        }
                        Spacer(Modifier.height(20.dp))
                        Text(place.name, style = MaterialTheme.typography.headlineLarge)
                        Spacer(Modifier.height(4.dp))
                        Row {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row {
                                    for (i in 1..5) {
                                        val fraction = when {
                                            place.rating >= i -> 1f
                                            place.rating > i - 1 -> place.rating - (i - 1)
                                            else -> 0f
                                        }
                                        ReviewStar(fraction.toFloat())
                                    }
                                }
                            }
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "(${place.reviewCount.toString()})",
                                style = MaterialTheme.typography.light8
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(place.address, style = MaterialTheme.typography.labelSmall)
                        Spacer(Modifier.height(16.dp))
                        Image(
                            painter = painterResource(Res.drawable.img_example),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .shadow(
                                    elevation = 2.dp,
                                    shape = RoundedCornerShape(8.dp),
                                    clip = false
                                )
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = 0.5.dp,
                                    color = Color.White.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                item {
                    Column {
                        Spacer(Modifier.height(20.dp))
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            text = "${place.name}에서 촬영한 콘텐츠",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Spacer(Modifier.height(4.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 20.dp)
                        ) {
                            items(5) { index ->
                                LocationItemSmall(
                                    /*bookmark = item.bookmark,
                                image = item.image,
                                title = item.title,
                                rank = item.rank,*/
                                    onClick = {
                                        println("ㅇㅇ 클릭됨")
                                    }
                                )
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                    }
                }
                item {
                    Column(
                        Modifier.background(White).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            "방문 후기를 남겨주세요!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Gray700,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                        var userRating by remember { mutableStateOf(0) }
                        ReviewStarInput(
                            rating = userRating,
                            onRatingChange = { newRating ->
                                userRating = newRating
                                rootNavigator?.push(ReviewScreen(1,"장소",place.name,newRating))
                            }
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                }
                item {
                    Spacer(Modifier.height(4.dp))
                }
                item {
                    Column(
                        modifier = Modifier.background(White),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ReviewLocationList(
                            "김승혁",
                            4.5f,
                            "2023.07.15",
                            "아이들에 대한 묘사가 너무 절묘하다. 유난히 똑똑한 아이들임을 보여주면서도 순진함 속에 그들은 그들만의 규칙이 있다. 어리다고 미성숙하게만 그리는 게 아니라 하나의 인격체로서 다룸. 일레븐한테 가발 씌우는 의미 불명의 행태를 빼면 굉장히 재밌게 봤다. 시즌 2가 나와주길 기대."
                        )
                        ReviewLocationList(
                            "김승혁",
                            3.5f,
                            "2004.06.11",
                            "스토리, 음악, 캐릭터까지 '슈퍼 에이트'보다 더 7080스럽고 사랑스럽지만 동시에 중독성 강한 SF호러. 아동, 하이틴, 미스터리를 모두 아름답게 조화시키며 깜찍함과 공포를 둘 다 느낄 수 있는 러브레터 이상의 수작."
                        )
                    }
                }
            }
        }
    }
}
