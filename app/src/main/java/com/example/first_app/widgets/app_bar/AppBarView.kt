package com.example.first_app.widgets.app_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.first_app.shopping_list.ShoppingItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarView() {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    var scope: CoroutineScope = rememberCoroutineScope()

    var drawerViewModel: DrawerViewModel = viewModel()

    // allow us to find out which "View" we are currently are
    val controller: NavHostController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val dialogOpen = remember {
        mutableStateOf(false)
    }

    val currentScreen = remember {
        drawerViewModel.currentScreen.value
    }

    val title = remember {
        mutableStateOf(currentScreen.title)
    }




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title.value) },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                })
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(screenInDrawer) { item ->
                    DrawerItems(selected = currentRoute == item.dRoute, item = item) {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                        if (item.dRoute == "add_account") {
                            dialogOpen.value = true
                        } else {
//                            controller.navigate(item.dRoute)
//                            title.value = item.dTitle

                            controller.navigate(item.dRoute) {
                                popUpTo(controller.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            title.value = item.dTitle
                        }
                    }
                }
            }
        }

    ) {
        Navigation(navController = controller, drawerViewModel = drawerViewModel, pd = it)
        AccountDialog(dialogOpen = dialogOpen)
    }
}

@Composable
fun DrawerItems(
    selected: Boolean,
    item: Screen.DrawerScreen,
    onDrawerItemClicked: () -> Unit
) {
    val backGroup = if (selected) Color.DarkGray else Color.White
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .background(backGroup)
            .clickable {
                onDrawerItemClicked()
            }) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.dTitle,
            Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text(
            text = item.dTitle,
            style = MaterialTheme.typography.h5
        )
    }
}

val screenInDrawer = listOf(
    Screen.DrawerScreen.Account,
    Screen.DrawerScreen.Subscription,
    Screen.DrawerScreen.AddAccount,
)

@Composable
fun Navigation(
    navController: NavHostController,
    drawerViewModel: DrawerViewModel,
    pd: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.DrawerScreen.Account.dRoute,
        modifier = Modifier.padding(pd)
    ) {
        composable(Screen.DrawerScreen.Account.dRoute) {
            AccountView()

        }
        composable(Screen.DrawerScreen.Subscription.dRoute) {
            Subscription()
        }
    }
}