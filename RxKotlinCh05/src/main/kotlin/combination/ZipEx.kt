package combination

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toObservable
import io.reactivex.rxkotlin.zipWith

/**
 * 결합 연산자
 */
fun main() {
    // startWith - 여러 프로듀서들을 결합
//    println("startWith Iterator")
//    Observable.range(5, 5).startWith(listOf(1, 2, 3, 4)).subscribe { print("$it ") }
//    println("\nstartWith another source Producer")
//    Observable.range(5, 5).startWith(Observable.just(1, 2, 3, 4))
//        .subscribe { print("$it ") }

    // zip() - 배출을 zipping
    val observable1 = Observable.range(1, 10)
    val observable2 = Observable.range(11, 8)
    Observable.zip(observable1, observable2, BiFunction<Int, Int, Int> { t1, t2 -> t1 + t2 } )
        .subscribe { println("Received $it") }

    // zipWith() - Observable 인스턴스 자체에서 호출 : 다른 Observable 과 zipping
    val observable3 = (1.. 10).map { "str $it" }.toObservable()
    observable3.zipWith(observable1) { s: String, i: Int? -> "$s $i" }.subscribe { println("Received $it") }
}