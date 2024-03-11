package com.example.ass5nav

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ass5nav.ui.theme.Ass5NavTheme
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ass5NavTheme {
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

@Parcelize
data class Register (
    var username: String,
    val password: String,
    val gender: String,
    val bd: String,
    val email: String
): Parcelable

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
            MainScreen(navController)
        }

        composable(ScreenRoute.Second.route) {
            secondScreen(navController)
        }
    }
}

@Composable
fun MainScreen(navHostController: NavHostController){
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var gender by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    val kinds = listOf("Male","Famale","Other")
    var (selected,setSelected) = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(modifier = Modifier
            .background(color = Color.LightGray, shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
            .align(Alignment.CenterHorizontally), text = "Page 1", fontWeight = FontWeight.Bold, fontSize = 25.sp)
        Text(text = "Register Form", fontWeight = FontWeight.Bold, fontSize = 25.sp)
        UserPass(Username = username, onUsernameChange = {username=it}, Password = password, onPasswordChange = {password=it})
        Gender(Items = kinds, selected,setSelected)
        Email(email = email, onEmailChange = {email=it})
        var bd = Birthday()
        Button(onClick = {
            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                "data",
                Register(username,password,selected, bd, email)

            )
            navHostController.navigate(ScreenRoute.Second.route)
        }) {
            Text(text = "Send Register")
        }
    }
}

@Composable
fun UserPass(Username:String, onUsernameChange: (String) -> Unit, Password:String, onPasswordChange: (String)-> Unit){
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        OutlinedTextField(value = Username, onValueChange = onUsernameChange, modifier = Modifier.width(400.dp), label = {Text(
            text = "Username"
        )})
        OutlinedTextField(value = Password, onValueChange = onPasswordChange, modifier = Modifier.width(400.dp), label = { Text(
            text = "Password"
        )}, visualTransformation = PasswordVisualTransformation('*'))
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
                    Text(text = item, modifier = Modifier.padding(start = 5.dp))
                }
            }
        }
    }
}

@Composable
fun Email(email: String, onEmailChange: (String)-> Unit){
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        OutlinedTextField(value = email, onValueChange = onEmailChange, label = { Text(text = "Email")}, modifier = Modifier.width(400.dp), keyboardOptions =
        KeyboardOptions(keyboardType = KeyboardType.Email)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Birthday():String{
    val calendar = Calendar.getInstance()
    val mYear = calendar.get(Calendar.YEAR)
    val mMonth = calendar.get(Calendar.MONTH)
    val mDay = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.set(mYear,mMonth,mDay)

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = calendar.timeInMillis
    )
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var selectedDate by remember {
        mutableLongStateOf(calendar.timeInMillis)
    }

    if(showDatePicker){
        DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = {
            TextButton(onClick = {
                showDatePicker = false
                selectedDate = datePickerState.selectedDateMillis!!
            }) {
                Text(text = "Confrime")
            }
        }, dismissButton = {
            TextButton(onClick = { showDatePicker = false }) {
                Text(text = "Cancel")
            }
        }) {
            DatePicker(state = datePickerState)
        }
    }
    val formatter = SimpleDateFormat("dd-MMM-yyyy")
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), verticalArrangement = Arrangement.Center) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Birthday")
            FilledIconButton(onClick = {showDatePicker = true}) {
                Icon(modifier = Modifier.size(size = 30.dp), imageVector = Icons.Outlined.DateRange, contentDescription = "Time Icon" )
            }
            Text(text = " : ${formatter.format(Date(selectedDate))}")
        }
    }
    return formatter.format(Date(selectedDate))
}

@Composable
fun secondScreen(navHostController: NavHostController){
    val data = navHostController.previousBackStackEntry?.savedStateHandle?.get<Register>("data") ?:
    Register("","","","","")

    Column {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentHeight()
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
            .background(color = Color(0xFFB2FF66))
        ) {
            Text(modifier = Modifier
                .background(color = Color.LightGray, shape = RoundedCornerShape(20.dp))
                .padding(16.dp)
                .align(Alignment.CenterHorizontally), text = "Page 2", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            Text(modifier = Modifier.padding(16.dp),text = "Register Information:\n\n Username: ${data.username}\n\n Password: ${data.password}\n\n" +
                    " Gender: ${data.gender}\n\n Email: ${data.email}\n\n Birthday: ${data.bd}")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ass5NavTheme {
        ComposeAllNavigation()
    }
}