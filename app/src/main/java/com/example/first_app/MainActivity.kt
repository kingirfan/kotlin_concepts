package com.example.first_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.first_app.counter_app.CounterViewModel
import com.example.first_app.ui.theme.First_appTheme
import kotlin.math.roundToInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.first_app.counter_app.CounterApp
import com.example.first_app.json_retrofit.RecipeApp
import com.example.first_app.json_retrofit.RecipeScreen

import com.example.first_app.navigation.FirstScreen
import com.example.first_app.navigation.SecondScreen
import com.example.first_app.room_db.HomeView
import com.example.first_app.room_db.route.Navigation
import com.example.first_app.shopping_list.ShoppingListApp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CounterViewModel = viewModel()
            val navController = rememberNavController()
            First_appTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    ShoppingListApp(modifier = Modifier.padding(innerPadding))
//                    CounterApp(modifier = Modifier.padding(innerPadding), viewModel)
//                    RecipeApp(
//                        modifier = Modifier.padding(innerPadding),
//                        navHostController = navController)
////                    MyApp(modifier = Modifier.padding(innerPadding))
//                }
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Navigation()
                }
            }
        }
    }

    @Composable
    fun MyApp(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "recipeScreen") {
            composable("firstScreen") {
                FirstScreen { name, age ->
                    navController.navigate("secondScreen/$name/$age")
                }
            }
            composable("secondScreen/{name}/{age}") {
                val name = it.arguments?.getString("name") ?: "No Name"
                val age = it.arguments?.getString("age") ?: "No Name"
                print("ReceivedName : $name")
                SecondScreen(name, age) {
                    navController.navigate("firstScreen")
                }
            }
            composable("recipeScreen") {
//                RecipeScreen()
            }

        }
    }

    @Composable
    fun UnitConverter(modifier: Modifier = Modifier) {

        var inputValue by remember { mutableStateOf("") }
        var outputValue by remember { mutableStateOf("") }
        var outputUnit by remember { mutableStateOf("Centimeters") }
        var inputUnit by remember { mutableStateOf("Meters") }
        var iExpanded by remember { mutableStateOf(false) }
        var oExpanded by remember { mutableStateOf(false) }
        var conversionFactor by remember { mutableDoubleStateOf(1.00) }
        var oConversionFactor by remember { mutableDoubleStateOf(1.00) }

        fun conversionUnits() {
            val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
            val result =
                (inputValueDouble * conversionFactor * 100.0 / oConversionFactor).roundToInt() / 100.0
            outputValue = result.toString()
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Unit Converter")
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = inputValue,
                label = { Text("Enter Text") },
                onValueChange = {
                    inputValue = it
                    conversionUnits()
                },
            )
            Spacer(modifier = Modifier.size(16.dp))
            Row {
                Box {
                    Button(onClick = {
                        iExpanded = true
                    }) {
                        Text(inputUnit)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow")
                    }
                    DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text("Centimeter") },
                            onClick = {
                                iExpanded = false
                                inputUnit = "Centimeter"
                                conversionFactor = 0.01
                                conversionUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Meters") },
                            onClick = {
                                iExpanded = false
                                inputUnit = "Meters"
                                conversionFactor = 1.0
                                conversionUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Feet") },
                            onClick = {
                                iExpanded = false
                                inputUnit = "Feet"
                                conversionFactor = 0.3048
                                conversionUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("MM") },
                            onClick = {
                                iExpanded = false
                                inputUnit = "Feet"
                                conversionFactor = 0.001
                                conversionUnits()
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box {
                    Button(onClick = { oExpanded = true }) {
                        Text(outputUnit)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow")
                    }
                    DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text("Centimeter") },
                            onClick = {
                                oExpanded = false
                                outputUnit = "Centimeter"
                                oConversionFactor = 0.01
                                conversionUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Meter") },
                            onClick = {
                                oExpanded = false
                                outputUnit = "Meter"
                                oConversionFactor = 1.0
                                conversionUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Feet") },
                            onClick = {
                                oExpanded = false
                                outputUnit = "Feet"
                                oConversionFactor = 0.3048
                                conversionUnits()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Inches") },
                            onClick = {
                                oExpanded = false
                                outputUnit = "Inches"
                                oConversionFactor = 0.01
                                conversionUnits()
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Text("Result : $outputValue")

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun UnitConverterPreview() {
        UnitConverter()
    }

    @Composable
    fun HandleStates(modifier: Modifier = Modifier) {
        val textValue = remember { mutableIntStateOf(0) }
        val direction = remember { mutableStateOf("North") }

        Column {
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Value Is: ${textValue.value}")
            Text(text = "Currect Direction: ${direction.value}")

            Button(onClick = {
                direction.value = "North"
                if (Random.nextBoolean()) {
                    textValue.value += 1
                }
            }) {
                Text(text = "North")
            }
            Button(onClick = {
                direction.value = "South"
                if (Random.nextBoolean()) {
                    textValue.value += 1
                }
            }) {
                Text(text = "South")
            }
            Button(onClick = {
                direction.value = "East"
                if (Random.nextBoolean()) {
                    textValue.value += 1
                }
            }) {
                Text(text = "East")
            }
            Button(onClick = {
                direction.value = "West"
                if (Random.nextBoolean()) {
                    textValue.value += 1
                }
            }) {
                Text(text = "West")
            }
        }
    }
}
