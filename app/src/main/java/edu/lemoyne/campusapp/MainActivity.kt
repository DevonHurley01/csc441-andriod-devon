package edu.lemoyne.campusapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.lemoyne.campusapp.ui.theme.CampusAppTheme

const val NAME = "Devon"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// --- Class 7: Step 1: a counter that remembers ---
@Composable
fun CounterDemo() {
    var count by remember { mutableStateOf(0) }

    Button(
        onClick = {count++ }
    ) {
        Text(text = "Tapped $count times")
    }
}

// --- Class 6: Step1: My own screen ---
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    // Class 7: Step 2: the list lives in state ---
    var info = remember {
        mutableStateListOf(
            "Free Chimney Inspections",
            "Certified Mason",
            "30 years of experience",
            "Certified Chimney Sweeper"
        )
    }

    // --- Class 7: Step 4: what's typed lives in state ---
    var newInfo by remember { mutableStateOf("") }
    // --- Class 8: step 2: the error message live in the state too ---
    var error by remember { mutableStateOf<String?>(null) }


    // --- Class 6: Step 3: a column, so things stack ---
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // CounterDemo()
        // --- Class6: Task 3: picture of my own ---
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Cross Masonry Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Class 6: Step 4: real styling ---
        Text(
            text = "Cross Masonry",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Brick, Block, and Stone",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- Class 7: Step 3: the text field ---
        OutlinedTextField(
            value = newInfo,
            // --- Class 8: Step 3: the field itself pushes back ---
            onValueChange = {
                newInfo = it.take(n = MAX_NAME_LENGTH)
                error = null
            },
            label = { Text("Message") },
            singleLine = true,
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )

        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }

        // Lab 7: Task 4: a live character counter ---
        Text(
            text = "${newInfo.length} / 30",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        //Class 7: Step 4: the button changes the state ---
        Button(onClick = {
            // --- Class 8: Step 3: check before your add ---
            val problem = validateMessageName( newInfo, info)
            if (problem == null) {
                info.add(newInfo)
                newInfo = ""
            } else {
                error = problem
            }

        },
            // --- Class 8: Step 4: the sign on the door, not the lock ---
            enabled = newInfo.isNotBlank()
            ) {
            Text("Add Message")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- Lab 7: Task 1: remove the last item ---
        Button(onClick = {
            if (info.isNotEmpty()) {
                info.removeAt(info.lastIndex)
            }
        }) {
            Text("Remove last")
        }

        // --- Lab 7: Task 3: clear all ---
        Button(onClick =  {
            info.clear()
        }) {
            Text("Clear")
        }

        // --- Class 7: step 3: draw whatever is in the  list
        Text(
            // --- Lab 7: task 2: singular and plural ---
            text = if (info.size == 1) "1 message" else "${info.size} messages",
            fontWeight = FontWeight.Bold
            )
        for (trail in info) {
            Text(text = trail, fontSize = 18.sp)
        }

        // --- Lab6: Task 1: Making screen yours ---
        //Text(text = "Free Chimney Inspections",fontSize = 18.sp)
        //Text(text = "Certified Mason", fontSize = 18.sp)
        //Text(text = "30 years of experience", fontSize = 18.sp)
        //Text(text = "Certified Chimney Sweeper")

        // --- Lab6: Task 2: footer ---
        Spacer(modifier = Modifier.height(350.dp))

        Text(
            text = "Last updated September 2026",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}


const val MAX_NAME_LENGTH = 30

// --- Class 8: Step 1: one rule book for message names ---
fun validateMessageName(input: String, existingMessage: List<String>): String? {
    val message = input.trim()
    return when {
        message.isEmpty() -> "Enter a message"
        // --- Lab 8: Task 1: minimum length ---
        message.length < 3 -> "Too short - at least 3 character"
        // --- Lab 8: Task 2: my own rule ---
        !message.first().isLetter() -> "Start with a letter"
        message.length > MAX_NAME_LENGTH -> "Keep it to $MAX_NAME_LENGTH character or less"
        existingMessage.any { it.equals( message, ignoreCase = true) } -> "That message has already on the list"
        else -> null
        }
    }



// Class 6: Step 2: preview ---
@Preview
@Composable
fun HomeScreenPreview() {
    CampusAppTheme() {
        HomeScreen()
    }
}

// --- Class6: Task 4: dark mode preview ---
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenDarkPreview() {
    CampusAppTheme() {
        Surface() {
            HomeScreen()
        }
    }
}