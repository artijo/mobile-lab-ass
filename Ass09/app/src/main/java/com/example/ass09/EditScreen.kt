package com.example.ass09

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
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(navController: NavHostController) {
    val data = navController.previousBackStackEntry?.savedStateHandle?.get<Employee>("data") ?: Employee(0,"","","",0)
    var textFirldID by remember {
        mutableStateOf(data.emp_id.toString())
    }
    var textFieldName by remember {
        mutableStateOf(data.emp_name)
    }
    var textFieldEmail by remember {
        mutableStateOf(data.emp_email)
    }
    var textFieldSalary by remember {
        mutableStateOf(data.emp_salary.toString())
    }
    val contextForToast = LocalContext.current
    var genderValue by remember {
        mutableStateOf(data.emp_gender)
    }
    var deleteDialog by remember { mutableStateOf(false) }
    val createClient = EmployeeAPI.create()

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,) {
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "Edit a Employee", fontSize = 25.sp)

        OutlinedTextField(value = textFieldName, onValueChange = { textFieldName = it }, label = { Text(text = "Name") }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        )
        genderValue = EditRadioGroupUsage(genderValue)
        OutlinedTextField(value = textFieldEmail, onValueChange = { textFieldEmail = it }, label = { Text(text = "Email") }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        )


        OutlinedTextField(value = textFieldSalary, onValueChange = { textFieldSalary = it }, label = { Text(text = "Salary") }, modifier = Modifier
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
                createClient.updateEmployee(
                    textFirldID.toInt(),textFieldName,genderValue,textFieldEmail,textFieldSalary.toInt()
                )
                    .enqueue(object : Callback<Employee>{
                        override fun onResponse(call: Call<Employee>, response: Response<Employee>){
                            if(response.isSuccessful){

                                Toast.makeText(contextForToast,"Successfully  Update", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(contextForToast,"Update Failer", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call: Call<Employee>, t: Throwable){
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
                    title= { Text(text = "Warning") },
                    text = { Text(text = "Are tou sure you want delete ${textFieldName}?") },
                    confirmButton = {
                        TextButton(onClick = {
                            deleteDialog=false
                            Toast.makeText(contextForToast,"Yes, ${textFieldName} is deleted", Toast.LENGTH_SHORT).show()
                            createClient.deleteEmployee(textFirldID.toInt())
                                .enqueue(object : Callback<Employee>{
                                    override fun onResponse(call: Call<Employee>, response: Response<Employee>){
                                        if(response.isSuccessful){
                                            Toast.makeText(contextForToast,"Successfully  Delete", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(contextForToast,"Delete Failer", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    override fun onFailure(call: Call<Employee>, t: Throwable){
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
        text = "Employee Gender: ",
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