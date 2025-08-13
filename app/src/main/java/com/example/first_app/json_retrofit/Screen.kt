package com.example.first_app.json_retrofit

sealed class Screen(val route : String){
    object RecipeScreen : Screen("recipeScreen")
    object CategoryDetailScreen : Screen("categoryDetailScreen")
}