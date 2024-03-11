package com.example.lab4ui2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.lab4ui2.ui.theme.Lab4UI2Theme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4UI2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
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
        OutlinedTextField(value = id, onValueChange = onIDChange, modifier = Modifier.width(400.dp), label = { Text( text ="Student ID") })
        OutlinedTextField(value = name, onValueChange = onNameChane, modifier = Modifier.width(400.dp), label = { Text(
            text = "Name"
        )})

    }
}
@Composable
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
    var subject by remember {
        mutableStateOf("")
    }
    var credit by remember {
        mutableStateOf("")
    }

    var hour by remember {
        mutableStateOf("")
    }
    var minute by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Enter Student Information", fontWeight = FontWeight.Bold, fontSize = 25.sp, modifier = Modifier.padding(5.dp))
        IdNameContext(id = id, onIDChange ={id=it} , name =name , onNameChane ={name = it} )
        subject = SubjectDropdown()
        CreditContent(credit = credit, onCreditChange = {credit=it} )
        var (hourValue, minuteValue) = TimeContent()
        hour = if(hourValue<10) "0${hourValue}" else "$hourValue"
        minute = if(minuteValue<10) "0${minuteValue}" else "$minuteValue"
        Spacer(modifier = Modifier.height(height = 8.dp))
        Button(onClick = {
            textInformation = ""
            textInformation = "Student Name: $name \n"+" ID: $id \n"+"Subject: $subject \n"+"Credit: $credit\n"+"Time: $hour : $minute"
            Toast.makeText(contextForToast,"This $textInformation", Toast.LENGTH_LONG).show() }) {
            Text(text = "Show Information")
        }
        Column(modifier = Modifier
            .width(400.dp)
            .padding(16.dp)
            .wrapContentHeight(unbounded = true)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))) {
            Text(text = "Student Information: ", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            Text(text = textInformation, fontSize = 18.sp, modifier = Modifier.padding(5.dp))
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDropdown():String{
    val keyboardController = LocalSoftwareKeyboardController.current
    val subjectList = listOf(
        "Select Subject",
        "SC362007 Mobile Device Programming",
        "SC362004 Web Applicatin Programing",
        "SC362005 Database Analysis and Design"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedSubject by remember {
        mutableStateOf(subjectList[0])
    }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {expanded = !expanded}, modifier = Modifier.clickable { keyboardController?.hide() }) {
        OutlinedTextField(value = selectedSubject, onValueChange = {}, textStyle = TextStyle.Default.copy(fontSize = 12.sp), readOnly = true, label = { Text(
            text = "Subject"
        )}, trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)}, colors = ExposedDropdownMenuDefaults.textFieldColors(),modifier = Modifier
            .width(340.dp)
            .menuAnchor()
            .clickable { keyboardController?.hide() })
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            subjectList.forEach { selectionOption ->
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
@Composable
fun CreditContent(credit:String,onCreditChange: (String) -> Unit){
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        OutlinedTextField(value = credit, onValueChange = onCreditChange, label = { Text("Credit")}, modifier = Modifier.width(400.dp), keyboardOptions  = KeyboardOptions(keyboardType = KeyboardType.Number))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeContent():Pair<Int, Int>{
    var selectedHour by remember {
        mutableStateOf(0)
    }
    var selectedMinute by remember {
        mutableStateOf(0)
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute
    )
    if(showDialog){
        AlertDialog(onDismissRequest = { showDialog = false }, properties = DialogProperties(usePlatformDefaultWidth = false), modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 12.dp)
            )) {
            Column(modifier = Modifier.background(color = Color.LightGray.copy(alpha = 0.3f)), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                TimePicker(state = timePickerState)
                Row(modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = "Dismiss")
                    }
                    TextButton(onClick = {
                        showDialog = false
                        selectedHour = timePickerState.hour
                        selectedMinute = timePickerState.minute
                    }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
        Text(text = "Select Time", modifier = Modifier.padding(5.dp))
        FilledIconButton(onClick = {showDialog = true}) {
            Icon(modifier = Modifier.size(size = 30.dp), imageVector = Icons.Outlined.DateRange, contentDescription = "Time Icon" )
        }
        Text(text = "(HH:MM) = $selectedHour : $selectedMinute")
    }
    return selectedHour to selectedMinute
}

@Composable
fun MainScreen(){
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
    var information by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Register Form", fontWeight = FontWeight.Bold, fontSize = 25.sp)
        UserPass(Username = username, onUsernameChange = {username=it}, Password = password, onPasswordChange = {password=it})
        Gender(Items = kinds, selected,setSelected)
        Email(email = email, onEmailChange = {email=it})
        var bd = Birthday()
        Button(onClick = {
            information = "Name: $username \nPassword: $password \nGender: $selected \nEmail: $email \n Birthday: $bd"
        }) {
            Text(text = "Register")
        }
        Column(modifier = Modifier
            .width(400.dp)
            .padding(16.dp)
            .wrapContentHeight(unbounded = true)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
            .background(color = Color(0xFFB2FF66))) {
            Text(text = "Register Information: ", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            Text(text = information, fontSize = 18.sp, modifier = Modifier.padding(5.dp))
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
        KeyboardOptions(keyboardType = KeyboardType.Email))
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4UI2Theme {
        MyScreen()
    }
}