package ideawolf.CookingAssistant

import Cuisine
import Recipe
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ideawolf.CookingAssistant.ui.theme.CookingAssistantTheme
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

var cookList = ArrayList<Cuisine>()
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

fun saveRecipes() {
    var jsonString: String = """
        [
            {
                "name": "라면",
                "description": "간편하게 먹는 라면입니다",
                "recipe": [
                    {
                        "title": "물 끓이기",
                        "material": [
                            "물500ml",
                            "냄비"
                        ],
                        "description": "물 550ml를 냄비에 받은 후 4분동안 끓이세요",
                        "duration": 240000,
                        "canDoOther": true
                    },
                    {
                        "title": "재료 넣기",
                        "material": [
                            "라면 1봉지"
                        ],
                        "description": "면, 스프, 후레이크를 넣어주세요. 4분 더 끓이신 후 완성입니다.",
                        "duration": 240000,
                        "canDoOther": false
                    }
                ]
            },
            {
                "name": "스테이크",
                "description": "소고기 구이입니다",
                "recipe": [
                    {
                        "title": "후라이팬 예열하기",
                        "material": [
                            "후라이펜",
                            "소량의 기름"
                        ],
                        "description": "후라이팬에 기름을 두리고 1분간 예열하세요",
                        "duration": 60000,
                        "canDoOther": true
                    },
                    {
                        "title": "소고기 굽기",
                        "material": [
                            "소고기 400g"
                        ],
                        "description": "후라이팬에 소고기를 올리고 취향에 맞게 (2분 - 레어, 3분 미디엄 레어, 4분 미디엄) 익혀주시면 완성입니다.",
                        "duration": 240000,
                        "canDoOther": false
                    }
                ]
            },
            {
                "name": "계란볶음밥",
                "description": "계란과 밥을 볶은 요리입니다",
                "recipe": [
                    {
                        "title": "후라이팬 예열하기",
                        "material": [
                            "후라이펜",
                            "소량의 기름"
                        ],
                        "description": "후라이팬에 기름을 두리고 1분간 예열하세요",
                        "duration": 60000,
                        "canDoOther": true
                    },
                    {
                        "title": "계란 익히기",
                        "material": [
                            "계란 2알"
                        ],
                        "description": "후라이팬에서 계란을 튀기듯이 2분간 익혀주세요",
                        "duration": 120000,
                        "canDoOther": false
                    },
                    {
                        "title": "밥과 계란 볶기",
                        "material": [
                            "밥 1공기"
                        ],
                        "description": "후라이팬에 밥을 넣고 계란과 함께 3분정도 볶아주시면 완성입니다.",
                        "duration": 180000,
                        "canDoOther": false
                    }
                ]
            }
        ]
    """.trimIndent()

    cookList = Json.decodeFromString<ArrayList<Cuisine>>(jsonString)
    println(cookList)
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavHost()
        }
    }

    override fun onStart() {
        super.onStart()
        saveRecipes() // reset recipes
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
                    items(cookList.size) { selectedItem ->
                        FoodCardInHome(cuisine = cookList[selectedItem])
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

val process_list = ArrayList<Recipe>()
val processing_food_list = ArrayList<Cuisine>()
val process_description = mutableStateOf("Process Description")
val process_food = mutableStateOf("Cuisine Name")

var IsWaitNext: Boolean = false
var proc_idx: Int = 0
var IsEnd:Boolean=false
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun cooking_page(onNavigateToCooking: () -> Unit, onNavigateToHome: () -> Unit, selectedItem: Int) {
    val buttonText = remember { mutableStateOf("Start") }
    var proc_level: Int = 0
    process_description.value = "-"
    process_food.value = "-"
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
                            "Current Cuisine:",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.padding(2.dp))
                        Text(
                            process_food.value,
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
                            process_description.value,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Button(onClick = {
                        if (proc_level == 0) {
                            cook(cart)
                            buttonText.value = "Next"
                            proc_level = 1
                            IsWaitNext = true
                        } else if (proc_level > 0) {
                            if (process_list.getOrNull(proc_idx) != null) {
                                IsWaitNext = false
                                process_description.value = "${process_list[proc_idx].description}"
                                process_food.value = "${processing_food_list[proc_idx].name}"
                                proc_idx++
                            } else if(!IsEnd){
                                process_description.value = "잠시만 기다려주세요."
                                process_food.value = "잠시만 기다려주세요."

                                IsWaitNext = true
                            } else {
                                process_description.value = "모든 요리가 완성되었습니다."
                                process_food.value = "완성"

                                buttonText.value = "END"

                                cart.clear()
                                saveRecipes()
                            }
                        }
                    }) {
                        Text(buttonText.value)
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
            .height(100.dp)
            .clickable {
                if (!isChecked.value) {
                    cart.add(cuisine)
                    isChecked.value = cart.contains(cuisine)
                } else {
                    cart.remove(cuisine)
                    isChecked.value = cart.contains(cuisine)
                }

            },
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
