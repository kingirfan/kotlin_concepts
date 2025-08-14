package com.example.first_app.room_db.route


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.first_app.room_db.AddEditeDetailView
import com.example.first_app.room_db.HomeView
import com.example.first_app.room_db.viewModel.WishViewModel


@Composable
fun Navigation(
    viewModel: WishViewModel = viewModel(),
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(navController = navHostController, startDestination = ScreenRoute.HomeScreen.route) {
        composable(ScreenRoute.HomeScreen.route) {
            HomeView(navHostController,viewModel)
        }
        composable(ScreenRoute.AddScreen.route+"/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.LongType
                defaultValue = 0L
                nullable = false
            }
        )) {
            entry ->
            val id = if(entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            AddEditeDetailView(id = id, viewModel = viewModel, navController = navHostController)
        }
    }
}