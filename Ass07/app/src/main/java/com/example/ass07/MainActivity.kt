package com.example.ass07

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ass07.ui.theme.Ass07Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ass07Theme {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen(){
    var memberItemsList = remember { mutableStateListOf<Member>() }
    val contextForToast = LocalContext.current.applicationContext

    var showDialog by remember { mutableStateOf(false) }
    var textFieldName by remember {
        mutableStateOf("")
    }
    var textFieldEmail by remember {
        mutableStateOf("")
    }
    var textFieldSalary by remember {
        mutableStateOf("")
    }
    var deleteDialog by remember {
        mutableStateOf(false)
    }
    val kinds = listOf("Male","Famale","Other")
    var (selected,setSelected) = remember {
        mutableStateOf("")
    }

    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(0.85f)) {
                Text(text = "Member Lists:", fontSize = 25.sp)
            }
            Button(onClick = {
                showDialog = true
            }) {
                Text(text = "Add Member")
            }
        }
        if(showDialog){
            AlertDialog(onDismissRequest = { showDialog=false },
                title = { Text(text = "Enter Information")},
                text = {
                    Column {
                        OutlinedTextField(value = textFieldName, onValueChange = {textFieldName=it}, label =  { Text(
                            text = "Enter your name"
                        )})
                        Gender(Items = kinds, selected,setSelected)
                        OutlinedTextField(value = textFieldEmail, onValueChange = {textFieldEmail=it}, label =  { Text(
                            text = "Enter your Email"
                        )})
                        OutlinedTextField(value = textFieldSalary, onValueChange = {textFieldSalary=it}, label =  { Text(
                            text = "Enter your Salary"
                        )})
                        Column {


                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog=false
                        var hb = ""

                        memberItemsList.add(Member(textFieldName,selected,textFieldEmail,textFieldSalary.toInt()))
                    }) {
                        Text(text = "Register")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false
                        textFieldName=""
                        textFieldEmail=""
                        textFieldSalary=""
                    }) {
                        Text(text = "Cancel")
                    }
                }
            )
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)){
            var itemClick = Member("","", "", 0)
            itemsIndexed(items = memberItemsList,){
                    index, item->
                Card(modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .height(120.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                    onClick = { Toast.makeText(contextForToast,"Click on ${item.name}.", Toast.LENGTH_SHORT).show()}
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(Dp(120f))
                        .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Name: ${item.name}\nGenger: ${item.gender}\nEmail: ${item.email}\nSalary: ${item.salary}", modifier = Modifier.weight(0.85f))
                        TextButton(onClick = {
                            itemClick=item
                            deleteDialog=true
                        }) {
                            Text(text = "Delete", modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically))
                        }
                    }
                    if(deleteDialog){
                        AlertDialog(onDismissRequest = { deleteDialog = false },
                            title= { Text(text = "Warning")},
                            text = { Text(text = "Are tou sure you want delete ${itemClick.name}?")},
                            confirmButton = {
                                TextButton(onClick = {
                                    deleteDialog=false
                                    Toast.makeText(contextForToast,"Yes, ${itemClick.name} is deleted", Toast.LENGTH_SHORT).show()
                                    memberItemsList.remove(itemClick)
                                }) {
                                    Text(text = "Yes")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    deleteDialog=false
                                }) {
                                    Text(text = "No")
                                }
                            },

                            )
                    }
                }
            }
        }
    }
}

@Composable
fun Gender(Items: List<String>,selected: String, setSelected: (selected:String)->Unit){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            Text(text = "Gender: $selected", textAlign = TextAlign.Start, modifier = Modifier.padding(start = 5.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Items.forEach { item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selected == item,
                        onClick = { setSelected(item) },
                        enabled = true,
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)
                    )
                    Text(text = item)
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ass07Theme {
        Greeting("Android")
    }
}