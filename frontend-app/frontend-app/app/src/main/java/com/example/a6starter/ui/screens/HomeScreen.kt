
package com.example.a6starter.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme as Material3Theme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.a6starter.R
import kotlinx.coroutines.delay

const val INTRO_DELAY_MS = 3000L
val MESSAGES = listOf(
    "Manage your notes easily with this app",
    "Made just for you",
    "Keeps track of your notes"
)
const val SLIDE_DURATION_MS = 500

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
) {
    var index by remember { mutableStateOf(0) }
    LaunchedEffect(key1 = null) {
        while (true) {
            delay(INTRO_DELAY_MS)
            index = (index + 1) % MESSAGES.size
        }
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF2196F3), // Blue
            Color(0xFF21CBF3)  // Light Blue
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppLogo(
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = "Welcome to Simple Notes",
                style = Material3Theme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            AnimatedContent(
                targetState = index,
                transitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(SLIDE_DURATION_MS)
                    ) with slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(SLIDE_DURATION_MS)
                    )
                }, label = ""
            ) { i ->
                Text(
                    text = MESSAGES[i],
                    style = Material3Theme.typography.bodyLarge.copy(
                        color = Color.White.copy(alpha = 0.9f)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

        }

        FloatingDecorations(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        )
    }
}

@Composable
fun AppLogo(modifier: Modifier = Modifier) {

    Icon(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "App Logo",
        tint = Color.White,
        modifier = modifier
    )
}

@Composable
fun FloatingDecorations(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        FloatingCircle(
            color = Color.White.copy(alpha = 0.1f),
            size = 100.dp,
            offset = 50.dp to 100.dp
        )
        FloatingCircle(
            color = Color.White.copy(alpha = 0.1f),
            size = 150.dp,
            offset = -100.dp to 200.dp
        )
        FloatingCircle(
            color = Color.White.copy(alpha = 0.1f),
            size = 80.dp,
            offset = 200.dp to -150.dp
        )
    }
}

@Composable
fun FloatingCircle(color: Color, size: Dp, offset: Pair<Dp, Dp>) {
    val (x, y) = offset
    Box(
        modifier = Modifier
            .size(size)
            .background(color = color, shape = CircleShape)
            .offset(x = x, y = y)
            .alpha(0.5f)
    )
}



