package com.example.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp
object Dimens {
    val appBarHeight = 40.dp
    val episodeItemSize = 120.dp
    val progressBarSize = 100.dp
    val largeLineHeight = 40.dp
    val mediumLineHeight = 12.dp
    val characterDetailsImageHeight = 400.dp
}

val LocalDimens = compositionLocalOf { Dimens }
val MaterialTheme.dimens: Dimens
    @Composable
    get() = LocalDimens.current
