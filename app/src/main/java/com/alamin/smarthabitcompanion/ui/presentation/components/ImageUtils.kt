package com.alamin.smarthabitcompanion.ui.presentation.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest
import com.alamin.smarthabitcompanion.R

@Composable
fun buildImageRequest(imageUrl: String,context: Context = LocalContext.current): ImageRequest {
    return ImageRequest.Builder(context)
        .data(imageUrl)
        .crossfade(true)
        .error(R.drawable.no_image)
        .build()
}