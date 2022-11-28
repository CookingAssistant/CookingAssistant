package ideawolf.CookingAssistant

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ideawolf.CookingAssistant.ui.theme.CookingAssistantTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun cooking_page(onNavigateToCooking: () -> Unit, onNavigateToHome: () -> Unit, selectedItem: Int) {
    val buttonText = remember { mutableStateOf("Start") }
    process_description.value = "재료 준비"
    process_food.value = "-"


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
                                imageVector = ImageVector.vectorResource(R.drawable.cooking_icon),
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
                                process_description.value = "잠시만 기다려주세요."
                                process_food.value = "-"
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
