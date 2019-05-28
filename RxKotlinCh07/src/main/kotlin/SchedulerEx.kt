import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * 스케줄러 : 스레드 풀 관리를 위한 추상화 계층으로서 스케줄러 API는 미리 구성된 스케줄러를 제공
 */
fun main() {
    // non-scheduler
//    startTime
//    Observable.range(1, 10)
//        .subscribe {
//            runBlocking { delay(200) }
//            println("Observable1 Item Received $it - ${elapsedTime()}ms")
//        }
//    Observable.range(11, 10)
//        .subscribe {
//            runBlocking { delay(100) }
//            println("Observable2 Item Received $it - ${elapsedTime()}ms")
//        }

    // scheduler
    startTime
    Observable.range(1, 10)
        .subscribeOn(Schedulers.computation())
        .subscribe {
            runBlocking { delay(200) }
            println("Observable1 Item Received $it - ${elapsedTime()}ms : ${getThreadName()}")
        }
    Observable.range(11, 10)
        .subscribeOn(Schedulers.computation())
        .subscribe {
            runBlocking { delay(100) }
            println("Observable2 Item Received $it - ${elapsedTime()}ms : ${getThreadName()}")
        }
    runBlocking { delay(2100) }
    
}

val startTime by lazy { System.currentTimeMillis() }

fun getThreadName(): String =  Thread.currentThread().name

fun elapsedTime(): Long = System.currentTimeMillis() - startTime