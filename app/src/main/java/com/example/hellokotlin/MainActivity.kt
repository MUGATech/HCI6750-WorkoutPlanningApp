package com.example.hellokotlin

import ads_mobile_sdk.h5
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hellokotlin.ui.theme.HelloKotlinTheme

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Typography


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            MaterialTheme {
                NavHost(
                    navController = navController,
                    startDestination = "mood_checkin"
                ) {
                    composable("mood_checkin") {
                        MoodCheckInScreen(
                            onContinue = {
                                //navController.navigate("next_screen")
                                navController.navigate("AdaptiveRecommendation_Screen")
                            }
                        )
                    }

                    //composable("next_screen") {
                        composable("AdaptiveRecommendation_Screen") {
                        //NextScreen()
                        AdaptiveRecommendationScreen(
                            onStartWorkout = { navController.navigate(Screen.Workout.route) },
                            onTryEasier = { /* logic */ }

                        )
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object MoodCheckIn : Screen("mood_check_in")
    object Recommendations : Screen("recommendations")
    object Workout : Screen("workout")
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name! Good Job",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloKotlinTheme {
        Greeting("Android")
    }
}

@Composable
fun MoodCheckInScreen(onContinue: () -> Unit = {}) {

    var energy by remember { mutableStateOf(0.3f) }
    var mood by remember { mutableStateOf(0.3f) }
    var soreness by remember { mutableStateOf(0.3f) }

    val sadEmoji = "😞"
    val neutralEmoji = "😐"
    val happyEmoji = "😊"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(24.dp))

        Text("Mood Check-In", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(8.dp))

        Text("How are you feeling today?", color = Color.Gray)

        Spacer(Modifier.height(32.dp))

        // --- ENERGY ---
        MoodSliderRow(
            label = "Energy",
            value = energy,
            onValueChange = { energy = it },
            startEmoji = sadEmoji,
            endEmoji = happyEmoji
        )

        Spacer(Modifier.height(20.dp))

        // --- MOOD ---
        MoodSliderRow(
            label = "Mood",
            value = mood,
            onValueChange = { mood = it },
            startEmoji = neutralEmoji,
            endEmoji = happyEmoji
        )

        Spacer(Modifier.height(20.dp))

        // --- SORENESS ---
        MoodSliderRow(
            label = "Soreness",
            value = soreness,
            onValueChange = { soreness = it },
            startEmoji = sadEmoji,
            endEmoji = happyEmoji
        )

        Spacer(Modifier.height(40.dp))

        Button(
            onClick = onContinue,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6EA4FF)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(25.dp))
        ) {
            Text("Continue", color = Color.White)
            Spacer(Modifier.width(8.dp))
            Text("→", color = Color.White)
        }
    }
}

@Composable
fun MoodSliderRow(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    startEmoji: String,
    endEmoji: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text("$label:", modifier = Modifier.width(80.dp))

        Text(startEmoji, fontSize = 32.sp)

        Slider(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF6EA4FF),
                activeTrackColor = Color.LightGray,
                inactiveTrackColor = Color.LightGray
            )
        )

        Text(endEmoji, fontSize = 32.sp)
    }
}

@Composable
fun NextScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to the next screen!", fontSize = 24.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveRecommendationScreen(
    onStartWorkout: () -> Unit,
    onTryEasier: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Adaptive Recommendation") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Today's Suggested Workout", style = MaterialTheme.typography.headlineMedium)

            // Workout metadata
            WorkoutDetailRow("Intensity", "Moderate")
            WorkoutDetailRow("Duration", "22 minutes")
            WorkoutDetailRow("Focus", "Full Body")

            // Exercise list
            WorkoutExercise("Squats", "45 sec")
            WorkoutExercise("Push-ups", "30 sec")
            WorkoutExercise("Plank", "20 sec")

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onStartWorkout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Workout")
            }

            OutlinedButton(
                onClick = onTryEasier,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Something Easier")
            }
        }
    }
}

@Composable
fun WorkoutDetailRow(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(label)
        Text(value, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun WorkoutExercise(name: String, duration: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("$name ($duration)")
        }
    }
}

@Composable
fun BottomNavBar() {
    var selectedItem by remember { mutableStateOf(0) }

    val items = listOf(
        NavItem("Explore", Icons.Outlined.CompassCalibration),
        NavItem("Create", Icons.Outlined.Add),
        NavItem("Progress", Icons.Outlined.BarChart),
        NavItem("Profile", Icons.Outlined.Person),
        NavItem("Help", Icons.Outlined.MenuBook)
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { selectedItem = index },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

data class NavItem(
    val label: String,
    val icon: ImageVector
)




