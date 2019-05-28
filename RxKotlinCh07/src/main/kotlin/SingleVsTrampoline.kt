import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * Schedulers.single() 과 Schedulers.trampoline()의 사용 비교
 */
fun main() {
    // Schedulers.single() : 순차적으로 실행되더라도 호출 스레드와 병렬로 실행
    // Schedulers.trampoline() : 스케줄러가 호출 스레드로 순차적으로 실행
    GlobalScope.async {
        startTime
        Observable.range(1, 10)
//            .subscribeOn(Schedulers.single())
            .subscribeOn(Schedulers.trampoline())
            .subscribe {
                runBlocking { delay(200) }
                println("Observable1 Item Received $it - ${elapsedTime()}ms : ${getThreadName()}")
            }

        Observable.range(11, 10)
//            .subscribeOn(Schedulers.single())
            .subscribeOn(Schedulers.trampoline())
            .subscribe {
                runBlocking { delay(100) }
                println("Observable2 Item Received $it - ${elapsedTime()}ms : ${getThreadName()}")
            }

        for (i in 1.. 10) {
            delay(100)
            println("Blocking Thread $i : ${getThreadName()}")
        }
    }
    runBlocking { delay(6000) }
}