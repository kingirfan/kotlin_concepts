package com.example.first_app.json_retrofit

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RecipeApp(modifier: Modifier = Modifier, navHostController: NavHostController) {
    val recipeViewModel: MainViewModel = viewModel()
    val viewState by recipeViewModel.categoriesState

    NavHost(navController = navHostController, startDestination = Screen.RecipeScreen.route) {

        composable(route = Screen.RecipeScreen.route) {
            RecipeScreen(viewState = viewState, navigationToDetailScreen = {
                navHostController.currentBackStackEntry?.savedStateHandle?.set("cat", it)
                navHostController.navigate(Screen.CategoryDetailScreen.route)
            })
        }

        composable(route = Screen.CategoryDetailScreen.route) {
            val category =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<Category>("cat")
                    ?: Category("", "", "", "")
            CategoryDetailScreen(category = category)
        }

    }

}