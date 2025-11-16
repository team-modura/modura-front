package com.modura.app.ui.screens.home

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.modura.app.LocalRootNavigator
import com.modura.app.data.dev.DummyProvider
import com.modura.app.ui.components.ContentItemSmall
import com.modura.app.ui.components.HomeContentRow
import com.modura.app.ui.components.HomePlaceRow
import com.modura.app.ui.components.LocationItemSmall
import com.modura.app.ui.components.SectionTitle
import com.modura.app.ui.screens.detail.ContentDetailScreen
import com.modura.app.ui.theme.Gray100
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_diagnosis
import modura.composeapp.generated.resources.img_logo_text
import org.jetbrains.compose.resources.painterResource
import kotlin.time.ExperimentalTime
class HomeScreen : Screen {
    override val key: String = "HomeScreenKey"

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<HomeScreenModel>()
        val navigator = LocalRootNavigator.current!!
        val scrollState = rememberScrollState()
        val uiState by screenModel.uiStateFlow.collectAsState()
        val topSeries by screenModel.topSeries.collectAsState()
        val topMovie by screenModel.topMovie.collectAsState()
        val topPlaces by screenModel.topPlaces.collectAsState()

        LaunchedEffect(Unit) {
            screenModel.topSeries()
            screenModel.topMovie()
            screenModel.topPlaces()
        }

        Box(
            modifier = Modifier
                .background(Gray100)
        ) {
            Column(modifier = Modifier .fillMaxSize()
                .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Image(
                        painter = painterResource(Res.drawable.img_logo_text),
                        contentDescription = "로고",
                        modifier = Modifier.height(15.dp),
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                }
                Spacer(Modifier.height(20.dp))
                Image(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .aspectRatio(16f/9f)
                        .shadow(elevation =2.dp, shape = RoundedCornerShape(8.dp), clip = false)
                        .clickable {}
                        .clip(RoundedCornerShape(8.dp))
                        .border(width = 0.5.dp, color = Color.White.copy(alpha = 0.3f), shape = RoundedCornerShape(8.dp)),
                    painter = painterResource(Res.drawable.img_diagnosis),
                    contentDescription = "상단 배너",
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(20.dp))
                SectionTitle("TOP 10 Series")
                HomeContentRow(topSeries, navigator)

                // --- TOP 10 촬영지 ---
                SectionTitle("TOP 10 촬영지")
                HomePlaceRow(topPlaces, navigator)

                // --- TOP 10 Movie ---
                SectionTitle("TOP 10 Movie")
                HomeContentRow(topMovie, navigator)

                Spacer(Modifier.height(20.dp))
            }
        }
    }
}