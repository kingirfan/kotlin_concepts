package com.example.first_app.widgets.app_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.first_app.R
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

    val isSheetFullScreen by remember { mutableStateOf(false) }

    val modifier = if (isSheetFullScreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()

    val dialogOpen = remember {
        mutableStateOf(false)
    }

    val currentScreen = remember {
        drawerViewModel.currentScreen.value
    }

    val title = remember {
        mutableStateOf(currentScreen.title)
    }

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )


    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp

    val bottomBar: @Composable () -> Unit = {
        if (currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreen.Home) {


            BottomNavigation(modifier = Modifier.wrapContentSize()) {

                screenInBottom.forEach { item ->


                    BottomNavigationItem(
                        selected = currentRoute == item.bRoute,
                        onClick = {
                            title.value = item.bTitle
                            controller.navigate(item.bRoute) {
                                // Pop up to the start destination of the graph
                                // to avoid building up a large stack of destinations
                                popUpTo(controller.graph.startDestinationId) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }

                        }, icon = {
                            Icon(
                                contentDescription = item.bTitle,
                                painter = painterResource(id = item.icon)
                            )
                        }, label = {
                            Text(text = item.bTitle)
                        },
                        unselectedContentColor = Color.Black,
                        selectedContentColor = Color.White
                    )

                }
            }
        }
    }


    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(
            topStart = roundedCornerRadius,
            topEnd = roundedCornerRadius
        ),
        sheetContent = {
            MoreBottomSheet(modifier = modifier)
        }) {
        Scaffold(
            bottomBar = bottomBar,
            topBar = {
                TopAppBar(
                    title = { Text(text = title.value) },
                     actions = {
                         if (currentRoute == Screen.BottomScreen.Library.bRoute) {

                         IconButton(
                             onClick = {
                                 scope.launch {
                                     if (modalSheetState.isVisible)
                                         modalSheetState.hide()
                                     else
                                         modalSheetState.show()
                                 }
                             }
                         ) {
                             Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                         }
                     }
                    },
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
            Navigation(navController = controller,  pd = it)
            AccountDialog(dialogOpen = dialogOpen)
        }
    }
}


@Composable
fun MoreBottomSheet(modifier: Modifier) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(
                MaterialTheme.colors.primarySurface
            )
    ) {
        Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Row(modifier = modifier.padding(16.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.add_account),
                    contentDescription = "Settings"
                )
                Text(text = "Settings", fontSize = 20.sp, color = Color.White)
            }
            Row(modifier = modifier.padding(16.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.add_account),
                    contentDescription = "Share"
                )

                Text(
                    text = "Share",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            Row(modifier = modifier.padding(16.dp)) {
                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.add_account),
                    contentDescription = "Help"
                )
                Text(text = "Help", fontSize = 20.sp, color = Color.White)
            }
        }
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

val screenInBottom = listOf(
    Screen.BottomScreen.Home,
    Screen.BottomScreen.Library,
    Screen.BottomScreen.Browse,
)

@Composable
fun Navigation(
    navController: NavHostController,

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

        composable(Screen.BottomScreen.Home.bRoute) {
            HomeView()
        }

        composable(Screen.BottomScreen.Browse.bRoute) {
            Text("Browse")
        }

        composable(Screen.BottomScreen.Library.bRoute) {
            LibraryScreen()
        }
    }
}