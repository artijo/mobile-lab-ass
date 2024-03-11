package com.example.a653380118_9_cocoa

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("RestrictedApi")
@Composable
fun InsertScreen(navController: NavHostController){
    val createClient = CocoaAPI.create()
    val contectForToast = LocalContext.current.applicationContext
    var textFirldCustomer by remember {
        mutableStateOf("")
    }
    var textFieldSweet by remember {
        mutableStateOf("")
    }
    var textFieldNumberofglass by remember {
        mutableStateOf("")
    }
    val kinds = listOf("S","M","L","XL")
    var (selected,setSelected) = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "Insert a new order", fontSize = 25.sp)
        OutlinedTextField(value = textFirldCustomer, onValueChange ={textFirldCustomer=it}, label = { Text(text = "Customer name") }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) )
        Gender(Items = kinds, selected,setSelected)
        textFieldSweet = sweetDropdown()
        OutlinedTextField(value = textFieldNumberofglass, onValueChange ={textFieldNumberofglass=it}, label = { Text(text = "Number of glass") }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Button(modifier = Modifier.width(130.dp),onClick = {
                var sweet = 0
                var price = 0
                when(textFieldSweet){
                    "0%" -> sweet = 0
                    "25%" -> sweet = 25
                    "50%" -> sweet = 50
                    "100%" -> sweet = 100
                }
                when(selected){
                    "S" -> price = 30
                    "M" -> price = 50
                    "L" -> price = 70
                    "XL" -> price = 90
                }

                var totalprice = price * textFieldNumberofglass.toInt()
                ///
                createClient.insertCocoa(textFirldCustomer,selected,textFieldNumberofglass.toInt(),sweet,totalprice)
                    .enqueue(object : Callback<Cocoa> {
                        override fun onResponse(call: Call<Cocoa>, response: Response<Cocoa>){
                            if (response.isSuccessful){
                                Toast.makeText(contectForToast,"Successfully Inserted ", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(contectForToast,"Inserted Failed ", Toast.LENGTH_LONG).show()
                            }
                        }
                        override fun onFailure(call: Call<Cocoa>, t: Throwable){
                            Toast.makeText(contectForToast,"Error onFailure "+t.message, Toast.LENGTH_LONG).show()
                        }
                    })
                textFirldCustomer=""
                textFieldSweet=""
                textFieldNumberofglass=""
                setSelected("")
                //back to home
                if(navController.currentBackStack.value.size >= 2){
                    navController.popBackStack()
                }
                navController.navigate(Screen.Home.route)
            }) {
                Text(text = "Save")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(modifier = Modifier.width(130.dp),onClick = {
                textFirldCustomer=""
                textFieldSweet=""
                textFieldNumberofglass=""
                setSelected("")
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
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            Text(text = "Glass size: $selected", textAlign = TextAlign.Start, modifier = Modifier.padding(start = 5.dp))
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
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun sweetDropdown():String{
    val keyboardController = LocalSoftwareKeyboardController.current
    val sweetList = listOf(
        "Select Sweet",
        "0%",
        "25%",
        "50%",
        "100%"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedSubject by remember {
        mutableStateOf(sweetList[0])
    }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {expanded = !expanded}, modifier = Modifier.clickable { keyboardController?.hide() }) {
        OutlinedTextField(value = selectedSubject, onValueChange = {}, textStyle = TextStyle.Default.copy(fontSize = 12.sp), readOnly = true, label = { Text(
            text = "Sweet"
        )}, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)}, colors = ExposedDropdownMenuDefaults.textFieldColors(),modifier = Modifier
            .width(360.dp)
            .menuAnchor()
            .clickable { keyboardController?.hide() })
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            sweetList.forEach { selectionOption ->
                DropdownMenuItem(text = { Text(selectionOption) },
                    onClick = {
                        selectedSubject = selectionOption
                        expanded = false
                    }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
            }
        }
    }
    return selectedSubject
}