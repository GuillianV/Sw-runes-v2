package com.example.sw_runes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sw_runes.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwRunesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Container("hey")
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SwRunesTheme {
        Container(name = "df")
    }
}

@Composable
fun Container(name: String) {
    Box(modifier = Modifier.padding(padding).innerShadow(Color.Black.copy(alpha = 0.25F),
        borderRadiusSize,2.dp,2.dp,2.dp,2.dp).outterShadow()
        .background(MaterialTheme.colorScheme.background)
        .border(
            1.dp,
            MaterialTheme.colorScheme.primary,
            shape = boxShape,
        ).padding(padding)){

        Text(text = name)
    }
}
