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

//
//@Composable
//fun navigationBar(onNavigateToCooking: () -> Unit, onNavigateToHome: () -> Unit) {
//    var selectedItem by remember { mutableStateOf(0) }
//    NavigationBar {
//        NavigationBarItem(
//            icon = { Icon(Icons.Filled.Home, contentDescription = "Go Home") },
//            label = { Text("Home") },
//            selected = selectedItem == 0,
//            onClick = {
//                onNavigateToHome()
//                selectedItem = 1;
//            }
//        )
//        NavigationBarItem(
//            icon = { Icon(Icons.Filled.Info, contentDescription = "Let's Cook") }, // 요리 아이콘 변경 필요
//            label = { Text("Cook") },
//            selected = selectedItem == 1,
//            onClick = {
//                onNavigateToCooking()
//                selectedItem = 0;
//            }
//        )
//        NavigationBarItem(
//            icon = { Icon(Icons.Filled.MoreVert, contentDescription = "More") },
//            label = { Text("More") },
//            selected = selectedItem == 2,
//            onClick = { selectedItem = 2 }
//        )
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBar() {
    TopAppBar(
        title = {
            Text(
                "Cooking Assistant",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
//        actions = {
//            IconButton(onClick = { /* doSomething() */ }) {
//                Icon(
//                    imageVector = Icons.Filled.Search,
//                    contentDescription = "Localized description"
//                )
//            }
//        },
//        navigationIcon = {
//            IconButton(onClick = { /* doSomething() */ }) {
//                Icon(
//                    imageVector = Icons.Filled.Menu,
//                    contentDescription = "Localized description"
//                )
//            }
//        },
//                                scrollBehavior = scrollBehavior
    )
}
