package com.uvg.pinterestfeed

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.metrics.performance.JankStats
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.uvg.pinterestfeed.ui.DetailScreen
import com.uvg.pinterestfeed.ui.FeedScreen
import com.uvg.pinterestfeed.ui.FeedViewModel
import com.uvg.pinterestfeed.ui.Routes
import com.uvg.pinterestfeed.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNav()
                }
            }


            LaunchedEffect(Unit) {
                val decor = window?.peekDecorView()
                if (decor != null) {
                    JankStats.createAndTrack(window) { frame ->
                        if (frame.isJank) {
                            Log.d("JankStats", "Jank detected: ${frame.frameDurationUiNanos}")
                        }
                    }
                } else {
                    Log.w("JankStats", "DecorView is null, skipping init")
                }
            }
        }
    }
}

@Composable
fun AppNav() {
    val nav = rememberNavController()


    val factory = viewModelFactory { initializer { FeedViewModel(createSavedStateHandle()) } }
    val vm: FeedViewModel = viewModel(factory = factory)

    NavHost(navController = nav, startDestination = Routes.Feed) {
        composable(Routes.Feed) {
            FeedScreen(
                vm = vm,
                onOpenDetail = { id, url, title, w, h ->
                    // ✅ Codificar URL y título para evitar crashes por '/'
                    val safeUrl = Uri.encode(url)
                    val safeTitle = Uri.encode(title)
                    nav.navigate("detail/$id/$safeUrl/$safeTitle/$w/$h")
                }
            )
        }
        composable(
            route = Routes.Detail,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("url") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType },
                navArgument("w") { type = NavType.IntType },
                navArgument("h") { type = NavType.IntType },
            )
        ) { backStack ->
            val id = backStack.arguments!!.getString("id")!!
            val encodedUrl = backStack.arguments!!.getString("url")!!
            val encodedTitle = backStack.arguments!!.getString("title")!!
            val width = backStack.arguments!!.getInt("w")
            val height = backStack.arguments!!.getInt("h")


            val realUrl = Uri.decode(encodedUrl)
            val realTitle = Uri.decode(encodedTitle)

            DetailScreen(
                id = id,
                url = realUrl,
                title = realTitle,
                width = width,
                height = height,
                onBack = { nav.popBackStack() }
            )
        }
    }
}
