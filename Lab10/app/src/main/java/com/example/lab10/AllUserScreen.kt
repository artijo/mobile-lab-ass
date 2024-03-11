package com.example.lab10

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun ShowAllUserScreen(navController: NavHostController) {
    val createClient = StudentAPI.create()
    var studentItemsList = remember { mutableStateListOf<ProfileClass>() }
    val contextForToast = LocalContext.current.applicationContext
    var textFieldID by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboadrdController = LocalSoftwareKeyboardController.current
    // Check Lifecycle State
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                showAllData(studentItemsList, contextForToast)
            }
        }
    }
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(text = "All Students:")
            Button(onClick = {
                keyboadrdController?.hide()
                focusManager.clearFocus()
                if (navController.currentBackStack.value.size>=2){
                    navController.popBackStack()
                }
                navController.navigate(Screen.Profile.route)
            }) {
                Text(text = "Back to profile")
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            var itemClick = ProfileClass("",  "",  "","")
            itemsIndexed(
                items = studentItemsList,
                itemContent = { index, item ->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .height(130.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),
                        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                        onClick = {
                            Toast.makeText(
                                contextForToast, "Click on ${item.std_name}.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(Dp(value = 130f))
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Student ID: ${item.std_id}\n" +
                                        "Name: ${item.std_name}\n" +
                                        "Gender: ${item.std_gender}\n" +
                                        "Role: ${item.role}",
                                Modifier.weight(0.85f)
                            )

                        }
                    }
                })   }   }   }
fun showAllData(studentItemsList: MutableList<ProfileClass>, context: Context) {
    val createClient = StudentAPI.create()
    createClient.showalluser()
        .enqueue(object : Callback<List<ProfileClass>> {
            override fun onResponse(
                call: Call<List<ProfileClass>>,
                response: Response<List<ProfileClass>>
            ) {
                studentItemsList.clear()
                response.body()?.forEach {
                    studentItemsList.add(
                        ProfileClass(
                            it.std_id,
                            it.std_name,
                            it.std_gender,
                            it.role
                        )
                    )
                }
            }

            override fun onFailure(call: Call<List<ProfileClass>>, t: Throwable) {
                Toast.makeText(context, "Error onFailure " + t.message, Toast.LENGTH_LONG)
                    .show()
            }
        })

}