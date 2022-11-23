package ideawolf.CookingAssistant

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
            defaultUI()
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
fun DefaultPreview() {
    CookingAssistantTheme {
        defaultUI()
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun defaultUI() {
    var selectedItem by remember { mutableStateOf(0) }
    CookingAssistantTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Cooking Assistant",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    actions = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },
//                                scrollBehavior = scrollBehavior
                )
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
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Go Home") },
                        label = { Text("Home") },
                        selected = selectedItem == 0,
                        onClick = { selectedItem = 0 }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Info, contentDescription = "Let's Cook") }, // 요리 아이콘 변경 필요
                        label = { Text("Cook") },
                        selected = selectedItem == 1,
                        onClick = { selectedItem = 1 }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Add, contentDescription = "Add Recipe") },
                        label = { Text("Add") },
                        selected = selectedItem == 2,
                        onClick = { selectedItem = 2 }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.MoreVert, contentDescription = "More") },
                        label = { Text("More") },
                        selected = selectedItem == 3,
                        onClick = { selectedItem = 3 }
                    )
                }
            }
        )
    }
}