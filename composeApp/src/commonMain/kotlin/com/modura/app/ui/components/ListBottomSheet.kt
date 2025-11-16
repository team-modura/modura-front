package com.modura.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.Gray800

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListBottomSheet(
    title: String?= null,
    items: List<String>,
    onDismissRequest: () -> Unit,
    onSelect: (String) -> Unit,
    ) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var selectedItem by remember { mutableStateOf<String>("") }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface ,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 50.dp, 20.dp, 30.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if(title!=null){
                Text(text = title, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(20.dp))
            }
            Column {
                items.forEach { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedItem = item
                                onSelect(item)
                            }
                            .padding(vertical = 10.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selectedItem == item) MaterialTheme.colorScheme.onBackground  else Gray500,
                        fontWeight = if (selectedItem == item) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}