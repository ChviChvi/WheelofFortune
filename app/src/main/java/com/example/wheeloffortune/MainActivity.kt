package com.example.wheeloffortune
/**
 *  Project:"Wheel of Fortune" made by Christiaan Vink s215832/cvin
 *
 *  I want to state that I have used the code lab:
 *  https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#0
 *  Therefore it could be possible that there is code which looks similar(or is the same) to this.
 *
 */
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.wheeloffortune.ui.theme.ui.Screen





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    ,

            ) {
                Screen( )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
        Screen()
}