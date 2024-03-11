package com.example.ass08

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Response

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController){
    val createClient = EmployeeAPI.create()
    var employeeItemsList = remember { mutableStateListOf<Employee>() }
    val contectForToast = LocalContext.current.applicationContext

    employeeItemsList.clear()
    createClient.retrieveStudent()
        .enqueue(object : retrofit2.Callback<List<Employee>>{
            override fun onResponse(call: Call<List<Employee>>, response: Response<List<Employee>>){
                response.body()?.forEach{
                    employeeItemsList.add(Employee(it.emp_name,it.emp_gender,it.emp_email,it.emp_salary))
                }
            }
            override fun onFailure(call: Call<List<Employee>>, t: Throwable){
                Toast.makeText(contectForToast,"Error onFailure "+t.message, Toast.LENGTH_LONG).show()
            }
        })
    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(0.85f)) {
                Text(text = "Employee Lists:", fontSize = 25.sp)
            }
            Button(onClick = {
                if (navController.currentBackStack.value.size >= 2){
                    navController.popBackStack()
                }
                navController.navigate(Screen.Insert.route)
            }) {
                Text(text = "Add Employee")

            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)){
            itemsIndexed(
                items = employeeItemsList,
            ){ index, item ->
                Card(modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .height(130.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), shape = RoundedCornerShape(corner = CornerSize(16.dp)), onClick = {
                    Toast.makeText(contectForToast,"Click on ${item.emp_name}", Toast.LENGTH_SHORT).show()
                }
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(Dp(130f))
                        .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text =
                                "Name: ${item.emp_name}\n"+
                                "Gender: ${item.emp_gender}\n"+
                                "E-mail: ${item.emp_email}\n"+
                                "salary: ${item.emp_salary}"
                        )
                    }
                }
            }
        }
    }
}