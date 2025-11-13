package com.modura.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.*

@Composable
fun Review(
    //id: Int,
    average: Double,
    count: List<Int>
){
    val maxCount = count.max()
    Row(modifier = Modifier.fillMaxWidth().padding(50.dp,10.dp).background(White), verticalAlignment = Alignment.CenterVertically){
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)){
            Text("평균",style = MaterialTheme.typography.bodySmall, color = Gray800)
            Text(average.toString(),style = MaterialTheme.typography.headlineLarge, color = Gray800)
            Row{
                for (i in 1..5) {
                    val fraction = when {
                        average >= i.toDouble() -> 1.0
                        average > (i - 1).toDouble() -> average - (i - 1).toDouble()
                        else -> 0.0
                    }
                    ReviewStar(fraction = fraction.toFloat())
                }
            }
        }
        Spacer(Modifier.width(20.dp))
        Column{
            count.forEachIndexed { index, count ->
                val score = 5 - index
                ReviewCount(
                    num = score,
                    count = count,
                    maxReviews = maxCount
                )
            }
        }
    }
}
/*
@Composable
@Preview
private fun preview(){
    Review(
        id = 1,
        average = 4.5f,
        count = listOf(10,4,6,2,3)
    )
}*/
