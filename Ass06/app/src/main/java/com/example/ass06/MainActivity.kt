package com.example.ass06

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ass06.ui.theme.Ass06Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ass06Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScaffoldLayout()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navController: NavHostController,contextForToast: Context){
    var expanded by remember {
        mutableStateOf(false)
    }
    CenterAlignedTopAppBar(title = { Text(text = "My Application") },
        actions = {
            IconButton(onClick = {
//                Toast.makeText(contextForToast,"Notification", Toast.LENGTH_SHORT).show()
                navController.navigate(Screen.Friend1.route)
            }) {
                Icon(painter = painterResource(id = R.drawable.emoji), contentDescription = "emoji", modifier = Modifier.size(23.dp), tint = Color.Unspecified)
            }
            IconButton(onClick = {
//                Toast.makeText(contextForToast,"Home", Toast.LENGTH_SHORT).show()
                navController.navigate(Screen.Friend2.route)
            }) {
                Icon(painter = painterResource(id = R.drawable.smile), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(23.dp))
            }

//            IconButton(onClick = { expanded = true }) {
//                Icon(Icons.Default.MoreVert , contentDescription = "Open Menu")
//            }
//            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
//                DropdownMenuItem(text = { Text(text = "Setting")}, onClick = {
//                    Toast.makeText(contextForToast,"Setting", Toast.LENGTH_SHORT).show()
//                    expanded = false
//                },
//                    leadingIcon = {
//                        Icon(Icons.Outlined.Settings, contentDescription = null)
//                    }
//                )
//                DropdownMenuItem(text = { Text(text = "Logout")}, onClick = {
//                    Toast.makeText(contextForToast,"Logout", Toast.LENGTH_SHORT).show()
//                    expanded = false
//                },
//                    leadingIcon = {
//                        Icon(Icons.Outlined.Logout, contentDescription = null)
//                    }
//                )
//            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Green.copy(alpha = 0.3f)
        )
    )
}

@SuppressLint("RestrictedApi")
@Composable
fun MyBottomBar(navController: NavHostController, contextForToast: Context){
    val navigationITems = listOf(
        Screen.Home,
        Screen.Friend1,
        Screen.Friend2
    )
    var selectedScreen by remember {
        mutableStateOf(0)
    }
    NavigationBar {
        navigationITems.forEachIndexed{ index, screen ->
            NavigationBarItem(selected = (selectedScreen == index), label = { Text(text = screen.name)}, icon = { Icon(
                imageVector = screen.icon,
                contentDescription = null
            ) } ,onClick = {
                if(navController.currentBackStack.value.size >= 2){
                    navController.popBackStack()
                }
                selectedScreen = index
                navController.navigate(screen.route)
                Toast.makeText(contextForToast,screen.name, Toast.LENGTH_SHORT).show()
            })
        }
    }
}

@Composable
fun MyScaffoldLayout(){
    val contextForToast = LocalContext.current.applicationContext
    val navController = rememberNavController()
    Scaffold(
        topBar = { MyTopAppBar(navController = navController,contextForToast = contextForToast)},
        bottomBar = { MyBottomBar(navController,contextForToast)},
        floatingActionButtonPosition = FabPosition.End
    ) {
            paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues), horizontalAlignment = Alignment.CenterHorizontally) {
//            Text(text = "Screen area")
        }
        NavGraph(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ass06Theme {
        Greeting("Android")
    }
}