package edu.lemoyne.campusapp
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect

@Composable
fun InterestsScreen(
    state: ShowcaseUiState,
    onBack: () -> Unit,

    onRefresh: () -> Unit, // Lab 21: Task 1
    modifier: Modifier = Modifier,
) {
    // Lab 21: task 1: - load when the screen opens
    LaunchedEffect(Unit) {
        onRefresh()
    }
    BackHandler { onBack() }
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) { Text("Back") }
        // Lab 21: task 1: - reload when tapped
        TextButton(onClick = onRefresh) {
            Text("Refresh items")
        }
        when (state) {
            ShowcaseUiState.Loading -> Text("Loading from Supabase...")
            is ShowcaseUiState.Error -> Column {
                Text("Could not load: " + state.message)
            }
            is ShowcaseUiState.Success -> {
                if (state.items.isEmpty()) {
                    Text("No items yet.")
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.items) { item ->
                            Column(Modifier.padding(8.dp)) {
                                Text(item.title)
                                Text(item.category)
                                Text(item.description)
                            }
                        }
                    }
                }
            }

        }
    }
}