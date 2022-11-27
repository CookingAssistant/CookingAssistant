import android.os.CountDownTimer

class Recipe(var title: String, var material: ArrayList<String>, var description: String, var duration:Long, var canDoOther: Boolean, var left: Recipe?, var right: Recipe?) : CountDownTimer(duration/10, 1000){
    constructor(title: String, material: ArrayList<String>, description: String, duration: Long, canDoOther: Boolean) : this(title, material, description, duration, canDoOther, null, null){
        // 생성자
    }

    var done: Boolean = false

    var isRunning: Boolean = false

    override fun onTick(millisUntilFinished: Long) {
        println("until : $millisUntilFinished")
        this.done = true;
        isRunning = true
        //rest of code
    }

    override fun onFinish() {
        isRunning = false

    }

}


//class SimpleThread(rot: rotto) : Thread(){
//
//    var c = rot.get()
//    override fun run() {
//        while(true) {
//            println("${Thread.currentThread()} add...")
//            Thread.sleep(1000)
//        }
//    }
//}