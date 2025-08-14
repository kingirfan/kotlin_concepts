package com.example.first_app.room_db


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.first_app.R
import com.example.first_app.room_db.data.Wish
import com.example.first_app.room_db.viewModel.WishViewModel
import kotlinx.coroutines.launch


@Composable
fun AddEditeDetailView(id: Long, viewModel: WishViewModel, navController: NavHostController) {

    val snackMessage = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    if (id != 0L) {
        val wish = viewModel.getWishById(id = id)
            .collectAsState(initial = Wish(id = 0, title = "", description = ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescritionState = wish.value.description
    } else {
        viewModel.wishTitleState = ""
        viewModel.wishDescritionState = ""
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarView(
                title = if (id != 0L) stringResource(R.string.update_wish) else stringResource(
                    R.string.add_wish
                ), onBackClicked = {
                    navController.navigateUp()
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Title", value = viewModel.wishTitleState, onValueChanged = {
                viewModel.onWishTitleChanged(it)
            })
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = "Descrition",
                value = viewModel.wishDescritionState,
                onValueChanged = {
                    viewModel.onWishDescritionChanged(it)
                })
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                if (viewModel.wishTitleState.isNotEmpty() &&
                    viewModel.wishDescritionState.isNotEmpty()
                ) {
                    if (id != 0L) {
                        viewModel.updateWish(
                            Wish(
                                id = id,
                                title = viewModel.wishTitleState.trimEnd(),
                                description = viewModel.wishDescritionState.trim()
                            )
                        )
                        snackMessage.value = "Wish has been Updated "
                    } else {
                        viewModel.addWish(
                            Wish(
                                title = viewModel.wishTitleState.trimEnd(),
                                description = viewModel.wishDescritionState.trim()
                            )
                        )
                        snackMessage.value = "Wish has been created"
                    }

                } else {
                    snackMessage.value = "Enter Fields To Create a Wish"
                }

                scope.launch {
//                    scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }

            }) {
                Text(
                    text = if (id != 0L) stringResource(R.string.update_wish) else stringResource(
                        R.string.add_wish
                    )
                )
            }
        }
    }
}

@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onValueChanged,
        label = { Text(text = label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black)
        )
    )
}


