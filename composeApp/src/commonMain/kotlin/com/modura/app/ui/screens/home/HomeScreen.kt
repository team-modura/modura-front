package com.modura.app.ui.screens.home

import androidx.compose.animation.core.copy
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.modura.app.LocalRootNavigator
import com.modura.app.data.dev.DummyProvider
import com.modura.app.ui.components.ContentItemSmall
import com.modura.app.ui.components.LocationItemSmall
import com.modura.app.ui.screens.detail.ContentDetailScreen
import com.modura.app.ui.screens.main.MainScreen
import com.modura.app.ui.theme.*
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime

object HomeScreen : Screen {
    override val key: String = "HomeScreenKey"
    @OptIn(ExperimentalTime::class)
    @Composable
    override fun Content() {
        val navigator = LocalRootNavigator.current!!
        val scrollState = rememberScrollState()
        val mediaList = DummyProvider.dummyMedia

        Box(
            modifier = Modifier
                .background(Gray100)
        ) {
            Column(modifier = Modifier .fillMaxSize()
                .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = painterResource(Res.drawable.img_logo_text),
                        contentDescription = "로고",
                        modifier = Modifier.height(15.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        painter = painterResource(Res.drawable.ic_search),
                        contentDescription = "검색",
                        modifier = Modifier
                            .clickable {
                                println("검색 아이콘 클릭됨")
                            }
                    )
                }
                Spacer(Modifier.height(20.dp))
                Image(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .aspectRatio(16f/9f)
                        .shadow(elevation =2.dp, shape = RoundedCornerShape(8.dp), clip = false)
                        .clip(RoundedCornerShape(8.dp))
                        .border(width = 0.5.dp, color = Color.White.copy(alpha = 0.3f), shape = RoundedCornerShape(8.dp)),
                    painter = painterResource(Res.drawable.img_diagnosis),
                    contentDescription = "30초 만에 진단",
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    text = "TOP 10 Series",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(Modifier.height(5.dp))
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
                Spacer(Modifier.height(20.dp))
                Text(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    text = "TOP 10 촬영지",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(Modifier.height(5.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(5) { index ->

                        LocationItemSmall(
/*                            bookmark = item.bookmark,
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
                Text(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    text = "힐링이 필요할 때",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(Modifier.height(5.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
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
                            }
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen.Content()
}