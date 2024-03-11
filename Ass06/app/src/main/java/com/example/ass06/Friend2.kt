package com.example.ass06

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Friend2(){
    val contextForToast = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//        Text(text = "Profile Screen")
//        Button(onClick = { Toast.makeText(contextForToast, "This is Profile Screen", Toast.LENGTH_SHORT).show() }) {
//            Text(text = "Click")
//        }
        Image(painter = painterResource(id = R.drawable.tha), contentDescription = "tha", modifier = Modifier.width(200.dp))
        Text(text = "ฐากูร", fontSize = 26.sp)
    }
}