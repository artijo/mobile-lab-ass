package com.example.lab10

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(navController: NavHostController){
    val contextForToast = LocalContext.current
    val createClient = StudentAPI.create()

    var studentID by remember { mutableStateOf("") }
    var studentName by remember {
        mutableStateOf("")
    }
    val gender = listOf("Male","Female","Other")
    var (selected , setSelected) = rememberSaveable { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isButtonEnabled by remember { mutableStateOf(false) }

   val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Register", fontSize = 25.sp)
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(value = studentID, onValueChange = {
            studentID = it
            isButtonEnabled = studentID.isNotEmpty() && password.isNotEmpty()
        }, label = { Text(text = "Student ID") }, keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next), leadingIcon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = null)
        }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = studentName,
            onValueChange = {studentName=it
                isButtonEnabled = validateInput(studentID,studentName,password,selected)
            },
            label = { Text(text = "Name")},
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null)},
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(20.dp))
        MyRadioButton(mItems = gender,selected,setSelected)
        Spacer(modifier = Modifier.padding(16.dp))
        OutlinedTextField(value = password, onValueChange = {
            password = it
            isButtonEnabled = validateInput(studentID, studentName,password, selected)

        }, label = { Text(text = "Password") }, keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done), leadingIcon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = null)
        }, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier.height(50.dp).fillMaxWidth(), enabled = isButtonEnabled,onClick = {
            keyboardController?.hide()
            focusManager.clearFocus()
            createClient.registerStudent(
                studentID,
                studentName,
                    password,
                selected
            ).enqueue(object : Callback<LoginClass> {
                @SuppressLint("RestrictedApi")
                override fun onResponse(call: Call<LoginClass>, response: Response<LoginClass>) {
                    if (response.isSuccessful) {
                        Toast.makeText(contextForToast, "Register Success", Toast.LENGTH_SHORT).show()
                        if(navController.currentBackStack.value.size >= 2){
                            navController.popBackStack()
                        }
                        navController.navigate(Screen.Login.route)
                    }else {
                        Toast.makeText(contextForToast, "Register Failed", Toast.LENGTH_SHORT).show()

                    }
                }
                override fun onFailure(call: Call<LoginClass>, t: Throwable) {
                    Toast.makeText(contextForToast, "Error onFailure " + t.message, Toast.LENGTH_LONG).show()
                }
            }
            )
        }) {
            Text(text = "Register")
        }
    }


}
fun validateInput(stdid:String,stdname:String,password:String,selected: String):Boolean{
    return !stdid.isNullOrEmpty()&&!stdname.isNullOrEmpty()&&!password.isNullOrEmpty()&&!selected.isNullOrEmpty()
}

@Composable
fun MyRadioButton(mItems:List<String>, selected: String, setSelected:(selected:String)->Unit){
    Column(
        modifier = Modifier
            .padding(start = 16.dp)
    ){
        Text(
            text = "Student Gender :",
            textAlign = TextAlign.Start,
            fontSize = 23.sp,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            mItems.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected == item,
                        onClick = {
                            setSelected(item)
                        },
                        enabled = true,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Magenta
                        )
                    )
                    Text(text = item, modifier = Modifier.padding(start = 5.dp))
                }
            }
        }
    }
}
