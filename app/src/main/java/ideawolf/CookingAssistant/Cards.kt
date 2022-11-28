package ideawolf.CookingAssistant

import Cuisine
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun FoodCardInProcess(cuisine: Cuisine) {
    var shape = RoundedCornerShape(9.dp)
    var name_to_filename = cuisine.name.replace(" ", "_")
    val food_logo = "@drawable/food_logo_${(name_to_filename).lowercase()}"
    var drawableInt = getDrawableIntByFileName(context = CookingAssistant.getAppContext(), fileName = food_logo)
    if (drawableInt == 0) {
        drawableInt = R.drawable.default_image
    }

    if (process_percent[cuisine] == null) {
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

    var name_to_filename = cuisine.name.replace(" ", "_")
    val food_logo = "@drawable/food_logo_${(name_to_filename).lowercase()}"
    var drawableInt = getDrawableIntByFileName(context = CookingAssistant.getAppContext(), fileName = food_logo)

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
