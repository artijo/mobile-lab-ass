package com.example.a653380118_9_massage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SecondScreen(navHostController: NavHostController){
    var data = navHostController.previousBackStackEntry?.savedStateHandle?.get<Content>("data")?: Content("","","","",0)
    var roomtypeprice: Double = 0.0
    if(data.roomtype == "Private Room"){
        roomtypeprice = 100.0
    }
    var mstypeprice:Double = 0.0
    if(data.mstype == "Thai massage") {
        mstypeprice=300.0
    }
    else if(data.mstype=="Foot massage") {
        mstypeprice = 400.0
    }
    else if(data.mstype=="Aromatherapy massage"){
        mstypeprice =750.0
    }
    var result:Double = (roomtypeprice+mstypeprice)*data.mstime
    var discount:Double = 0.0
    if (data.mstime>=3){
        discount = ((mstypeprice*data.mstime)*10/100)
        result = roomtypeprice+(mstypeprice*data.mstime)-discount
    }
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(16.dp)) {
        Text(text = "Lab Midterm Exam", fontSize = 20.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
        Row {
            IconButton(modifier = Modifier.size(100.dp),onClick = {
                navHostController.navigateUp()
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "", tint = Color.Magenta)
            }
            Text(text = "Check Information", fontWeight = FontWeight.Bold, fontSize = 20.sp,textAlign = TextAlign.End)
        }


        Text(text = "Customer Name: ${data.name}\nBooking Date: ${data.bkdate}\nRoom Type: ${data.roomtype}\nMassage Type: ${data.mstype}\nMassage Time: ${data.mstime}\nPrice: ${result}", fontSize = 20.sp, modifier = Modifier.padding(16.dp))
        Button(onClick = { navHostController.navigateUp() }, modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            Text(text = "Close")
        }
    }
}