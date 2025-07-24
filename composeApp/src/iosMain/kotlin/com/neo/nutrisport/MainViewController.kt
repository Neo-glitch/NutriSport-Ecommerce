package com.neo.nutrisport

import androidx.compose.ui.window.ComposeUIViewController
import com.neo.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }