import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import ideawolf.CookingAssistant.process_percent
import kotlinx.serialization.Serializable

@Serializable
class Recipe(var title: String, var material: ArrayList<String>, var description: String, var duration:Long, var canDoOther: Boolean) : Thread(){

    var done = false

    override fun run(){
        super.run()
        SystemClock.sleep(duration/5)
        this.done = true

        //println("$title end !")

    }

}

//class SimpleThread(rot: rotto) : Thread(){
//
//    var c = rot.get()
//    override fun run() {
//        while(true) {
//            println("${Thread.currentThread()} add...")
//            Thread.sleep(1000)Cooking.kt
//        }
//    }
//}