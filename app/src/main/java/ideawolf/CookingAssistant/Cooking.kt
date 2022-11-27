package ideawolf.CookingAssistant

import Cuisine


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
    while (did < num_of_recipe) {
        for (c in Cuisines) {
            //println(c)
            for (i in 0 until c.recipe.size) {

                if (i == 0 && !c.recipe[i].isAlive && !c.recipe[i].done) {
                    println(c.recipe[i].description)
                    c.recipe[i].start()
                    did++


                } else if (i > 0){
                    if(c.recipe[i - 1].done && !c.recipe[i].isAlive && !c.recipe[i].done){

                        println(c.recipe[i].description)
                        c.recipe[i].start()
                        did++
                    }

                }




            }


        }

    }
    println("요리가 완성되었습니다 ~")
}