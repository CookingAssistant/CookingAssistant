package ideawolf.CookingAssistant

import Cuisine
import Recipe
import android.content.Context
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ideawolf.CookingAssistant.CookingAssistant.getAppContext
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
                "name": "Ramen",
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
                "name": "Steak",
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
                "name": "Egg fried rice",
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
            },
            {
                "name": "Seasoned bean sprouts",
                "description": "매콤새콤한 콩나물무침입니다.",
                "recipe": [
                    {
                        "title": "콩나물 손질하기",
                        "material": [
                            "콩나물",
                            "물500ml",
                            "체"
                        ],
                        "description": "콩나물을 흐르는 물에 여러번 씻어준 뒤 체에 밭쳐 물기를 빼주세요.",
                        "duration": 60000,
                        "canDoOther": false
                    },
                    {
                        "title": "대파 손질하기",
                        "material": [
                            "대파",
                            "가위"
                        ],
                        "description": "대파를 가위로 잘라주세요.",
                        "duration": 30000,
                        "canDoOther": false
                    },
                    {
                        "title": "물 끓이기",
                        "material": [
                            "물",
                            "냄비"
                        ],
                        "description": "물이 끓을 때 까지 기다려주세요",
                        "duration": 240000,
                        "canDoOther": true
                    },
                    {
                        "title": "콩나물 데치기",
                        "material": [
                            "콩나물",
                            "끓는 물",
                            "소금"
                        ],
                        "description": "끓는 물에 소금 반 큰술과 콩나물을 넣고 끓여주세요",
                        "duration": 300000,
                        "canDoOther": true
                    },
                    {
                        "title": "콩나물 물 빼기",
                        "material": [
                            "콩나물",
                            "체"
                        ],
                        "description": "데친 콩나물을 체에 밭쳐 물기를 빼주세요",
                        "duration": 60000,
                        "canDoOther": false
                    },
                    {
                        "title": "재료 넣기",
                        "material": [
                            "콩나물",
                            "대파",
                            "고춧가루 2 큰 술",
                            "소금 적당량",
                            "다진 마늘 반 큰술",
                            "깨소금 적당량"
                        ],
                        "description": "재료를 넣어 주세요",
                        "duration": 120000,
                        "canDoOther": false
                    },
                    {
                        "title": "석어 주기",
                        "material": [
                            "진간장"
                        ],
                        "description": "진간장 한 큰술을 넣고, 콩나물 대가리가 떨어지지 않도록 조심스럽게 섞어주세요",
                        "duration": 180000,
                        "canDoOther": false
                    },
                    {
                        "title": "마무리 하기",
                        "material": [
                            "참기름"
                        ],
                        "description": "참기름 한 큰 술 두륵 조물조물 해주세요",
                        "duration": 60000,
                        "canDoOther": false
                    }
                ]
            },
            {
                "name": "Soft tofu stew",
                "description": "얼큰한 순두부찌개입니다.",
                "recipe": [
                    {
                        "title": "냄비에 재료 넣기",
                        "material": [
                            "물",
                            "멸치",
                            "다시마",
                            "표고버섯"
                        ],
                        "description": "냄비에 재료를 넣어주세요.",
                        "duration": 30000,
                        "canDoOther": false
                    },
                    {
                        "title": "냄비 끓이기",
                        "material": [],
                        "description": "냄비에 넣은 재료를 끓여주세요.",
                        "duration": 240000,
                        "canDoOther": true
                    },
                    {
                        "title": "재료 손질하기",
                        "material": [
                            "청양고추1개",
                            "호박 1/4 개",
                            "칼",
                            "가위"
                        ],
                        "description": "청양고추를 어슷하게 썰고 호박은 반달 모양으로 썰어주세요",
                        "duration": 180000,
                        "canDoOther": false
                    },
                    {
                        "title": "냄비에 재료 볶기",
                        "material": [
                            "냄비",
                            "참기름 한 큰술",
                            "식용유 두 큰술",
                            "양파",
                            "대파"
                        ],
                        "description": "냄비에 참기름과 식용유를 넣고 양파, 대파를 볶아주세요",
                        "duration": 300000,
                        "canDoOther": false
                    },
                    {
                        "title": "고추 기름 만들기",
                        "material": [
                            "고추가루 한 큰술 반"
                        ],
                        "description": "냄비에 고루가루 한 큰술 반을 넣고 볶아주세요",
                        "duration": 300000,
                        "canDoOther": false
                    },
                    {
                        "title": "간 맞추기",
                        "material": [
                            "소금 반 큰술",
                            "설탕 한 꼬집"
                        ],
                        "description": "냄비에 재료를 넣고 계속 볶아주세요",
                        "duration": 180000,
                        "canDoOther": false
                    },
                    {
                        "title": "맛 만들기",
                        "material": [
                            "굴소스 한 큰술",
                            "간장 한 큰술"
                        ],
                        "description": "냄비에 재료를 넣고 계속 볶아주세요",
                        "duration": 180000,
                        "canDoOther": false
                    },
                    {
                        "title": "국 물 만들기",
                        "material": [
                            "육수"
                        ],
                        "description": "냄비에 전에 만든 육수를 반정도 채워주세요",
                        "duration": 30000,
                        "canDoOther": false
                    },
                    {
                        "title": "국물 끓이기",
                        "material": [],
                        "description": "냄비를 팔팔 끓여주세요",
                        "duration": 300000,
                        "canDoOther": true
                    },
                    {
                        "title": "간 조절하기",
                        "material": [
                            "호박",
                            "고추",
                            "다진마늘"
                        ],
                        "description": "끓는 냄비에 호박, 고추, 다진마늘을 넣어주세요",
                        "duration": 120000,
                        "canDoOther": false
                    },
                    {
                        "title": "순부두 넣기",
                        "material": [
                            "순두부"
                        ],
                        "description": "순두부를 넣어주세요",
                        "duration": 60000,
                        "canDoOther": false
                    },
                    {
                        "title": "마무리 하기",
                        "material": [
                            "달걀",
                            "파"
                        ],
                        "description": "달걀과 파를 넣고 마무리 해주세요",
                        "duration": 60000,
                        "canDoOther": false
                    }
                ]
            },
            {
                "name": "Braised Spicy Chicken",
                "description": "매콤한 닭볶음탕입니다",
                "recipe": [
                    {
                        "title": "재료 손질하기",
                        "material": [
                            "파",
                            "청양고추",
                            "당근",
                            "감자",
                            "양파"
                        ],
                        "description": "파와 청양고추를 어슷, 감자는 큼직, 양파는 사각으로 썰어주세요.",
                        "duration": 180000,
                        "canDoOther": false
                    },
                    {
                        "title": "얀념 만들기",
                        "material": [
                            "고추장 두 큰술",
                            "고추가루 일곱 큰술",
                            "설탕 두 큰술",
                            "국간장 다섯 큰술",
                            "마늘 한 큰술",
                            "물 소주잔 한 컵"
                        ],
                        "description": "재료를 모두 넣고 섞어주세요",
                        "duration": 300000,
                        "canDoOther": false
                    },
                    {
                        "title": "닭 손질하기-물 끓이기",
                        "material": [
                            "냄비",
                            "물"
                        ],
                        "description": "냄비에 물을 넣고 끓여주세요",
                        "duration": 300000,
                        "canDoOther": true
                    },
                    {
                        "title": "닭 손질하기 - 데치기",
                        "material": [
                            "끓는 물",
                            "닭"
                        ],
                        "description": "닭을 데쳐주세요",
                        "duration": 300000,
                        "canDoOther": true
                    },
                    {
                        "title": "닭 손질하기 - 데치기",
                        "material": [
                            "찬 물",
                            "깊은 냄비",
                            "소주 한 잔"
                        ],
                        "description": "닭을 냄비에 넣고 소주 한 컵을 부어 줍니다.(잡내를 잡아줍니다)",
                        "duration": 60000,
                        "canDoOther": false
                    },
                    {
                        "title": "재료 넣기",
                        "material": [
                            "당근",
                            "감자",
                            "양념장",
                            "물800ml"
                        ],
                        "description": "당근,감자,양념장 물을 넣어줍니다",
                        "duration": 60000,
                        "canDoOther": false
                    },
                    {
                        "title": "냄비 끓이기",
                        "material": [],
                        "description": "냄비를 강불에서 끓여주세요",
                        "duration": 300000,
                        "canDoOther": true
                    },
                    {
                        "title": "재료 섞기",
                        "material": [],
                        "description": "냄비에 재료를 한번 섞어주세요",
                        "duration": 30000,
                        "canDoOther": false
                    },
                    {
                        "title": "온도 조절하기",
                        "material": [],
                        "description": "강불과 중불 사이에서 팔팔 끓여주세요",
                        "duration": 6000000,
                        "canDoOther": true
                    },
                    {
                        "title": "졸이기",
                        "material": [],
                        "description": "취향에 따라 졸여주세요",
                        "duration": 300000,
                        "canDoOther": true
                    },
                    {
                        "title": "마무리 하기",
                        "material": [
                            "대파",
                            "청양고추"
                        ],
                        "description": "대파와 청양고추를 넣고 불을 꺼주세요",
                        "duration": 30000,
                        "canDoOther": false
                    }
                ]
            },
            {
                "name": "Kimchi stew with pork",
                "description": "얼큰한 김치찌개 입니다",
                "recipe": [
                    {
                        "title": "고기 손질하기",
                        "material": [
                            "키친타올",
                            "고기",
                            "칼",
                            "도마"
                        ],
                        "description": "키친타올로 고기 핏물을 제거하고 먹기 좋은 크기로 썰어주세요",
                        "duration": 300000,
                        "canDoOther": false
                    },
                    {
                        "title": "김치 손질하기",
                        "material": [
                            "김치",
                            "칼",
                            "도마"
                        ],
                        "description": "김치를 먹기 좋은 크기로 썰어주세요",
                        "duration": 300000,
                        "canDoOther": false
                    },
                    {
                        "title": "된장 볶기",
                        "material": [
                            "들기름 두 큰술",
                            "된장 한 큰술"
                        ],
                        "description": "냄비에 들기슬과 된장을 넣고 볶아주세요",
                        "duration": 300000,
                        "canDoOther": false
                    },
                    {
                        "title": "고기 볶기",
                        "material": [
                            "고기"
                        ],
                        "description": "고기 겉면이 익을 때가지 잘 볶아주세요",
                        "duration": 180000,
                        "canDoOther": false
                    },
                    {
                        "title": "김치 볶기",
                        "material": [
                            "김치"
                        ],
                        "description": "김치를 넣고 볶아주세요",
                        "duration": 180000,
                        "canDoOther": false
                    },
                    {
                        "title": "육수 넣고 끓이기",
                        "material": [
                            "물800ml"
                        ],
                        "description": "물(육수)를 넣고 강불로 끓여주세요",
                        "duration": 300000,
                        "canDoOther": true
                    },
                    {
                        "title": "졸이기",
                        "material": [],
                        "description": "뚜껑을 덮고 약불로 줄여 졸여주세요",
                            "duration": 1200000,
                        "canDoOther": true
                    },
                    {
                        "title": "고추, 대파 손질",
                        "material": [
                            "대파",
                            "고추",
                            "가위"
                        ],
                        "description": "대파를 어슷썰고 청양고추를 송송 썰어 주세요",
                        "duration": 180000,
                        "canDoOther": false
                    },
                    {
                        "title": "간 맞추기",
                        "material": [
                            "다진마늘 한 큰술",
                            "후추 두 꼬집"
                        ],
                        "description": "중불로 올린 뒤 다진마늘과 후추를 넣고 저어주세요",
                        "duration": 60000,
                        "canDoOther": false
                    },
                    {
                        "title": "재료 넣기",
                        "material": [
                            "대파",
                            "고추"
                        ],
                        "description": "손질된 대파와 고추를 넣어주세요",
                        "duration": 60000,
                        "canDoOther": false
                    },
                    {
                        "title": "마무리 하기",
                        "material": [],
                        "description": "5분간 팔팔 끓이고 마무리합니다",
                        "duration": 300000,
                        "canDoOther": true
                    }
                ]
            },
            {
                "name": "Bean paste stew",
                "description": "시원한 된장찌개 입니다",
                "recipe": [
                    {
                        "title": "냄비에 재료 넣기",
                        "material": [
                            "물800ml",
                            "된장",
                            "고추가루",
                            "다진마늘",
                            "설탕",
                            "간장"
                        ],
                        "description": "냄비에 재료를 넣어주세요",
                        "duration": 120000,
                        "canDoOther": false
                    },
                    {
                        "title": "끓이기",
                        "material": [],
                        "description": "냄비를 강불로 끓여주세요",
                        "duration": 300000,
                        "canDoOther": true
                    },
                    {
                        "title": "재료손질",
                        "material": [
                            "양파",
                            "버섯",
                            "애호박",
                            "청양고추",
                            "두부"
                        ],
                        "description": "재료를 적당한 크기로 설어주세요",
                        "duration": 300000,
                        "canDoOther": false
                    },
                    {
                        "title": "재료 넣기",
                        "material": [],
                        "description": "손질한 재료를 냄비에 넣어주세요",
                        "duration": 60000,
                        "canDoOther": false
                    },
                    {
                        "title": "마무리 하기",
                        "material": [],
                        "description": "5분간 끓여주고 마무리해주세요",
                        "duration": 300000,
                        "canDoOther": true
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
val process_material = mutableStateOf("Material List")

var IsWaitNext: Boolean = false
var proc_idx: Int = 0
var IsEnd: Boolean = false
var proc_level: Int = 0

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun cooking_page(onNavigateToCooking: () -> Unit, onNavigateToHome: () -> Unit, selectedItem: Int) {
    val buttonText = remember { mutableStateOf("Start") }
    process_description.value = "재료 준비"
    process_food.value = "모든 요리"


    var materials = ArrayList<String>()
    for (cuisine in cart) {
        for (recipe in cuisine.recipe) {
            for (material in recipe.material) {
                materials.add(material)
            }
        }
    }

    process_material.value = materials.joinToString(separator = ", ")

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
                        .padding(padding)
                        .verticalScroll(rememberScrollState()),
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
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.padding(2.dp))
                        Text(
                            process_food.value,
                            fontSize = 14.sp,
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            "Materials:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            process_material.value,
                            fontSize = 14.sp,
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            "Current Process:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            process_description.value,
                            fontSize = 14.sp,
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


                                for (curr_recipe in processing_food_list[proc_idx].recipe) {
                                    process_material.value =
                                        curr_recipe.material.joinToString(separator = ", ")
                                }

                                proc_idx++
                            } else if (!IsEnd) {
                                process_description.value = "-"
                                process_food.value = "잠시만 기다려주세요."
                                process_material.value = "-"

                                IsWaitNext = true
                            } else {
                                process_description.value = "모든 요리가 완성되었습니다."
                                process_food.value = "완성"

                                buttonText.value = "END"

                                cart.clear()
                                saveRecipes()
                                proc_level = 0
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

fun getDrawableIntByFileName(context: Context, fileName: String): Int {
    return context.resources.getIdentifier(fileName, "drawable", context.packageName)
}

var process_percent = mutableStateMapOf<Cuisine, Int>()

@Preview
@Composable
fun FoodCardInProcess(cuisine: Cuisine) {
    var shape = RoundedCornerShape(9.dp)
    var name_to_filename = cuisine.name.replace(" ", "_")
    val food_logo = "@drawable/food_logo_${(name_to_filename).lowercase()}"
    var drawableInt = getDrawableIntByFileName(context = getAppContext(), fileName = food_logo)
    if (drawableInt == 0) {
        drawableInt = R.drawable.default_image
    }

    if(process_percent[cuisine] == null){
        process_percent[cuisine] = 0
    }

    Card(
        Modifier.size(width = 100.dp, height = 150.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
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
                painter = painterResource(id = drawableInt),
                contentDescription = cuisine.description,
                Modifier.fillMaxSize(),
                tint = Color.Unspecified,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .align(alignment = Alignment.CenterHorizontally)

        ) {
            Text(
                cuisine.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                "${process_percent[cuisine]} %"
            )
        }
        // Card content
    }
}

@Preview
@Composable
fun FoodCardInHome(cuisine: Cuisine) {
    var shape = RoundedCornerShape(9.dp)
    val isChecked = remember { mutableStateOf(false) }

    val food_logo = "@drawable/food_logo_${(cuisine.name).lowercase()}"
    var drawableInt = getDrawableIntByFileName(context = getAppContext(), fileName = food_logo)

    if (drawableInt == 0) {
        drawableInt = R.drawable.default_image
    }
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
                    painter = painterResource(id = drawableInt),
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
