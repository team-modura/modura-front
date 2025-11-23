package com.modura.app.ui.screens.detail

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import coil3.compose.AsyncImage
import com.modura.app.LocalRootNavigator
import com.modura.app.ui.screens.mypage.MypageScreenModel
import com.modura.app.ui.theme.Black
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.Gray900
import com.modura.app.ui.theme.White
import com.modura.app.util.extension.shimmerEffect

class StillcutDetailScreen(private val startStillcutId: Int) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalRootNavigator.current!!
        val screenModel = getScreenModel<MypageScreenModel>()

        LaunchedEffect(Unit) {
            screenModel.getStillcuts()
        }

        val stillcutList by screenModel.stillcuts.collectAsState()
        val currentStillcutDetail by screenModel.stillcutDetail.collectAsState()

        var cachedStillcut by remember { mutableStateOf(currentStillcutDetail) }

        if (currentStillcutDetail != null) {
            cachedStillcut = currentStillcutDetail
        }

        var currentIndex by remember { mutableStateOf(0) }

        LaunchedEffect(stillcutList) {
            if (stillcutList.isNotEmpty()) {
                val index = stillcutList.indexOfFirst { it.id == startStillcutId }
                currentIndex = if (index != -1) index else 0
                screenModel.getStillcutDetail(stillcutList[currentIndex].id)
            }
        }

        LaunchedEffect(currentIndex) {
            if (stillcutList.isNotEmpty() && currentIndex in stillcutList.indices) {
                screenModel.getStillcutDetail(stillcutList[currentIndex].id)
            }
        }

        val progress = remember { Animatable(0f) }

        LaunchedEffect(currentStillcutDetail) {
            val currentId = stillcutList.getOrNull(currentIndex)?.id

            if (currentStillcutDetail != null && currentStillcutDetail?.id == currentId) {
                progress.snapTo(0f)
                progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 10000, easing = LinearEasing)
                )

                if (currentIndex < stillcutList.lastIndex) {
                    currentIndex++
                } else {
                    navigator.pop()
                }
            }
        }
        if (stillcutList.isEmpty()) {
            Box(
                modifier = Modifier.Companion.fillMaxSize().background(Color.Companion.Black),
                contentAlignment = Alignment.Companion.Center
            ) {
                CircularProgressIndicator(color = White)
            }
            return
        }

        val displayStillcut = currentStillcutDetail ?: cachedStillcut

        if (displayStillcut == null) {
            Box(
                modifier = Modifier.Companion.fillMaxSize().background(Color.Companion.Black),
                contentAlignment = Alignment.Companion.Center
            ) {
                CircularProgressIndicator(color = White)
            }
            return
        }

        val isLoading =
            currentStillcutDetail == null || currentStillcutDetail?.id != stillcutList.getOrNull(
                currentIndex
            )?.id


        Box(modifier = Modifier.Companion.fillMaxSize()) {
            AsyncImage(
                model = displayStillcut.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Companion.Crop,
                modifier = Modifier.Companion.fillMaxSize()
            )

            Box(
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .background(
                        Brush.Companion.verticalGradient(
                            colors = listOf(
                                Color.Companion.Transparent,
                                Color.Companion.Black.copy(alpha = 0.8f)
                            ),
                            startY = 500f
                        )
                    )
            )

            Row(modifier = Modifier.Companion.fillMaxSize()) {
                Box(
                    modifier = Modifier.Companion
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (currentIndex > 0) {
                                currentIndex--
                            }
                        }
                )
                Box(
                    modifier = Modifier.Companion
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (currentIndex < stillcutList.lastIndex) {
                                currentIndex++
                            } else {
                                navigator.pop()
                            }
                        }
                )
            }
            Row(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                stillcutList.forEachIndexed { index, _ ->
                    LinearProgressIndicator(
                        progress = {
                            when {
                                index < currentIndex -> 1f
                                index == currentIndex -> progress.value
                                else -> 0f
                            }
                        },
                        modifier = Modifier.Companion
                            .weight(1f)
                            .height(2.dp)
                            .clip(MaterialTheme.shapes.small),
                        color = White,
                        trackColor = White.copy(alpha = 0.3f),
                    )
                }
            }
            Column(
                modifier = Modifier.Companion
                    .align(Alignment.Companion.BottomStart)
                    .padding(20.dp)
                    .padding(bottom = 40.dp)
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.Companion
                            .size(40.dp)
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "${displayStillcut.similarity}%",
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text("유사도", style = MaterialTheme.typography.labelSmall, color = Gray900)
                    }
                }
                Spacer(modifier = Modifier.Companion.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (isLoading) {
                        Box(
                            modifier = Modifier.Companion
                                .size(40.dp)
                                .clip(CircleShape)
                                .shimmerEffect()
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "${displayStillcut.angle}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Black
                            )
                            Text("구도", style = MaterialTheme.typography.labelSmall, color = Gray900)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "${displayStillcut.clarity}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Black
                            )
                            Text(
                                "선명도",
                                style = MaterialTheme.typography.labelSmall,
                                color = Gray900
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "${displayStillcut.color}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Black
                            )
                            Text("색감", style = MaterialTheme.typography.labelSmall, color = Gray900)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "${displayStillcut.palette}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Black
                            )
                            Text(
                                "색 구성",
                                style = MaterialTheme.typography.labelSmall,
                                color = Gray900
                            )
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
                Row(verticalAlignment = Alignment.Companion.CenterVertically) {
                    if (isLoading) {
                        Box(
                            modifier = Modifier.Companion
                                .size(40.dp)
                                .clip(CircleShape)
                                .shimmerEffect()
                        )
                    } else {
                        AsyncImage(
                            model = displayStillcut.stillcut,
                            contentDescription = null,
                            contentScale = ContentScale.Companion.Crop,
                            modifier = Modifier.Companion
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Gray500)
                        )
                    }

                    Spacer(modifier = Modifier.Companion.width(12.dp))

                    Column {
                        if (isLoading) {
                            Box(
                                modifier = Modifier.Companion
                                    .height(20.dp)
                                    .width(120.dp)
                                    .clip(MaterialTheme.shapes.small)
                                    .shimmerEffect()
                            )
                            Spacer(modifier = Modifier.Companion.height(4.dp))
                            Box(
                                modifier = Modifier.Companion
                                    .height(14.dp)
                                    .width(80.dp)
                                    .clip(MaterialTheme.shapes.small)
                                    .shimmerEffect()
                            )
                        } else {
                            Text(
                                text = displayStillcut.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = White,
                                fontWeight = FontWeight.Companion.Bold
                            )
                            Text(
                                text = displayStillcut.name,
                                style = MaterialTheme.typography.bodySmall,
                                color = White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.Companion.height(8.dp))

                if (isLoading) {
                    Box(
                        modifier = Modifier.Companion
                            .height(12.dp)
                            .width(60.dp)
                            .clip(MaterialTheme.shapes.small)
                            .shimmerEffect()
                    )
                } else {
                    Text(
                        text = displayStillcut.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = Gray500,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}