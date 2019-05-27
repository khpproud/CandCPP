package combination

import io.reactivex.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

/**
 * concat() - 한 프로듀서의 onComplete 후 나머지 프로듀서를 이어 배출
 */
fun main() {
    val observable1 = Observable.interval(500, TimeUnit.MILLISECONDS).take(2)
        .map { "Observable 1: $it" }
    val observable2 = Observable.interval(100, TimeUnit.MILLISECONDS)
        .map { "Observable 2: $it" }

//    startTime
//    Observable.concat(observable1, observable2).subscribe { println("Received $it - ${elapsedTime()}ms") }
//    runBlocking { delay(1500) }

    // amb() - 먼저 도착한 프로듀서의 업스트림만 출력, 나머지 프로듀서는 폐기
    Observable.amb(listOf(observable1, observable2))
        .subscribe { println("Received $it") }
    runBlocking { delay(1000) }
}