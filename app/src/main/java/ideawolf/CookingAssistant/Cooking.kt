package ideawolf.CookingAssistant

import Cuisine
import android.os.Handler;
import android.util.Log


fun cook(Cuisines: ArrayList<Cuisine>) {
    // 총 해야하는 일의 갯수 ( 레시피의 갯수)
    var num_of_recipe: Int = 0

    for(c in Cuisines) {
            num_of_recipe = c.recipe.size + num_of_recipe
        }
//            for(recipe in c.recipe){
//                recipe.start()
//            }

    var did: Int = 0
    IsEnd = false

    Thread(Runnable {
        while (did < num_of_recipe) {
            for (c in Cuisines) {
                for (i in 0 until c.recipe.size) {

                    if (i == 0 && !c.recipe[i].isAlive && !c.recipe[i].done) {
                        println(c.recipe[i].description)
//                        process_description.value = c.recipe[i].description
                        process_list.add(c.recipe[i])
                        processing_food_list.add(c)

                        if(IsWaitNext){
                            IsWaitNext = false
                            process_description.value = process_list[proc_idx].description
                            process_food.value = "${processing_food_list[proc_idx].name}"

                            proc_idx++
                        }

                        c.recipe[i].start()
                        did++


                    } else if (i > 0){
                        if(c.recipe[i - 1].done && !c.recipe[i].isAlive && !c.recipe[i].done){
                            println(c.recipe[i].description)
//                            process_description.value = c.recipe[i].description
                            process_list.add(c.recipe[i])
                            processing_food_list.add(c)

                            if(IsWaitNext){
                                IsWaitNext = false
                                process_description.value = process_list[proc_idx].description
                                process_food.value = "${processing_food_list[proc_idx].name}"
                                proc_idx++
                            }

                            c.recipe[i].start()
                            did++
                        }
                    }
                }
            }
        }

        proc_level = 0
        IsEnd = true
    }).start()

}