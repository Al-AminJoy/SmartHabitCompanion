package com.alamin.smarthabitcompanion.ui.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alamin.smarthabitcompanion.R
import kotlinx.coroutines.delay

@Composable
fun SplashLogo() {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
        delay(1500)
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(1500)) + scaleIn(tween(1500), initialScale = 0.8f),
        exit = fadeOut(tween(1500)) + scaleOut(tween(1500), targetScale = 1.2f)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
    }
}