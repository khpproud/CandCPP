package filtering

import combination.elapsedTime
import combination.startTime
import io.reactivex.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

/**
 * take() - skip()과 정확히 반대로 동작 선두/마지막 부터 n개의 요소나, 시간 범위 이내의 요소만 가져옴
 */
fun main() {
    // take()
//    val observable1 = Observable.range(1, 10)
//    observable1.take(5)
//        .subscribe { println("Received $it") }

//    val observable2 = Observable.interval(100L, TimeUnit.MILLISECONDS)
//    startTime
//    observable2.take(400L, TimeUnit.MILLISECONDS)
//        .subscribe { println("Received $it - ${elapsedTime()}ms") }
//    runBlocking { delay(1000) }

    // takeLast()
//    val observable = Observable.range(1, 10)
//    observable.takeLast(5)
//        .subscribe { println("Received $it") }

    // takeWhile()
    val observable = Observable.range(1, 10)
    observable.takeWhile { it < 5 }
        .subscribe { println("Received $it") }
}