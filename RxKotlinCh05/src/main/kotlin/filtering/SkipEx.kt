package filtering

import combination.elapsedTime
import combination.startTime
import io.reactivex.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

/**
 * skip() - 특정 조건이 충족된 경우 or 무조건 선두/마지막 배출 일부를 건너뛰고 싶은 경우에 사용
 */
fun main() {
    // skip()
//    val observable1 = Observable.range(1, 10)
//    observable1.skip(5).subscribe { println("Received $it") }

    // 지정한 시간 동안 발생한 배출을 무시
//    val observable2 = Observable.interval(100L, TimeUnit.MILLISECONDS)
//    startTime
//    observable2.skip(400L, TimeUnit.MILLISECONDS)
//        .subscribe { println("Received $it - ${elapsedTime()}ms") }
//    runBlocking { delay(1000) }

    // skipLast()
//    val observable = Observable.range(1, 10)
//    observable.skipLast(5)
//        .subscribe { println("Received $it") }

//    val observable = Observable.interval(100L, TimeUnit.MILLISECONDS)
//    startTime
//    observable.skipLast(400L, TimeUnit.MILLISECONDS)
//        .subscribe { println("Received $it - ${elapsedTime()}ms") }
//    runBlocking { delay(1000) }

    // skipWhile() - 조건식(Predicate)기반으로 건너뜀, true이면 건너뛰고, false가 반환되는 순간부터 모든 배출을 다운스트림으로 전달
//    val observable = Observable.range(1, 10)
//    observable.skipWhile { it < 5 }
//        .subscribe { println("Received $it") }

    // skipUntil()
    val observable1 = Observable.interval(100L, TimeUnit.MILLISECONDS)
    val observable2 = Observable.timer(500L, TimeUnit.MILLISECONDS)

    startTime
    observable1.skipUntil(observable2)
        .subscribe { println("Received $it - ${elapsedTime()}ms") }

    runBlocking { delay(1500) }
}