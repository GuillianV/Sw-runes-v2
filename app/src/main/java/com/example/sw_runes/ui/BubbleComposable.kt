package com.example.sw_runes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.sw_runes.ui.theme.*
import kotlin.math.roundToInt


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SwRunesTheme {
        Bubble({ return@Bubble } )
    }
}

@Composable
inline fun Bubble(crossinline click: () -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }




            Box(modifier = Modifier

                .width(75.dp)
                .height(75.dp)

                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }

                .onGloballyPositioned {  }
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        offsetX += dragAmount.x
                        var offset : Float = offsetX
                        offsetY += dragAmount.y
                    }
                }
                .innerShadow(
                    Color.Black.copy(alpha = 0.25F),
                    50.dp, 2.dp, 2.dp, 2.dp, 2.dp
                )
                .outterShadow(50.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.background)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50.dp),
                )
                .padding(padding)){



            }






}
