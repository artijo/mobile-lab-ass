package com.example.lab3layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.lab3layout.ui.theme.Lab3LayoutTheme
import androidx.compose.material3.Text as Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab3LayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
//                    RowLayout(messege1 = "World", messege2 = "We are cats")
                    Bday()
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
fun RowLayout(messege1: String, messege2: String, modifier : Modifier = Modifier){
    Row {
        Text(
            text = "Hello $messege1!",
            style = TextStyle(background = Color.Yellow),
            fontSize = 30.sp,
            modifier = modifier
        )
        Text(
            text = "$messege2!",
            style = TextStyle(background = Color.Gray),
            fontSize = 20.sp,
            modifier = Modifier.padding(5.dp)
        )
        Image(painter = painterResource(R.drawable.cartoon_cat), contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier.size(100.dp))
    }
}

@Composable
fun boxLayout(){
    Box(modifier = Modifier
        .background(Color(0.447f, 0.878f, 0.933f, 0.749f))
        .fillMaxSize()){
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .background(Color.Yellow)
                .padding(10.dp),
            text = "TopStart",
            fontSize = 20.sp
        )

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(Color.Yellow)
                .padding(10.dp),
            text = "TopCenter",
            fontSize = 20.sp
        )

        Text(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(Color.Yellow)
                .padding(10.dp),
            text = "TopEnd",
            fontSize = 20.sp
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .background(Color.Yellow)
                .padding(10.dp),
            text = "CenterStart",
            fontSize = 20.sp
        )

        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color(255, 192, 203))
                .padding(10.dp),
            text = "Center",
            fontSize = 20.sp
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .background(Color(255, 192, 203))
                .padding(10.dp),
            text = "CenterEnd",
            fontSize = 20.sp
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(Color(255, 192, 203))
                .padding(10.dp),
            text = "CenterEnd",
            fontSize = 20.sp
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Color(255, 192, 203))
                .padding(10.dp),
            text = "CenterEnd",
            fontSize = 20.sp
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(Color(255, 192, 203))
                .padding(10.dp),
            text = "CenterEnd",
            fontSize = 20.sp
        )
    }
}

@Composable
fun boxLayout2(){
    Box(modifier = Modifier
        .wrapContentSize(unbounded = true, align = CenterEnd)
        .border(
            width = 1.dp,
            color = Color.Green,
            shape = RoundedCornerShape(32.dp)
        )){
        Image(painter = painterResource(id = R.drawable.cartoon_cat), contentDescription = "cate image",
            contentScale = ContentScale.Fit, modifier = Modifier.size(300.dp))
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            text = "We are cats.",
            fontSize = 30.sp,
            color = Color(50,50,250)
        )
    }
}

@Composable
fun ConstraintLayoutEx(){
    ConstraintLayout (
        modifier = Modifier.fillMaxSize()
    )
    {
        val (firstText,secondText,catImage) = createRefs()
        Text(
            text = "Hello World!",
            style = TextStyle(background = androidx.compose.ui.graphics.Color.Yellow),
            fontSize = 30.sp,
            modifier = androidx.compose.ui.Modifier.constrainAs(firstText) {
                centerHorizontallyTo(parent)
            })
        Text(
            text = "We are cats!",
            style = TextStyle(background = Color.Gray),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(5.dp)
                .constrainAs(secondText) {
                    top.linkTo(catImage.bottom)
                    centerHorizontallyTo(catImage)
                })
        Image(painter = painterResource(R.drawable.cartoon_cat), contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(300.dp)
                .border(width = 0.8.dp, color = Color.Blue, shape = RoundedCornerShape(10.dp))
                .constrainAs(catImage) {
                    top.linkTo(firstText.bottom)
                    centerHorizontallyTo(firstText)
                }
            )
    }
}

@Composable
fun BackGroundBox(){
    Box(modifier = Modifier.fillMaxSize())
    {
        Image(painter = painterResource(R.drawable.background), contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.3F
            )
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(unbounded = true, align = TopEnd)
        .border(
            width = 1.dp,
            color = Color.Green.copy(alpha = 0.5f),
            shape = RoundedCornerShape(28.dp)
        )){
        Image(painter = painterResource(R.drawable.cartoon_cat), contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(200.dp)
            )
        Text(
            text = "We are cate!",
            fontSize = 25.sp,
            color = Color.Magenta,
            modifier = Modifier
                .padding(5.dp)
                .align(BottomCenter)
        )
    }
}

@Composable
fun Bday(){
    Box(modifier = Modifier.fillMaxSize())
    {
        Image(painter = painterResource(R.drawable.bd), contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.3F,
            modifier = Modifier.fillMaxSize()
        )
    }

    ConstraintLayout (
        modifier = Modifier.fillMaxSize().wrapContentSize(Center)
    )
    {
        val (Text,Image) = createRefs()
        Text(
            text = "สุขสันวันเกิดจ้า มีความสุขหลายๆเด้อ",
            color = Color.Red,
            fontSize = 30.sp,
            modifier = Modifier.constrainAs(Text) {
                centerHorizontallyTo(parent)
            }
                .padding(horizontal = 16.dp)
        )
        Image(painter = painterResource(R.drawable.pinat), contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(200.dp)
                .constrainAs(Image) {
                    top.linkTo(Text.bottom)
                    centerHorizontallyTo(Text)
                }
        )
    }
    ConstraintLayout (
        modifier = Modifier.fillMaxSize().wrapContentSize(BottomEnd)
    )
    {
        val (Text,Image) = createRefs()
        Text(
            text = "จาก ARTIJO",
            color = Color.Green,
            fontSize = 30.sp,
            modifier = Modifier.constrainAs(Text) {
                centerHorizontallyTo(parent)
            })
        Image(painter = painterResource(R.drawable.art), contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(150.dp)
                .constrainAs(Image) {
                    top.linkTo(Text.bottom)
                    centerHorizontallyTo(Text)
                }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab3LayoutTheme {
        Bday()
    }
}