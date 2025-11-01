package com.modura.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.*
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_cast_example
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun Cast(
    //id: Int,
    //image: String,
    name: String,
    role: String
){
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Image(
            //painter = painterResource(image),
            painter = painterResource(Res.drawable.img_cast_example),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.width(100.dp).height(100.dp).clip(RoundedCornerShape(999.dp))
        )
        Spacer(Modifier.height(4.dp))
        Text(name, style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(2.dp))
        Text(role, style = MaterialTheme.typography.labelSmall)
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
