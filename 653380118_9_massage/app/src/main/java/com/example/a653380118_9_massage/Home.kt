package com.example.a653380118_9_massage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Home(navHostController: NavHostController){
    var name by remember { mutableStateOf("") }
    var bkdate by remember { mutableStateOf("") }
    var rType by remember  { mutableStateOf("") }
    var rMS by remember { mutableStateOf("") }
    var tMS by remember { mutableStateOf("") }

    val kinds = listOf("Private Room","Normal Room")
    var (roomselected,setroomSelected) = remember {
        mutableStateOf("")
    }

    val mstype = listOf(
        "Select Massage",
        "Thai massage",
        "Foot massage",
        "Aromatherapy massage"
    )
    var (mstypeselected,setmstypeselected) = remember { mutableStateOf(mstype[0]) }

    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(16.dp)) {
        Text(text = "Lab Midterm Exam", fontSize = 32.sp,textAlign = TextAlign.Center, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Massage & Spa Shop", fontSize = 36.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        OutlinedTextField(value = name, onValueChange = {newName->name=newName}, label = { Text(text = "Name") }, modifier = Modifier.width(400.dp))
        OutlinedTextField(value = bkdate, onValueChange = {bkdate=it}, label = { Text(text = "Booking Date") }, modifier = Modifier.width(400.dp))
        RoomType(Items = kinds, roomselected,setroomSelected)
        MSType(mstype = mstype, mstypeselected = mstypeselected, setmstypeSelected = setmstypeselected)
        OutlinedTextField(value = tMS, onValueChange = {tMS=it}, label = { Text(text = "Massage time") }, modifier = Modifier.width(400.dp))
        Button(onClick = {
            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                "data",Content(name = name, bkdate = bkdate, roomtype = roomselected, mstype= mstypeselected, mstime = tMS.toInt())
            )
            navHostController.navigate(Screen.Second.route)
        }, modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            Text(text = "Submit")
        }
    }
}

@Composable
fun RoomType(Items: List<String>,roomselected: String, setroomSelected: (roomselected:String)->Unit){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            Text(text = "Room Type: $roomselected", textAlign = TextAlign.Start, modifier = Modifier.padding(start = 5.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Items.forEach { item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = roomselected == item,
                        onClick = { setroomSelected(item) },
                        enabled = true,
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Magenta)
                    )
                    Text(text = item, modifier = Modifier.padding(start = 5.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MSType(mstype: List<String>, mstypeselected: String, setmstypeSelected: (mstypeselected: String) -> Unit){
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {expanded=!expanded}) {
        OutlinedTextField(value = mstypeselected, onValueChange = {}, label = { Text(text = "Massage Type") }, trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(
            expanded = expanded
        )}, colors = ExposedDropdownMenuDefaults.textFieldColors(), modifier = Modifier
            .menuAnchor()
            .width(400.dp), readOnly = true)
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            mstype.forEach{item->
                DropdownMenuItem(text = { Text(text = item) }, onClick = {
                    setmstypeSelected(item)
                    expanded = false
                }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding, modifier = Modifier.width(400.dp))
            }
        }
    }
}