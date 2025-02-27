package com.hometest.flicksearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hometest.flicksearch.ui.common.AppBackground
import com.hometest.flicksearch.ui.common.TopBar
import com.hometest.flicksearch.ui.navigation.AppNavigation
import com.hometest.flicksearch.ui.theme.FlickSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlickSearchTheme {
                val navController = rememberNavController()
                val topBarState = rememberSaveable { (mutableStateOf(false)) }
                val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AnimatedVisibility(
                            visible = topBarState.value,
                            enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
                            exit = fadeOut() + slideOutVertically(targetOffsetY = { -it })
                        ) {
                            TopBar(
                                Modifier,
                                navController,
                                bottomBarState
                            )
                        }
                    },
                ) { innerPadding ->
                    AppBackground(modifier = Modifier) {
                        AppNavigation(
                            modifier = Modifier,
                            navController = navController,
                            padding = innerPadding,
                        )
                    }
                }
            }
        }
    }
}
