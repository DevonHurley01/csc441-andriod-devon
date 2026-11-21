package edu.lemoyne.campusapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.lemoyne.campusapp.ui.theme.CampusAppTheme

const val NAME = "Devon"

class MainActivity : ComponentActivity() {
    private val showcaseViewModel: ShowcaseViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val showcaseState by showcaseViewModel.uiState.collectAsStateWithLifecycle()
            val loginState by loginViewModel.state.collectAsStateWithLifecycle()
            showcaseViewModel
            CampusAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CampusAppScreen(
                        showcaseState = showcaseState,
                        loginState = loginState,
                        onRefreshShowcase = showcaseViewModel::refresh,
                        onLogin = { email, password, navigate ->
                            loginViewModel.login(email, password) { token ->
                                showcaseViewModel.setAccessToken(token)
                                showcaseViewModel.refresh()
                                navigate()
                            }
                        },
                        modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
