package ideawolf.CookingAssistant

import Cuisine
import Recipe
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ideawolf.CookingAssistant.ui.theme.CookingAssistantTheme
import java.util.concurrent.TimeUnit

var ramen_Recipe_List = ArrayList<Recipe>();

var ramen = Cuisine(name = "라면", description = "간편하게 먹는 라면입니다", recipe = ramen_Recipe_List);

var ramen_recipe2 = Recipe(
    title = "재료 넣기",
    material = arrayListOf<String>("라면 1봉지"),
    description = "면, 스프, 후레이크를 넣어주세요. 4분 더 끓이신 후 완성입니다.",
    duration = TimeUnit.SECONDS.toMinutes(4),
    canDoOther = false
);

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
        ramen_Recipe_List.add(ramen_recipe2);
        setContent {
            defaultUI()
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
fun cooking_page() {
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
            content = { padding ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {  // Selected Food
                        FoodCard(ramen)
                        FoodCard(ramen)
                        FoodCard(ramen)
                    }
                    Text("Hello World")
                    Text("Hello World")
                }
            },
        )
    }
}

@Preview
@Composable
fun FoodCard(cuisine: Cuisine) {
    var shape = RoundedCornerShape(9.dp)
    Card(
        Modifier.size(width = 100.dp, height = 150.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
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
                    tint= Color.Unspecified,
                )
            }
            Text(cuisine.name)
            Text("99.9" + " %")
        }
        // Card content
    }
}