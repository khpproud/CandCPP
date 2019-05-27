package filtering

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable

fun main() {
    // distinct() - 업스트림의 모든 중복 배출을 제거
//    listOf(1, 2, 3, 3, 4, 4, 5, 5, 5, 6, 7, 7, 7, 7, 8, 8, 9, 10, 10)
//        .toObservable().distinct().subscribe { println("Received $it") }

    // distinctUntilChanged() - 연속적인 중복 배출만 제거
//    listOf(1, 2, 2, 3, 4, 4, 5, 2, 3, 3, 6, 7, 7, 3, 8, 9, 10, 10)
//        .toObservable().distinctUntilChanged().subscribe { println("Received $it") }

    // elementAt() - n번째 요소만 배출
//    val observable = listOf(10, 1, 2, 5, 8, 6, 9).toObservable()
//
//    observable.elementAt(5).subscribe { println("Received $it") }
//    observable.elementAt(50).subscribe { println("Received $it") }

    // filter() - 인자로 주어진 조건식이 참인 항목만 배출
//    Observable.range(1, 20).filter { it % 2 == 0 }.subscribe { println("Received $it") }

    // first(), last() - 첫 번재, 마지막 원소만 배출
//    val observable = Observable.range(1, 10)
//    observable.first(2).subscribeBy { println("Received $it") }
//    observable.last(2).subscribeBy { println("Received $it") }
//    Observable.empty<Int>().first(2).subscribeBy { println("Received $it") }

    // ignoreElements() - onComplete 이벤만 존재하는 Completable 모나드를 반환
    val observable = Observable.range(1, 10)
    observable.ignoreElements().subscribe { println("Completed") }
}