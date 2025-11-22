package com.modura.app.ui.screens.mypage

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.copy
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import coil3.compose.AsyncImage
import com.modura.app.LocalRootNavigator
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.White


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
        if (stillcutList.isEmpty() || currentStillcutDetail == null) {
            Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = White)
            }
            return
        }
        val stillcut = currentStillcutDetail!!

        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = stillcut.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                            startY = 500f
                        )
                    )
            )

            Row(
                modifier = Modifier
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
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp)
                            .clip(MaterialTheme.shapes.small),
                        color = White,
                        trackColor = White.copy(alpha = 0.3f),
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
                    .padding(bottom = 40.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = stillcut.stillcut,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Gray500)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = stillcut.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stillcut.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = White.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stillcut.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = Gray500,
                    fontSize = 12.sp
                )
            }
        }
    }
}