package com.ninhtbm.vcl.feature.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.ninhtbm.vcl.utils.throttleClick

@Composable
internal fun SearchScreen(navController: NavController, keyword: String) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .throttleClick(onClick = navController::navigateUp),
        textAlign = TextAlign.Center,
        text = keyword
    )
}