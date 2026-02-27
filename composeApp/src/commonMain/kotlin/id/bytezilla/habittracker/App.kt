package id.bytezilla.habittracker

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.bytezilla.habittracker.ui.HabitViewModel
import id.bytezilla.habittracker.ui.add.AddHabitScreen
import id.bytezilla.habittracker.ui.home.HomeScreen
import id.bytezilla.habittracker.ui.stats.StatsScreen
import id.bytezilla.habittracker.ui.theme.HabitTrackerTheme

@Composable
fun App() {
    HabitTrackerTheme {
        val navController = rememberNavController()
        val viewModel: HabitViewModel = viewModel { HabitViewModel() }

        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onAddHabitClick = { navController.navigate("add") },
                    onStatsClick = { navController.navigate("stats") }
                )
            }
            composable("add") {
                AddHabitScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("stats") {
                StatsScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
