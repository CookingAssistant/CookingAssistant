package ideawolf.CookingAssistant

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ideawolf.CookingAssistant.ui.theme.CookingAssistantTheme


val colors = listOf(
    Color(0xFFffd7d7.toInt()),
    Color(0xFFffe9d6.toInt()),
    Color(0xFFfffbd0.toInt()),
    Color(0xFFe3ffd9.toInt()),
    Color(0xFFd0fff8.toInt())
)


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomePage()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun HomePage() {
    var selectedItem by remember { mutableStateOf(0) }
    CookingAssistantTheme {
        Scaffold(
            topBar = {
                topBar()
            },
            content = { innerPadding ->
                LazyColumn(
                    // consume insets as scaffold doesn't do it by default
                    modifier = Modifier.consumedWindowInsets(innerPadding),
                    contentPadding = innerPadding
                ) {
                    items(count = 100) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(colors[it % colors.size])
                        )
                    }
                }
            },
            bottomBar = {
                navigationBar()
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun ready_page() {
    defaultUI()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun defaultUI() {
    CookingAssistantTheme {
        Scaffold(
            topBar = {
                topBar()
            },
            bottomBar = {
                navigationBar()
            },
            content = { innerPadding ->
                Box(
// consume insets as scaffold doesn't do it by default
                    modifier = Modifier.consumedWindowInsets(innerPadding),
                ) {
                    Text("Hello world!")
                }
            })
    }
}