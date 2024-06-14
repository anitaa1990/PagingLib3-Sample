package com.an.paginglib3_sample.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.an.paginglib3_sample.R
import kotlinx.coroutines.delay

@Composable
fun OfflineStatusBar(
    modifier: Modifier,
    isConnected: Boolean
)  {
    var visibility by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = visibility,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        OfflineStatusBox(modifier, isConnected)
    }

    LaunchedEffect(isConnected) {
        if (!isConnected) {
            visibility = true
        } else {
            delay(2000)
            visibility = false
        }
    }
}

@Composable
fun OfflineStatusBox(
    modifier: Modifier,
    isConnected: Boolean
) {
    val backgroundColor by animateColorAsState(
        if (isConnected) {
            MaterialTheme.colorScheme.onSurface
        } else MaterialTheme.colorScheme.onErrorContainer,
        label = ""
    )
    val message = if (isConnected) {
        R.string.connection_available
    } else R.string.connection_unavailable

    val iconResource = if (isConnected) {
        R.drawable.ic_connection_on
    } else {
        R.drawable.ic_connection_off
    }

    Box(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = "Connectivity Icon",
                tint = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(id = message),
                color = MaterialTheme.colorScheme.surface,
                fontSize = 15.sp
            )
        }
    }
}

@Preview
@Composable
fun OfflineStatusBoxPreview() {
    OfflineStatusBox(Modifier, true)
}