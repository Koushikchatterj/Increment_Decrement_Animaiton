package com.example.incrementdecrementanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.example.incrementdecrementanimation.data.storedata
import com.example.incrementdecrementanimation.ui.theme.IncrementDecrementAnimationTheme

class MainActivity : ComponentActivity() {
    lateinit var Storedata: storedata
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Storedata = ViewModelProvider(this).get(storedata::class.java)
            IncrementDecrementAnimationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IncDec(
                        Storedata,
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun IncDec(
    viewModel: storedata,
    modifier: Modifier = Modifier
) {
    val text by viewModel.counter.collectAsState()


    val buttonModifier = Modifier
        .size(45.dp)
        .padding(0.dp)

    Column(
        modifier = Modifier.height(1000.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(Color.White),
            shape = RoundedCornerShape(10.dp),
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                val (textRef) = createRefs()

                AnimatedContent(
                    targetState = text,
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInVertically { -it } with slideOutVertically { it }
                        } else {
                            slideInVertically { it } with slideOutVertically { -it }
                        }
                    },
                    label = "CounterAnimation",
                    modifier = Modifier.constrainAs(textRef) {
                        centerTo(parent)
                    }
                ) { targetText ->
                    Text(
                        text = targetText.toString(),
                        fontSize = 24.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Increment Button
            ElevatedButton(
                onClick = { viewModel.increment() },
                colors = ButtonDefaults.elevatedButtonColors(Color.White),
                modifier = buttonModifier,
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Increment",
                    tint = Color.Black
                )
            }

            // Decrement Button
            ElevatedButton(
                onClick = { viewModel.decrement() },
                colors = ButtonDefaults.elevatedButtonColors(Color.White),
                modifier = buttonModifier,
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = "Decrement",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Reset Button
        ElevatedButton(
            onClick = { viewModel.reset() },
            colors = ButtonDefaults.elevatedButtonColors(Color.White),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            Text("Reset", color = Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IncrementDecrementAnimationTheme {
       // IncDec()
    }
}