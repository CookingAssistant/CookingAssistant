import android.graphics.drawable.Drawable
import kotlinx.serialization.Serializable
import androidx.annotation.DrawableRes

// A class for store Cusine(Cooking).
// This class contain the arraylist of Recipe class.
@Serializable
class Cuisine(var name: String, var description: String, var recipe: ArrayList<Recipe>){

    var material = ArrayList<String>();
    init {
        for (x in recipe){
            this.material.addAll(x.material)
        }


    }


    fun printRecipe(){
        println("***** To make ${name} [${description}]*****")
        print("You need ")
        for(x in material){
            print("$x ")
        }
        println("")


        for(x in recipe){
            println("<${x.title}> - ${x.description}, 소요시간 : ${x.duration}, do other : ${x.canDoOther}")
        }

    }







}

