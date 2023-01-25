package com.example.sw_runes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
        Container( )
    }
}

@Composable
inline fun Container() {
    Box(modifier = Modifier
        .padding(padding)
        .innerShadow(
            Color.Black.copy(alpha = 0.25F),
            borderRadiusSize, 2.dp, 2.dp, 2.dp, 2.dp
        )
        .outterShadow(borderRadiusSize)
        .clip(boxShape)
        .background(MaterialTheme.colorScheme.background)
        .border(
            1.dp,
            MaterialTheme.colorScheme.primary,
            shape = boxShape,
        )
        .padding(padding)){

        Text(text = "sdqdsq")

    }
}
