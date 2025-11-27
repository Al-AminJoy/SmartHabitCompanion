package com.alamin.smarthabitcompanion.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.alamin.smarthabitcompanion.ui.theme.Red
import kotlinx.coroutines.delay
import java.text.BreakIterator
import java.text.StringCharacterIterator

@Composable
fun TypeTextAnimation(modifier: Modifier = Modifier,text: String,textStyle: TextStyle) {

    val breakIterator = remember(text) { BreakIterator.getCharacterInstance() }

    var subStringText by remember { mutableStateOf("") }
    var typeAgain by remember { mutableStateOf(true) }

    LaunchedEffect(typeAgain) {
        delay(1000)
        breakIterator.text = StringCharacterIterator(text)

        var nextIndex = breakIterator.next()

        while (nextIndex != BreakIterator.DONE){
            subStringText = text.substring(0,nextIndex)
            nextIndex = breakIterator.next()
            delay(200)
        }
        typeAgain = !typeAgain

    }

    Text(subStringText, style = textStyle, modifier = Modifier)

    
}