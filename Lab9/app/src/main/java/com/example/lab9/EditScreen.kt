package com.example.lab9

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Response

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(navController: NavHostController) {
    val data = navController.previousBackStackEntry?.savedStateHandle?.get<Student>("data") ?:Student("","","",0)
    var textFirldID by remember {
        mutableStateOf(data.std_id)
    }
    var textFieldName by remember {
        mutableStateOf(data.std_name)
    }
    var textFieldAge by remember {
        mutableStateOf(data.std_age.toString())
    }
    val contextForToast = LocalContext.current
    var genderValue by remember {
        mutableStateOf(data.std_gender)
    }
    var deleteDialog by remember { mutableStateOf(false) }
    val createClient = StudentAPI.create()
    
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,) {
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "Edit a Student", fontSize = 25.sp)
        
        OutlinedTextField(value = textFirldID, onValueChange = { textFirldID = it }, label = { Text(text = "Student ID") }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), enabled = false
        )
        OutlinedTextField(value = textFieldName, onValueChange = { textFieldName = it }, label = { Text(text = "Student Name") }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        )
        genderValue = EditRadioGroupUsage(genderValue)

        OutlinedTextField(value = textFieldAge, onValueChange = { textFieldAge = it }, label = { Text(text = "Student Age") }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
            Button(onClick = {
                deleteDialog=true
            }, modifier = Modifier.width(100.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Text(text = "Delete")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = {
                createClient.updateStudent(
                    textFirldID,textFieldName,genderValue,textFieldAge.toInt()
                )
                    .enqueue(object : retrofit2.Callback<Student>{
                        override fun onResponse(call: Call<Student>, response: Response<Student>){
                            if(response.isSuccessful){

                                Toast.makeText(contextForToast,"Successfully  Update", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(contextForToast,"Update Failer", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<Student>, t: Throwable){
                            Toast.makeText(contextForToast,"Error onFailure "+t.message, Toast.LENGTH_LONG).show()
                        }
                    })
                navController.navigateUp()
            }, modifier = Modifier.width(100.dp)) {
                Text(text = "Update")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = {
                if(navController.currentBackStack.value.size >= 2){
                    navController.popBackStack()
                }
                navController.navigateUp()
            }, modifier = Modifier.width(100.dp)) {
                Text(text = "Cancel")
            }

            if(deleteDialog){
                AlertDialog(onDismissRequest = { deleteDialog = false },
                    title= { Text(text = "Warning")},
                    text = { Text(text = "Are tou sure you want delete ${textFieldName}?")},
                    confirmButton = {
                        TextButton(onClick = {
                            deleteDialog=false
                            Toast.makeText(contextForToast,"Yes, ${textFieldName} is deleted", Toast.LENGTH_SHORT).show()
                            createClient.deleteStudent(textFirldID)
                                .enqueue(object : retrofit2.Callback<Student>{
                                    override fun onResponse(call: Call<Student>, response: Response<Student>){
                                        if(response.isSuccessful){
                                            Toast.makeText(contextForToast,"Successfully  Delete", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(contextForToast,"Delete Failer", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    override fun onFailure(call: Call<Student>, t: Throwable){
                                        Toast.makeText(contextForToast,"Error onFailure "+t.message, Toast.LENGTH_LONG).show()
                                    }
                                })
                            navController.navigateUp()
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
@Composable
fun EditRadioGroupUsage(s: String): String {
    val kinds = listOf("Male", "Female", "Other")
    var (selected, setSelected) = remember { mutableStateOf(s) }
    Text(
        text = "Student Gender: ",
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 10.dp),
    )
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp)) {
        EditRadioGroup(
            mItems = kinds,
            selected, setSelected
        )
    }
    return selected
}

@Composable
fun EditRadioGroup(
    mItems: List<String>,
    selected: String,
    setSelected: (selected: String) -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        mItems.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selected == item,
                    onClick = { setSelected(item) },
                    enabled = true,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Green
                    )
                )
                Text(text = item, modifier = Modifier.padding(start = 5.dp))
            }
        }
    }
}
