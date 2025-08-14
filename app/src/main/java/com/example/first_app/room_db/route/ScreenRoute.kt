package com.example.first_app.room_db.route

sealed  class ScreenRoute(val route : String) {
    object HomeScreen : ScreenRoute(route = "home_screen")
    object AddScreen : ScreenRoute(route = "add_screen")
}