package com.modura.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.modura.app.data.dev.City
import com.modura.app.data.dev.DummyProvider
import com.modura.app.data.dev.States
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.Gray800
import com.modura.app.ui.theme.White
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.ic_check
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressBottomSheet(
    onDismissRequest: () -> Unit,
    onAddressSelected: (States, City) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val address = DummyProvider.address
    var selectedState by remember { mutableStateOf<States?>(address.firstOrNull()) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding( top =50.dp,bottom =  30.dp)
                .fillMaxHeight(0.4f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(White)
                ) {
                    items(address) { state ->
                        Text(
                            text = state.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedState = state }
                                .padding(16.dp),
                            fontWeight = if (selectedState == state) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedState == state) Color.Black else Gray500
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    selectedState?.cities?.let { cities ->
                        items(cities) { city ->
                            Text(
                                text = city.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onAddressSelected(selectedState!!, city)
                                        onDismissRequest()
                                    }
                                    .padding(16.dp),
                                color = Gray800
                            )
                        }
                    }
                }
            }
        }
    }
}