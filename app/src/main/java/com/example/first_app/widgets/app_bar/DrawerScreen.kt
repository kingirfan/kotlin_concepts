package com.example.first_app.widgets.app_bar


import androidx.annotation.DrawableRes
import com.example.first_app.R

sealed class Screen(val title: String, val route: String) {

    sealed class BottomScreen(val bTitle: String, val bRoute: String, @DrawableRes val icon: Int) :
        Screen(bTitle, bRoute) {
        object Home : BottomScreen("Home", "home", R.drawable.add_account)
        object Library : BottomScreen("Library", "library", R.drawable.baseline_library_add_24)
        object Browse : BottomScreen("Browse", "browse", R.drawable.browse)
    }

    sealed class DrawerScreen(val dTitle: String, val dRoute: String, @DrawableRes val icon: Int) :
        Screen(dTitle, dRoute) {
        object Account :
            DrawerScreen(dTitle = "Account", dRoute = "account", R.drawable.add_account)

        object Subscription :
            DrawerScreen(dTitle = "Subscription", dRoute = "subscription", R.drawable.ic_subscribe)

        object AddAccount :
            DrawerScreen(
                dTitle = "AddAccount",
                dRoute = "add_account",
                R.drawable.baseline_person_add_alt_1_24
            )
    }
}