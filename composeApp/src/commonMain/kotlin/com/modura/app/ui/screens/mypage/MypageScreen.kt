package com.modura.app.ui.screens.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.modura.app.LocalRootNavigator
import com.modura.app.data.dev.DummyProvider
import com.modura.app.ui.components.ContentItemSmall
import com.modura.app.ui.components.LocationItemSmall
import com.modura.app.ui.screens.detail.ContentDetailScreen
import com.modura.app.ui.theme.Gray100

object MyPageScreen : Screen {
    override val key: String = "MypageScreenKey"

    @Composable
    override fun Content() {
        val navigator = LocalRootNavigator.current!!
        val mediaList = DummyProvider.dummyMedia

        Column(
            modifier = Modifier.background(Gray100)
        ) {
            val name: String ?= "김승혁"
            Column(modifier = Modifier.padding(horizontal = 20.dp)){
                Spacer(Modifier.height(20.dp))
                Text("마이페이지", style = MaterialTheme.typography.headlineLarge)
                Spacer(Modifier.height(30.dp))
                Text("${name}님 안녕하세요!", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(30.dp))
            }
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
                            /* bookmark = item.bookmark,
                            image = item.image,
                            title = item.title,
                            rank = item.rank,*/
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
}