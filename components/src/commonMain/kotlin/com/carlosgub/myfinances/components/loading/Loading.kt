package com.carlosgub.myfinances.components.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.carlosgub.myfinances.theme.ColorOrange

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize().then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(
            color = ColorOrange,
        )
    }
}
