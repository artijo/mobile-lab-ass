package com.example.lab4ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab4ui.ui.theme.Lab4UITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4UITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreen()
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
@Composable
fun IdNameContext(id: String, onIDChange: (String)-> Unit, name: String, onNameChane: (String)-> Unit)
{
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        OutlinedTextField(value = id, onValueChange = onIDChange, modifier = Modifier.width(400.dp), label = { Text( text ="Student ID") }),
        OutlinedTextField(value = name, onValueChange = onIDChange, modifier = Modifier.width(400.dp), label = { Text(
            text = "Name"
        )})

    }
}
fun MyScreen(){
    val contextForToast = LocalContext.current.applicationContext
    var textInformation by rememberSaveable {
        mutableStateOf("Test")
    }
    var id by rememberSaveable {
        mutableStateOf("")
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Enter Student Information", fontWeight = FontWeight.Bold, fontSize = 25.sp, modifier = Modifier.padding(5.dp))
        IdNameContext(id = id, onIDChange ={id=it} , name =name , onNameChane ={name = it} )
        Spacer(modifier = Modifier.height(height = 8.dp))
        Button(onClick = { Toast.makeText(contextForToast,"This $id - $name", Toast.LENGTH_LONG).show() }) {
            Text(text = "Show Information")
        }
        Column(modifier = Modifier.width(400.dp).padding(16.dp).wrapContentHeight(unbounded = true).border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))) {
            Text(text = "Student Information: ", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            Text(text = textInformation, fontSize = 18.sp, modifier = Modifier.padding(5.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4UITheme {
        Greeting("Android")
    }
}