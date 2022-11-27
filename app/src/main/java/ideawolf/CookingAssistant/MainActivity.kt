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

var ramen_recipe = ArrayList<Recipe>();

var ramen_recipe1 = Recipe(
    title = "물 끓이기",
    material = arrayListOf<String>("물500ml", "냄비"),
    description = "물 550ml를 냄비에 받은 후 4분동안 끓이세요",
    duration = TimeUnit.MINUTES.toMillis(4),
    canDoOther = true,
    );

var ramen_recipe2 = Recipe(
    title = "재료 넣기",
    material = arrayListOf<String>("라면 1봉지"),
    description = "면, 스프, 후레이크를 넣어주세요. 4분 더 끓이신 후 완성입니다.",
    duration = TimeUnit.MINUTES.toMillis(4),
    canDoOther = false,
    );

var ramen = Cuisine(name = "라면", description = "간편하게 먹는 라면입니다", recipe = ramen_recipe);


var steak_recipe = ArrayList<Recipe>();

var steak_recipe1 = Recipe(
    title = "후라이팬 예열하기",
    material = arrayListOf<String>("후라이펜", "소량의 기름"),
    description = "후라이팬에 기름을 두리고 1분간 예열하세요",
    duration = TimeUnit.MINUTES.toMillis(1),
    canDoOther = true,
    );

var steak_recipe2 = Recipe(
    title = "소고기 굽기",
    material = arrayListOf<String>("소고기 400g"),
    description = "후라이팬에 소고기를 올리고 취향에 맞게 (2분 - 레어, 3분 미디엄 레어, 4분 미디엄) 익혀주시면 완성입니다.",
    duration = TimeUnit.MINUTES.toMillis(4),
    canDoOther = false,
    );


var steak = Cuisine(name = "스테이크", description = "소고기 구이입니다", recipe = steak_recipe);


var egg_fried_rice_recipe = ArrayList<Recipe>();

var egg_fried_rice_recipe1 = Recipe(
    title = "후라이팬 예열하기",
    material = arrayListOf<String>("후라이펜", "소량의 기름"),
    description = "후라이팬에 기름을 두리고 1분간 예열하세요",
    duration = TimeUnit.MINUTES.toMillis(1),
    canDoOther = true,
    );
var egg_fried_rice_recipe2 = Recipe(
    title = "계란 익히기",
    material = arrayListOf<String>("계란 2알"),
    description = "후라이팬에서 계란을 튀기듯이 2분간 익혀주세요",
    duration = TimeUnit.MINUTES.toMillis(2),
    canDoOther = false,
    );
var egg_fried_rice_recipe3 = Recipe(
    title = "밥과 계란 볶기",
    material = arrayListOf<String>("밥 1공기"),
    description = "후라이팬에 밥을 넣고 계란과 함께 3분정도 볶아주시면 완성입니다.",
    duration = TimeUnit.MINUTES.toMillis(3),
    canDoOther = false,
    );

var egg_fried_rice = Cuisine(name = "계란볶음밥", description = "계란과 밥을 볶은 요리입니다", recipe = egg_fried_rice_recipe);


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
fun saveRecipes(){

    ramen_recipe1.left = null
    ramen_recipe1.right = ramen_recipe2

    ramen_recipe2.left = ramen_recipe1
    ramen_recipe1.right = null

    ramen_recipe.add(ramen_recipe1);
    ramen_recipe.add(ramen_recipe2);


    steak_recipe1.left = null
    steak_recipe1.right = steak_recipe2

    steak_recipe2.left = steak_recipe1
    steak_recipe2.right = null


    steak_recipe.add(steak_recipe1);
    steak_recipe.add(steak_recipe2);

    egg_fried_rice_recipe1.left = null
    egg_fried_rice_recipe1.right = egg_fried_rice_recipe2

    egg_fried_rice_recipe2.left = egg_fried_rice_recipe1
    egg_fried_rice_recipe2.right = egg_fried_rice_recipe3

    egg_fried_rice_recipe3.left = egg_fried_rice_recipe2
    egg_fried_rice_recipe3.right = null



    egg_fried_rice_recipe.add(egg_fried_rice_recipe1);
    egg_fried_rice_recipe.add(egg_fried_rice_recipe2);
    egg_fried_rice_recipe.add(egg_fried_rice_recipe3);

    cookList.add(ramen)
    cookList.add(steak)
    cookList.add(egg_fried_rice)
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        saveRecipes()
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
                    Button(onClick = {
                        cook(cart)
                    }) {
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
