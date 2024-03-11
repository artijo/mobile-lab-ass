package com.example.lab7

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab7.ui.theme.Lab7Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab7Theme {
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
    var studentItemsList = remember { mutableListOf<Student>() }
    val contextForToast = LocalContext.current.applicationContext

    var showDialog by remember { mutableStateOf(false) }
    var textFieldID by remember {
        mutableStateOf("")
    }
    var textFieldName by remember {
        mutableStateOf("")
    }
    var textFieldAge by remember {
        mutableStateOf("")
    }
    val hobbyList = listOf("Reading","Painting","Cooking")
    var selectedOut by remember {
        mutableStateOf("")
    }
    val selectedItems by remember {
        mutableStateOf(mutableListOf<String>())
    }
    var deleteDialog by remember {
        mutableStateOf(false)
    }
    
    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(0.85f)) {
                Text(text = "Student Lists:", fontSize = 25.sp)
            }
            Button(onClick = {
                showDialog = true
            }) {
                Text(text = "Add Student")
            }
        }
        if(showDialog){
           AlertDialog(onDismissRequest = { showDialog=false }, 
               title = { Text(text = "Enter Information")},
               text = {
                      Column {
                          OutlinedTextField(value = textFieldID, onValueChange = {textFieldID=it}, label =  { Text(
                              text = "Enter your ID"
                          )})
                          OutlinedTextField(value = textFieldName, onValueChange = {textFieldName=it}, label =  { Text(
                              text = "Enter your name"
                          )})
                          OutlinedTextField(value = textFieldAge, onValueChange = {textFieldAge=it}, label =  { Text(
                              text = "Enter your Age"
                          )}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                          Text(text = "Select your hobby:", modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp))
                          Column {
                              CheckboxGroup(items = hobbyList){ newSelectedItems->
                                  selectedItems.clear()
                                  selectedItems.addAll(newSelectedItems)
                                  Log.d("CheckboxGroup","Selected items: $selectedItems")
                                  selectedOut = selectedItems.toString()
                              }

                          }
                      }
               },
               confirmButton = { 
                   TextButton(onClick = { 
                        showDialog=false
                       var hb = ""
                       selectedItems.forEach{item->
                           hb+="${item}, "
                       }
                       if (hb.endsWith(", ")) {
                           hb = hb.substring(0, hb.length - 2)
                       }
                       studentItemsList.add(Student(textFieldID,textFieldName,textFieldAge.toInt(),hb))
                   }) {
                       Text(text = "Save")
                   }
               },
               dismissButton = {
                   TextButton(onClick = { showDialog = false
                            textFieldID=""
                       textFieldName=""
                       textFieldAge=""
                   }) {
                       Text(text = "Cancel")
                   }
               }
               )
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)){
            var itemClick = Student("","", 0, "")
            itemsIndexed(items = studentItemsList,){
                index, item->
                Card(modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .height(120.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                    onClick = {Toast.makeText(contextForToast,"Click on ${item.name}.",Toast.LENGTH_SHORT).show()}
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(Dp(120f))
                        .padding(10.dp)) {
                        Text(text = "ID: ${item.id}\n"+"Name: ${item.name}\nAge: ${item.age}\nHobby: ${item.hobby}", modifier = Modifier.weight(0.85f))
                        TextButton(onClick = { 
                            itemClick=item
                            deleteDialog=true
                        }) {
                            Text(text = "Delete")
                        }
                    }
                    if(deleteDialog){
                        AlertDialog(onDismissRequest = { deleteDialog = false },
                            title= { Text(text = "Warning")},
                            text = { Text(text = "Are tou sure you want delete ${item.name}?")},
                            confirmButton = {
                                TextButton(onClick = { 
                                    deleteDialog=false
                                    Toast.makeText(contextForToast,"Yes, ${itemClick.name} is deleted", Toast.LENGTH_SHORT).show()
                                    studentItemsList.remove(itemClick)
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
    Lab7Theme {
        Greeting("Android")
    }
}