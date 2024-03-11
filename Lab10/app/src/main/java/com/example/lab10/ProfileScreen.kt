package com.example.lab10

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("RestrictedApi")
@Composable
fun ProfileScreen(navController: NavHostController){
    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context = contextForToast)

    val userId = sharedPreferences.userId ?: ""

    val createClient = StudentAPI.create()

    val initialStudent = ProfileClass("","","","")
    var studentItems by remember {
        mutableStateOf(initialStudent)
    }

    var logoutDialog by remember { mutableStateOf(false) }
    var checkState by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState){
        when(lifecycleState){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                createClient.searchStudent(userId).enqueue(object : Callback<ProfileClass> {
                    override fun onResponse(call: Call<ProfileClass>, response: Response<ProfileClass>) {
                        if (response.isSuccessful) {
                            studentItems = ProfileClass(
                                response.body()!!.std_id,
                                response.body()!!.std_name,
                                response.body()!!.std_gender,
                                response.body()!!.role
                            )
                        }else {
                            Toast.makeText(contextForToast, "Student ID Not Found", Toast.LENGTH_SHORT).show()

                        }
                    }
                    override fun onFailure(call: Call<ProfileClass>, t: Throwable) {
                        Toast.makeText(contextForToast, "Error", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Profile", fontSize = 25.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Student ID: ${studentItems.std_id}\nName: ${studentItems.std_name}\nGender: ${studentItems.std_gender}\nRole: ${studentItems.role}", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))

        val isInvisible = studentItems.role == "admin"
        Box(
            content = {
                if (isInvisible) {
                    Button(onClick = {
                        if (navController.currentBackStack.value.size>=2){
                            navController.popBackStack()
                        }
                        navController.navigate(Screen.ShowAllUser.route)
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)) {
                        Text(text = "Show all students")
                    }
                }
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            logoutDialog = true
        }, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)) {
            Text(text = "Logout")
        }
        if(logoutDialog){
            AlertDialog(
                onDismissRequest = {
                    logoutDialog = false
                },
                title = {
                    Text(text = "Logout")
                },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Are you sure you want to logout?")
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Checkbox(checked = checkState, onCheckedChange = { isChecked ->
                                checkState = isChecked
                            })
                            Text(text = "Remember by e-mail")
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        logoutDialog = false
                        if(checkState){
                            sharedPreferences.clearUserLogin()
                        }else{
                            sharedPreferences.clearUserAll()
                        }
                        if(navController.currentBackStack.value.size >= 2){
                            navController.popBackStack()
                        }
                        navController.navigate(Screen.Login.route)
                    }) {
                        Text(text = "Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        logoutDialog = false
                    }) {
                        Text(text = "No")
                    }
                }
            )
        }
    }
}