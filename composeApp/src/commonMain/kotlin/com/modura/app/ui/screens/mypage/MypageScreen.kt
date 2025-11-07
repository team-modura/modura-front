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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.modura.app.ui.components.ContentGrid
import com.modura.app.ui.components.ContentItemSmall
import com.modura.app.ui.components.LocationItemSmall
import com.modura.app.ui.components.TabItem
import com.modura.app.ui.screens.detail.ContentDetailScreen
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
                    //.clickable { navigator.push(SettingScreen) }
                )
            }
            Spacer(Modifier.height(40.dp))
            Text("${name}님 안녕하세요!", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(horizontal = 20.dp))
            Spacer(Modifier.height(20.dp))

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
                var selectedContentType by remember { mutableStateOf("시리즈") }
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        "시리즈", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedContentType == "시리즈") Black else Gray500,
                        modifier = Modifier.clickable { selectedContentType = "시리즈" }
                    )
                    Spacer(Modifier.width(20.dp))
                    Text(
                        "영화", style = MaterialTheme.typography.titleMedium,
                        color = if (selectedContentType == "영화") Black else Gray500,
                        modifier = Modifier.clickable { selectedContentType = "영화" })
                }
                Spacer(Modifier.height(10.dp))

                val filteredList = when (selectedContentType) {
                    "시리즈" -> mediaList.filter { "netflix" in it.ott }
                    "영화" -> mediaList.filter { "netflix" !in it.ott }
                    else -> mediaList
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredList.size) { index ->
                        val item = filteredList[index]
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
                Text("리뷰")
            }
        }
    }
}





/*
옛날코드
            Column {
                Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text("찜한 컨텐츠", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.weight(1f))
                    Text("전체보기", style = MaterialTheme.typography.bodySmall, modifier = Modifier.clickable { navigator.push(MypageContentScreen)})
                }
                Spacer(Modifier.height(4.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(mediaList.size) { index ->
                        val item = mediaList[index]

                        ContentItemSmall(
                            bookmark = item.bookmark,
                            ott = item.ott,
                            image = item.image,
                            title = item.title,
                            rank = item.rank,
                            onClick = {
                                println("${item.title} 클릭됨")
                                navigator?.push(ContentDetailScreen(title = item.title))                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            Column {
                Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text("찜한 장소", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.weight(1f))
                    Text("전체보기", style = MaterialTheme.typography.bodySmall,  modifier = Modifier.clickable { navigator.push(MypageLocationScreen)})
                }
                Spacer(Modifier.height(4.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(5) { index ->
                        LocationItemSmall(
                            *//* bookmark = item.bookmark,
                            image = item.image,
                            title = item.title,
                            rank = item.rank,*//*
                            onClick = {
                                println("ㅇㅇ 클릭됨")
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            Column(modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)) {
                 Text("명장면 모음", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.clickable { navigator.push(MypageStillcutScreen)})
                Text("리뷰 관리", style = MaterialTheme.typography.bodyMedium,  modifier = Modifier.clickable { navigator.push(MypageReviewScreen)})
                Text("로그아웃", style = MaterialTheme.typography.bodyMedium)
                Text("계정탈퇴", style = MaterialTheme.typography.bodyMedium)
                Text("약관 동의", style = MaterialTheme.typography.bodyMedium)
                Text("설정", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}*/