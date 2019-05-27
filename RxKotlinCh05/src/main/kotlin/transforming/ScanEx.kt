package transforming

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable

/**
 * scan() - 이전 배출을 누적시킴(증분)
 */
fun main() {
    Observable.range(1, 10).scan { prevAcc: Int, newEmission: Int -> prevAcc + newEmission }
        .subscribe { println("Received $it") }

    listOf("Str 1", "Str 2", "Str 3", "Str 4").toObservable().scan { prevAcc, newEmission -> "$prevAcc $newEmission" }
        .subscribe { println("Received $it") }
    Observable.range(1, 5)
        .scan{ prevAcc, newEmission -> prevAcc * 10 + newEmission }.subscribe { println("Received $it") }
}