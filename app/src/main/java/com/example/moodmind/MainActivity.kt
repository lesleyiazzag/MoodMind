package com.example.moodmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moodmind.ui.navigation.MoodMindScreenRoute
import com.example.moodmind.ui.navigation.SplashScreenRoute
import com.example.moodmind.ui.navigation.SummaryScreenRoute
import com.example.moodmind.ui.screen.MoodMindScreen
import com.example.moodmind.ui.screen.SplashScreen
import com.example.moodmind.ui.screen.SummaryScreen
import com.example.moodmind.ui.screen.SummaryViewModel
import com.example.moodmind.ui.theme.MoodMindTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoodMindTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreenRoute
    ) {
        composable<SplashScreenRoute> {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(MoodMindScreenRoute) {
                        popUpTo(SplashScreenRoute) { inclusive = true }
                    }
                }
            )
        }
        composable<MoodMindScreenRoute> {
            MoodMindScreen(
                onInfoClicked = { allEntries, sadEntries, happyEntries, angryEntries, surprisedEntries, disgustedEntries, motivatedEntries, unmotivatedEntries, apatheticEntries, stressedEntries, creativeEntries, productiveEntries, unproductiveEntries ->
                    navController.navigate(
                        SummaryScreenRoute(allEntries, sadEntries, happyEntries, angryEntries, surprisedEntries, disgustedEntries, motivatedEntries, unmotivatedEntries, apatheticEntries, stressedEntries, creativeEntries, productiveEntries, unproductiveEntries)
                    )
                }
            )
        }

        composable<SummaryScreenRoute> {
            SummaryScreen()
        }

    }
}
