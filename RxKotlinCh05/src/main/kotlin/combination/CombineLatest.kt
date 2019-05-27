package combination

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

/**
 * combineLatest() - 각 프로듀서의 배출 시마다 나머지 프로듀서의 최근 배출과 결합한 내용 배출
 */
fun main() {
    // zip()과 비교
    val observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)
    val observable2 = Observable.interval(250, TimeUnit.MILLISECONDS)

    startTime
//    Observable.zip(observable1, observable2, BiFunction { t1: Long, t2: Long -> "t1: $t1 t2: $t2" })
//        .subscribe { println("Received $it - ${elapsedTime()}ms") }
//    runBlocking { delay(1100) }

    // combineLatest()
    Observable.combineLatest(observable1, observable2, BiFunction {t1: Long, t2: Long -> "t1: $t1 t2: $t2" })
        .subscribe { println("Received $it - ${elapsedTime()}ms") }
    runBlocking { delay(1100) }
}

val startTime by lazy { System.currentTimeMillis() }

inline fun elapsedTime(): Long {
    return System.currentTimeMillis() - startTime
}