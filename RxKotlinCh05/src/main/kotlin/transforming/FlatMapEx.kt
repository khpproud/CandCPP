package transforming

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable

/**
 * flatMap()
 */
fun main() {
//    val observable = (10 downTo 1).toObservable()
//    observable.flatMap {
//        number -> Observable.just("Transforming Int to String $number")
//    }.subscribe { println("Received $it") }

    val observable = (10 downTo 1).toObservable()
    observable.flatMap { number ->
        Observable.create<String> {
            it.onNext("The Number $number")
            it.onNext("number / 2 ${number / 2}")
            it.onNext("number % 2 ${number % 2}")
            it.onComplete()
        }
    }.subscribeBy(
        onNext = { println(it) },
        onComplete = { println("Complete") }
    )
}