package com.example.sw_runes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sw_runes.ui.theme.*


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SwRunesTheme {
        Bubble({ return@Bubble } )
    }
}

@Composable
inline fun Bubble(crossinline click: () -> Unit) {
    Box(modifier = Modifier
    .padding(padding)
        .innerShadow(
            Color.Black.copy(alpha = 0.25F),
            50.dp, 2.dp, 2.dp, 2.dp, 2.dp
        )
        .outterShadow(50.dp)
        .clip(RoundedCornerShape(50.dp))
        .background(MaterialTheme.colorScheme.background).width(75.dp).height(75.dp).clickable( onClick =  { click() } , )
        .border(
            1.dp,
            MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(50.dp),
        )
        .padding(padding)){



    }
}
