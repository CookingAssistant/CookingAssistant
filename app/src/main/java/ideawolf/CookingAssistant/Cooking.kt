package ideawolf.CookingAssistant

import Cuisine
import android.os.Handler;
import android.util.Log

// A Cooking schedule algorithm work hear.
// Compute optimize cooking order.
fun cook(Cuisines: ArrayList<Cuisine>) {
    // 총 해야하는 일의 갯수 ( 레시피의 갯수)
    var num_of_recipe: Int = 0 // Total number of recipe

    for(c in Cuisines) {
            num_of_recipe = c.recipe.size + num_of_recipe
        }

    var did: Int = 0 // Number of finished process(recipe)
    IsEnd = false // Loop break condition

    var endCheck = false

    Thread(Runnable {
        while (!endCheck) {
            for (c in Cuisines) { // select a cusine
                for (i in 0 until c.recipe.size) { // Select a recipe of cuisine
                    // First recipe and recipe is not using and recipe is not done
                    if (i == 0 && !c.recipe[i].isAlive && !c.recipe[i].done) {
                        println(c.recipe[i].description)
//                        process_description.value = c.recipe[i].description
                        process_list.add(c.recipe[i]) // Add recipe to process list
                        processing_food_list.add(c) // Add Cuisine to processing food list

                        // This is the code that makes the ui interact with the user.
                        if(IsWaitNext){
                            IsWaitNext = false
                            process_description.value = process_list[proc_idx].description
                            process_food.value = "${processing_food_list[proc_idx].name}"
                            for (curr_recipe in processing_food_list[proc_idx].recipe) {
                                process_material.value = curr_recipe.material.joinToString(separator = ", ")
                            }

                            proc_idx++
                        }

                        c.recipe[i].start() // User start recipe
                        did++ // Count up


                    } else if (i > 0){ // c.recipe[i] is not the first recipe
                        // c.recipe[i – 1] is done and c.recipe[i] is not using and c.recipe[i] is not done
                        if(c.recipe[i - 1].done && !c.recipe[i].isAlive && !c.recipe[i].done){
                            println(c.recipe[i].description)
//                            process_description.value = c.recipe[i].description
                            process_list.add(c.recipe[i]) // Add recipe to process list
                            processing_food_list.add(c) // Add Cuisine to processing food list

                            // This is the code that makes the ui interact with the user.
                            if(IsWaitNext){
                                IsWaitNext = false
                                process_description.value = process_list[proc_idx].description
                                process_food.value = "${processing_food_list[proc_idx].name}"
                                for (curr_recipe in processing_food_list[proc_idx].recipe) {
                                    process_material.value = curr_recipe.material.joinToString(separator = ", ")
                                }
                                proc_idx++
                            }

                            c.recipe[i].start() // User start recipe
                            did++ // Count up
                        }
                    }

                }

                // Get the number of finished recipe
                var max = 0
                c.recipe.forEachIndexed { index, element ->
                    if(element.done == true){
                        if(max < index + 1){
                            max = index + 1
                        }
                    }
                }
                // Get the progress of cuisine
                process_percent[c] = ((max / c.recipe.size.toDouble()) * 100).toInt()
            }
            endCheck = true // Check the cuisine is done
            for (c in Cuisines) {
                if(!c.recipe[c.recipe.size - 1].done){
                    endCheck = false
                }

                var max = 0
                c.recipe.forEachIndexed { index, element ->
                    if(element.done == true){
                        if(max < index + 1){
                            max = index + 1
                        }
                    }
                }

                process_percent[c] = ((max / c.recipe.size.toDouble()) * 100).toInt()
            }
        }

        process_description.value = "모든 요리가 완성되었습니다."
        IsEnd = true

    }).start()

}