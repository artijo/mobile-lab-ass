package com.example.ass08

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertScreen(navController: NavHostController){
    val createClient = EmployeeAPI.create()
    val contectForToast = LocalContext.current.applicationContext
    var textFieldName by remember {
        mutableStateOf("")
    }
    var textFieldEmail by remember {
        mutableStateOf("")
    }
    var textFieldSalary by remember{
        mutableStateOf("")
    }
    val kinds = listOf("Male","Famale","Other")
    var (selected,setSelected) = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "Insert New Employee", fontSize = 25.sp)
        OutlinedTextField(value = textFieldName, onValueChange ={textFieldName=it}, label = { Text(text = "Name") }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) )
        ///
        Gender(Items = kinds, selected,setSelected)
        OutlinedTextField(value = textFieldEmail, onValueChange ={textFieldEmail=it}, label = { Text(text = "Email") }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) )
        OutlinedTextField(value = textFieldSalary, onValueChange ={textFieldSalary=it}, label = { Text(text = "Saraly") }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Button(modifier = Modifier.width(130.dp),onClick = {
                ///
                createClient.insertStd(textFieldName,selected,textFieldEmail,textFieldSalary.toInt())
                    .enqueue(object : Callback<Employee> {
                        override fun onResponse(call: Call<Employee>, response: Response<Employee>){
                            if (response.isSuccessful){
                                Toast.makeText(contectForToast,"Successfully Inserted ", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(contectForToast,"Inserted Failed ", Toast.LENGTH_LONG).show()
                            }
                        }
                        override fun onFailure(call: Call<Employee>, t: Throwable){
                            Toast.makeText(contectForToast,"Error onFailure "+t.message, Toast.LENGTH_LONG).show()
                        }
                    })
                textFieldName=""
                textFieldEmail=""
                setSelected("")
                textFieldSalary=""
                navController.navigateUp()
            }) {
                Text(text = "Save")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(modifier = Modifier.width(130.dp),onClick = {
                textFieldName=""
                textFieldEmail=""
                setSelected("")
                textFieldSalary=""
                if(navController.currentBackStack.value.size >= 2){
                    navController.popBackStack()
                }
                navController.navigate(Screen.Home.route)
            }) {
                Text(text = "Cancel")
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