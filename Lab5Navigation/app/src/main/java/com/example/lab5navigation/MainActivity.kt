package com.example.lab5navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab5navigation.ui.theme.Lab5NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab5NavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ComposeAllNavigation()
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

sealed class ScreenRoute(val route: String){
    object First : ScreenRoute("first_screen")
    object Second : ScreenRoute("second_screen")
}


@Composable
fun ComposeAllNavigation() {

    val navController = rememberNavController()
    Column(modifier = Modifier
        .fillMaxSize()
        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally) {

    }
    NavHost(navController = navController, startDestination = ScreenRoute.First.route) {
        composable(ScreenRoute.First.route) {
            MyPage1(navController)
        }

        composable(ScreenRoute.Second.route) {
            MyPage2(navController)
        }
    }
}

@Composable
fun IdNameAgeContent(id:String, onIDChane:(String)->Unit, name:String, onNameChange:(String)->Unit,age:String, onAgeChange:(String)->Unit){
    Column(modifier = Modifier.padding(horizontal = 5.dp)) {
        OutlinedTextField(value = id, onValueChange =onIDChane, label = { Text(text = "StudentID")}, modifier = Modifier
            .width(400.dp)
            .padding(bottom = 16.dp) )
        OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text(text = "Name")}, modifier = Modifier
            .width(400.dp)
            .padding(bottom = 16.dp))
        OutlinedTextField(value = age, onValueChange = onAgeChange, label = { Text(text = "Age")}, modifier = Modifier
            .width(400.dp)
            .padding(bottom = 16.dp), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }

}

@Composable
fun CheckboxGroup(items: List<String>, onSelectionChange: (List<String>)->Unit){
    val selectedItem = remember {
        mutableStateListOf<String>()
    }
    items.forEach{ item->
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = selectedItem.contains(item), onCheckedChange = {
                if(it){
                    selectedItem.add(item)
                }else{
                    selectedItem.remove(item)
                }
                onSelectionChange(selectedItem.toList())
            })
            Text(text = item, fontSize = 15.sp)
        }
    }
}

fun startActivitySaft(context: Context?, pagkageName:String){
    if (context == null || pagkageName == null){
        Log.e("startActivitySafely","Context or intent is null!")
        return
    }
    try {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.`package`=pagkageName
        context.startActivity(intent)
    }
    catch (e: Exception){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse("https://play.google.com/store/apps/details?id=$pagkageName")
        ContextCompat.startActivity(context,i,null)
        Log.e("startActivitySafely","Error strarting activity",e)
    }
}

@Composable
fun MyPage1(navHostController: NavHostController){
    var id by rememberSaveable {
        mutableStateOf("")
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var age by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 16.dp, vertical = 5.dp), horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "Page1", modifier = Modifier.background(color = Color.LightGray, shape = RoundedCornerShape(20.dp)), fontWeight = FontWeight.Bold, fontSize = 25.sp)
        Text(text = "Enter Student Information", modifier = Modifier.padding(5.dp), fontSize = 20.sp)
        IdNameAgeContent(id = id, onIDChane = {id=it}, name = name, onNameChange = {name=it}, age = age, onAgeChange = {age=it})
        val hobbyList = listOf("Reading","Painting","Cooking")
        var selectedOut by remember {
            mutableStateOf("")
        }
        val selectedItems by remember {
            mutableStateOf(mutableListOf<String>())
        }
        Text(text = "Select your hobby:", modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp))
        Row {
            CheckboxGroup(items = hobbyList){ newSelectedItems->
                selectedItems.clear()
                selectedItems.addAll(newSelectedItems)
                Log.d("CheckboxGroup","Selected items: $selectedItems")
                selectedOut = selectedItems.toString()
            }

        }
        Spacer(modifier = Modifier.height(height = 8.dp))
        Button(onClick = {
            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                "data",
                Student(id,name,age.toInt(),selectedItems)

            )
            navHostController.navigate(ScreenRoute.Second.route)
        }) {
            Text(text = "Send Information")
        }

        Button(onClick = {
            val packegeName = "com.google.android.youtube"
            startActivitySaft(context, pagkageName = packegeName)
        }) {
            Text(text = "Open Youtube")
        }
        Button(onClick = {
            val packegeName = "com.ss.android.ugc.trill"
            startActivitySaft(context, pagkageName = packegeName)
        }) {
            Text(text = "Open TikTok")
        }
    }

}

@Composable
fun MyPage2(navHostController: NavHostController){
    val data = navHostController.previousBackStackEntry?.savedStateHandle?.get<Student>("data") ?:
    Student("","",0, listOf())
    var lastIndex = data.hobby.size-1
    var hobbySelect=""

    data.hobby.forEachIndexed { index, item ->
        hobbySelect += if (index == lastIndex) item else "$item, "
    }

    Column(horizontalAlignment = Alignment.Start) {
        IconButton(modifier = Modifier.size(100.dp),onClick = {
            navHostController.navigateUp()
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "", tint = Color.Magenta)
        }
    }
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), horizontalAlignment = Alignment.Start){
        Text(modifier = Modifier
            .background(color = Color.LightGray, shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
            .align(Alignment.CenterHorizontally), text = "Page 2", fontWeight = FontWeight.Bold, fontSize = 25.sp)
        Text(text = "Student ID: ${data.id} \n\nStudent Name: ${data.name} "+"\n\nAge: ${data.age}\n\nHobby: $hobbySelect\n", modifier = Modifier.padding(16.dp), fontSize = 20.sp)
        Button(modifier = Modifier.align(Alignment.CenterHorizontally),onClick = {
            navHostController.navigate(ScreenRoute.First.route){
                launchSingleTop = true
                popUpTo("first"){
                    inclusive = false
                }
            }
        }) {
            Text(text = "Go to Page1")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab5NavigationTheme {
        ComposeAllNavigation()
    }
}