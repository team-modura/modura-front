package com.modura.app.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.modura.app.model.WelfareBenefit
import com.modura.app.ui.components.CommonSearch
import com.modura.app.ui.components.WelfareListItem
import com.modura.app.ui.theme.Black
import com.modura.app.ui.theme.Gray100
import com.modura.app.ui.theme.Green700
import com.modura.app.ui.theme.White
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.ic_alarm
import modura.composeapp.generated.resources.ic_chevron_right_1dp
import modura.composeapp.generated.resources.img_diagnosis
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime

object HomeScreen : Screen {
    override val key: String = "HomeScreenKey"
    @OptIn(ExperimentalTime::class)
    @Composable
    override fun Content() {
        var searchText by remember { mutableStateOf("") }
        val scrollState = rememberScrollState()

        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val sampleList = listOf(
            WelfareBenefit(
                "1",
                "서울",
                LocalDate(today.year, 12, 31),
                "청년 월세 지원 사업",
                "최대 20만원",
                "만 19세 ~ 39세 무주택 청년"
            ),
            WelfareBenefit(
                "2",
                "경기",
                today.plus(30, DateTimeUnit.DAY),
                "청년 면접수당",
                "최대 50만원",
                "만 18세 ~ 39세 미취업 청년"
            ),
            WelfareBenefit(
                "3",
                "전국",
                today.plus(7, DateTimeUnit.DAY),
                "국민취업지원제도",
                "월 50만원 x 6개월",
                "만 15세 ~ 69세 구직자"
            )
        )

        Box(
            modifier = Modifier
                .background(Gray100)
        ) {
            Column(modifier = Modifier .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp)
            ) {
                Row(){
                    Spacer(Modifier.weight(1f))
                    Icon(
                        painter = painterResource(Res.drawable.ic_alarm),
                        tint = Green700,
                        contentDescription = "알림",
                        modifier = Modifier
                            .clickable {
                                println("알림 아이콘 클릭됨")
                            }
                    )
                }
                Spacer(Modifier.height(20.dp))
                CommonSearch(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                    }
                )
                Spacer(Modifier.height(20.dp))
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    painter = painterResource(Res.drawable.img_diagnosis),
                    contentDescription = "30초 만에 진단",
                    contentScale = ContentScale.Fit
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "000님을 위한 추천 혜택",
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(Modifier.height(10.dp))
                sampleList.forEachIndexed { index, benefitItem ->
                    WelfareListItem(
                        item = benefitItem,
                        onItemClick = {
                            println("${it.name} 클릭됨")
                        }
                    )
                    if (index < sampleList.lastIndex) {
                        Spacer(Modifier.height(10.dp))
                    }
                }
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "NEW 혜택",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(Modifier.weight(1f))
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text(
                            text = "전체보기",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Spacer(Modifier.width(5.dp))
                        Icon(
                            painter = painterResource(Res.drawable.ic_chevron_right_1dp),
                            tint = Black,
                            contentDescription = "전체보기"
                        )
                    }
                }
                Spacer(Modifier.height(5.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(3) { index ->
                        val imageRes = when (index) {
                            0 -> Res.drawable.img_diagnosis
                            1 -> Res.drawable.img_diagnosis
                            else -> Res.drawable.img_diagnosis
                        }

                        Image(
                            painter = painterResource(imageRes),
                            contentDescription = "New 혜택 ${index + 1}",
                            modifier = Modifier
                                .width(200.dp)
                                .height(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    println("New 혜택 ${index + 1} 클릭됨")
                                },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White, RoundedCornerShape(8.dp))
                ){
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "내 주변 복지 시설 찾아보기",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(Modifier.height(50.dp))
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen.Content()
}