package ideawolf.CookingAssistant

import Cuisine
import Recipe
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ideawolf.CookingAssistant.ui.theme.CookingAssistantTheme
import java.util.concurrent.TimeUnit

var ramen_Recipe_List = ArrayList<Recipe>();

var ramen = Cuisine(name = "라면", description = "간편하게 먹는 라면입니다", recipe = ramen_Recipe_List);
var ramen2 = Cuisine(name = "라면2", description = "간편하게 먹는 라면입니다", recipe = ramen_Recipe_List);
var ramen3 = Cuisine(name = "라면3", description = "간편하게 먹는 라면입니다", recipe = ramen_Recipe_List);

var foodList = ArrayList<Cuisine>()

var ramen_recipe2 = Recipe(
    title = "재료 넣기",
    material = arrayListOf<String>("라면 1봉지"),
    description = "면, 스프, 후레이크를 넣어주세요. 4분 더 끓이신 후 완성입니다.",
    duration = TimeUnit.SECONDS.toMinutes(4),
    canDoOther = false
);

var cart = ArrayList<Cuisine>()

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "homepage"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("homepage") {
            HomePage(
                onNavigateToCooking = {
                    navController.navigate("cookpage")
                },
                /*...*/
                onNavigateToHome = {
                    navController.navigate("homepage")

                },
                0,
            )
        }
        composable("cookpage") {
            cooking_page(
                onNavigateToCooking = {
                    navController.navigate("cookpage")
                },
                /*...*/
                onNavigateToHome = {
                    navController.navigate("homepage")
                },
                1,
            )
        }
    }
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ramen_Recipe_List.add(ramen_recipe2);
        foodList.add(ramen)
        foodList.add(ramen2)
        foodList.add(ramen3)
        setContent {
            AppNavHost()
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomePage(onNavigateToCooking: () -> Unit, onNavigateToHome: () -> Unit, selectedItem: Int) {
    CookingAssistantTheme {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    icon = { Icon(Icons.Filled.Build, "") },
                    text = { Text("Cook") },
                    onClick = onNavigateToCooking,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                )

            },
            topBar = {
                topBar()
            },
            content = { innerPadding ->
                LazyColumn(
                    // consume insets as scaffold doesn't do it by default
                    modifier = Modifier.consumedWindowInsets(innerPadding),
                    contentPadding = innerPadding
                ) {
                    items(foodList.size) { selectedItem ->
                        FoodCardInHome(cuisine = foodList[selectedItem])
                    }
                }
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Go Home") },
                        label = { Text("Home") },
                        selected = selectedItem == 0,
                        onClick = {
                            onNavigateToHome()
                        }
                    )
                    NavigationBarItem(
                        icon = {
                            Icon(
                                Icons.Filled.Info,
                                contentDescription = "Let's Cook"
                            )
                        }, // 요리 아이콘 변경 필요
                        label = { Text("Cook") },
                        selected = selectedItem == 1,
                        onClick = {
                            onNavigateToCooking()
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.MoreVert, contentDescription = "More") },
                        label = { Text("More") },
                        selected = selectedItem == 2,
                        onClick = {
                            onNavigateToHome()
                        }
                    )
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun cooking_page(onNavigateToCooking: () -> Unit, onNavigateToHome: () -> Unit, selectedItem: Int) {
    CookingAssistantTheme {
        Scaffold(
            topBar = {
                topBar()
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Go Home") },
                        label = { Text("Home") },
                        selected = selectedItem == 0,
                        onClick = {
                            onNavigateToHome()
                        }
                    )
                    NavigationBarItem(
                        icon = {
                            Icon(
                                Icons.Filled.Info,
                                contentDescription = "Let's Cook"
                            )
                        }, // 요리 아이콘 변경 필요
                        label = { Text("Cook") },
                        selected = selectedItem == 1,
                        onClick = {
                            onNavigateToCooking()
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.MoreVert, contentDescription = "More") },
                        label = { Text("More") },
                        selected = selectedItem == 2,
                        onClick = {
                            onNavigateToHome()
                        }
                    )
                }
            },
            content = { padding ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {  // Selected Food
                        items(cart.size) { selectedItem ->
                            FoodCardInProcess(cuisine = cart[selectedItem])
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            "Expected Remaining Time:",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.padding(2.dp))
                        Text(
                            "00 : 00",
                            fontSize = 18.sp,
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            "Current Process:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Process Name Lorem ipsum  Lorem ipsum  Lorem ipsum  Lorem ipsum ",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text("Start")

                    }
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun defaultUI() {
}

@Preview
@Composable
fun FoodCardInProcess(cuisine: Cuisine) {
    var shape = RoundedCornerShape(9.dp)
    Card(
        Modifier.size(width = 100.dp, height = 150.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .size(98.dp)
                    .clip(shape)
                    .padding(horizontal = 5.dp)
                    .border(1.5.dp, Color(0xEAEAEA), shape = shape)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.info_image_box),
                    contentDescription = cuisine.description,
                    Modifier.fillMaxSize(),
                    tint = Color.Unspecified,
                )
            }
            Text(cuisine.name)
            Text("99.9" + " %")
        }
        // Card content
    }
}


@Preview
@Composable
fun FoodCardInHome(cuisine: Cuisine) {
    var shape = RoundedCornerShape(9.dp)
    val isChecked = remember { mutableStateOf(false) }

    isChecked.value = cart.contains(cuisine)

    Card(
        Modifier
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1F)
        ) {
            Box(
                modifier = Modifier
                    .size(98.dp)
                    .clip(shape)
                    .border(1.5.dp, Color(0xEAEAEA), shape = shape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.info_image_box),
                    contentDescription = null,
                    Modifier.fillMaxSize(),
                    tint = Color.Unspecified,
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.weight(0.8f)) {
                Text(
                    cuisine.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    cuisine.description,
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Box(Modifier.weight(0.2f)) {
                Checkbox(checked = isChecked.value, onCheckedChange = { checked ->
                    if (checked) {
                        // Checked
                        cart.add(cuisine)
                        isChecked.value = cart.contains(cuisine)
                    } else {
                        //Unchecked
                        cart.remove(cuisine)
                        isChecked.value = cart.contains(cuisine)
                    }
                })
            }
        }

        // Card content
    }
}
