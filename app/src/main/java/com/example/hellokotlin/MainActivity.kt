package com.example.hellokotlin

import ads_mobile_sdk.h5
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Star

import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign

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
                            onStartWorkout = { navController.navigate("WorkoutScreen_Screen") },
                            onTryEasier = { /* logic */ },

                            )

                    }


                    composable("WorkoutScreen_Screen") {
                        //NextScreen()
                        WorkoutScreen(
                            onWorkoutComplete = { navController.navigate("WorkoutCompleteScreen_Screen") }
                        )
                    }


                    composable("WorkoutCompleteScreen_Screen") {
                        //NextScreen()
                        WorkoutCompleteScreen(

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

    Scaffold(
        //topBar = { TopAppBar(title = { Text("Adaptive Recommendation") }) },
        bottomBar = { BottomBar() }
    ) { padding ->
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
        topBar = { TopAppBar(title = { Text("Adaptive Recommendation") }) },
        bottomBar = { BottomBar() }
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
            WorkoutDetailRow("Duration", "30 minutes")
            WorkoutDetailRow("Focus", "Full Body")

            // Exercise list
            WorkoutExercise("Squats", "10 min")
            WorkoutExercise("Push-ups", "10 min")
            WorkoutExercise("Plank", "10 min")

            Spacer(modifier = Modifier.weight(1f))

            /*        Button(
                        onClick = onStartWorkout,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Start Workout")
                    }*/

            Button(
                onClick = onStartWorkout,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6EA4FF)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Text("Start Workout", color = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("→", color = Color.White)
            }


            /*
                        OutlinedButton(
                            onClick = onTryEasier,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Something Easier")
                        }*/

            Button(
                onClick = onTryEasier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFf59c85)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Text("Something Easier", color = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("→", color = Color.White)
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
fun BottomBar() {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Outlined.Explore, contentDescription = "Explore") },
            label = { Text("Explore") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Outlined.Add, contentDescription = "Create") },
            label = { Text("Create") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Outlined.BarChart, contentDescription = "Progress") },
            label = { Text("Progress") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Outlined.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Outlined.Book, contentDescription = "Help") },
            label = { Text("Help") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    onWorkoutComplete: () -> Unit

) {

    Scaffold(
        topBar = { TopAppBar(title = { Text("During Workout") }) },
        bottomBar = { BottomBar() },
        /*modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)*/
    )
    { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(0.dp))

            /*Text(
                text = "During Workout",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )*/

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Push-ups",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            var timeLeft by remember { mutableStateOf(45) }
/*
            CircularTimerDial(
                totalTime = 60,
                timeLeft = timeLeft
            )*/

            CountdownCircularTimer(totalTime = 60_000L) // 60 seconds

           /* Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "00:20 / 00:30",
                style = MaterialTheme.typography.titleMedium
            )*/

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { /* Pause logic */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF69A8FF)
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Pause,
                        contentDescription = "Pause"
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Pause")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { /* Skip logic */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF69A8FF)
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Skip"
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Skip")
                }
            }



            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = onWorkoutComplete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6EA4FF)
                ),
                modifier = Modifier
                    //.fillMaxWidth()
                    .width(width = 300.dp)
                    .height(52.dp)
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Text("Finish Workout", color = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("→", color = Color.White)
            }

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Feeling Struggled?",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFf59c85)
                ),
                modifier = Modifier
                    //  .fillMaxWidth()
                    .width(width = 300.dp)
                    .height(52.dp)
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Text("Suggest Easier Move", color = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("→", color = Color.White)
            }
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutCompleteScreen(
    totalTime: String = "20 min",
    exerciseCount: Int = 3,
    onSaveTemplate: () -> Unit = {},
    onDone: () -> Unit = {}
)
{
    var rating by remember { mutableStateOf(0) }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Workout Summary") }) },
        bottomBar = { BottomBar() },
        /*modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)*/
    )
    { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Workout Complete!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))
            InfoItem(label = "Total time:", value = totalTime)
            InfoItem(label = "Exercise:", value = exerciseCount.toString())
            InfoItem(
                label = "Moderate:",
                valueComposable = {
                    Text("😊", fontSize = 28.sp)
                }
            )


            Spacer(Modifier.height(60.dp))

            Text("Rate Your Workout", fontSize = 22.sp)
            Spacer(Modifier.height(16.dp))
            StarRating(
                rating = rating,
                onRatingChanged = { rating = it }
            )

            Spacer(Modifier.height(16.dp))
            Text("You rated: $rating stars")

            Spacer(Modifier.height(40.dp))
            // Buttons
            Button(
                onClick = onSaveTemplate,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA89B)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Save as Template", fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onDone,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF76A8FF)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Done", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}


@Composable
fun InfoItem(
    label: String,
    value: String = "",
    valueComposable: @Composable (() -> Unit)? = null

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text("★", fontSize = 30.sp, color = Color(0xFF4A72FF))
        Spacer(Modifier.width(8.dp))
        Text(label, fontSize = 18.sp)
        Spacer(Modifier.width(4.dp))
        if (valueComposable != null) {
            valueComposable()
        } else {

            Text(value, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun StarRating(
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (star in 1..5) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rate $star stars",
                modifier = Modifier
                    .size(36.dp)
                    .clickable { onRatingChanged(star) },
                tint = if (star <= rating) Color(0xFFFFC107) else Color.Gray
            )
        }
    }
}


@Composable
fun CircularTimerDial(
    totalTime: Int = 20,   // total time in seconds
    timeLeft: Int = 10     // time remaining in seconds
) {
    val progress = timeLeft / totalTime.toFloat()

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        label = "timer-progress"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(200.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            // Background circle
            drawArc(
                color = Color.LightGray,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 18f, cap = StrokeCap.Round)
            )

            // Progress arc
            drawArc(
                color = Color(0xFF4A72FF),
                startAngle = -90f,
                sweepAngle = animatedProgress * 360f,
                useCenter = false,
                style = Stroke(width = 18f, cap = StrokeCap.Round)
            )
        }

        // Time label in center
        Text(
            text = formatTime(timeLeft),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}


@Composable
fun CountdownCircularTimer(
    totalTime: Long = 600_000L // total time in milliseconds
) {
    var timeLeft by remember { mutableStateOf(totalTime) }

    // Start the timer once
    LaunchedEffect(Unit) {
        object : CountDownTimer(totalTime, 10L) { // updates every 100ms
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
            }

            override fun onFinish() {
                timeLeft = 0
            }
        }.start()
    }

    val progress = timeLeft / totalTime.toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        label = "timer-progress"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(200.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Background circle
            drawArc(
                color = Color.LightGray,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 18f, cap = StrokeCap.Round)
            )

            // Foreground (progress) arc
            drawArc(
                color = Color(color = 0xFF87B972 ),
                startAngle = -90f,
                sweepAngle = animatedProgress * 360f,
                useCenter = false,
                style = Stroke(width = 18f, cap = StrokeCap.Round)
            )
        }

        // Time text in center
        Text(
            text = formatTime((timeLeft / 1000).toInt()),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return String.format("%02d:%02d", m, s)
}


data class NavItem(
    val label: String,
    val icon: ImageVector
)






