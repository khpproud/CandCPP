import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Schedulers.from(executor: Executor) 사용 예
 */
fun main() {
    val executor: Executor = Executors.newFixedThreadPool(2)
    val scheduler: Scheduler = Schedulers.from(executor)
    startTime
    Observable.range(1, 10)
        .subscribeOn(scheduler)
        .subscribe {
            runBlocking { delay(200) }
            println("Observable1 Item Received $it - ${elapsedTime()}ms : ${getThreadName()}")
        }

    Observable.range(11, 10)
        .subscribeOn(scheduler)
        .subscribe {
            runBlocking { delay(100) }
            println("Observable2 Item Received $it - ${elapsedTime()}ms : ${getThreadName()}")
        }

    Observable.range(21, 10)
        .subscribeOn(scheduler)
        .subscribe {
            runBlocking { delay(100) }
            println("Observable3 Item Received $it - ${elapsedTime()}ms : ${getThreadName()}")
        }

    runBlocking { delay(6_000) }
    System.exit(0)
}